package be.ugent.devops.services.logic.impl;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.BaseLogic.BaseBehavior;
import be.ugent.devops.services.logic.impl.UnitLogic.BaseProbability;
import be.ugent.devops.services.logic.impl.UnitLogic.UnitBehavior;
import io.quarkus.logging.Log;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomContext {

    private static CustomContext instance;

    private CustomContext() {}

    public static synchronized CustomContext getInstance() {
        if (instance == null) {

            instance = new CustomContext();
        }
        return instance;
    }

    public static synchronized void setInstance(CustomContext newInstance) {
        instance = newInstance;
    }

    protected static final Random rg = new Random();

    public String currentGameId;
    public BaseBehavior currentBaseBehavior;
    public int factionId;
    public int width;
    public int height;
    public Tile baseLocation;
    public Long previousGold = 0L; //Used to calculate goldBalance
    public Long goldBalance = 0L; //For if we generate or lose gold so we generate just enough gold. A bit broken but works good enough
    public int knownResources = 0; //The amount of known resource locations
//    public Queue<UnitBehavior> queuedBehavior = new LinkedList<>();
    public HashMap<UnitType, Queue<UnitBehavior>> queuedBehavior = new HashMap<>();

    public Tile[][] gameGrid; //Tile[y][x] contains the known gameGrid from last turn
    public HashMap<UnitType, HashMap<Integer, UnitInfo>> units = new HashMap<>();//Used to keep track of amount of units per type
    public HashMap<String, UnitMoveType> targetedTiles = new HashMap<>(); //Tiles which have been targeted for this turn. use to prevent action conflicts. Resets every turn
    public HashMap<UUID, UnitGroup> unitGroups = new HashMap<>(); //List of fighterGroups, these are 4 units working as a group
    public BaseProbability baseProbability;
    public List<Integer> unitsToRetire;
    public Boolean panicMode = false; //Set true if all unit should defend

    MoveFactory factory;

    public void reset(GameContext context, BaseBehavior startBaseBehavior, Faction faction, MoveFactory factory){
        this.currentBaseBehavior = startBaseBehavior;
        this.currentGameId = context.gameId();
        this.width = context.mapWidth();
        this.height = context.mapHeight();
        this.goldBalance = 0L;
        this.factionId = faction.id();
        this.factory = factory;
        this.baseProbability = new BaseProbability(width, height, factionId);
        this.previousGold = 200L;
        this.unitsToRetire = new ArrayList<>();
        this.knownResources = 0;
        this.panicMode = false;
        unitGroups = new HashMap<>();
        baseProbability = new BaseProbability(width, height, factionId);
        unitsToRetire = new ArrayList<>();
        queuedBehavior.clear();
        for(UnitType unit: UnitType.values()){
            queuedBehavior.put(unit, new LinkedList<>());
        }
        gameGrid = new Tile[height][width];
        // Fill each element with the initial value
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gameGrid[y][x] = new Tile(x, y);
            }
        }

        units = new HashMap<>();
        for(UnitType unit: UnitType.values()){
            units.put(unit, new HashMap<>());
        }

        this.baseLocation = gameGrid[faction.base().getY()][faction.base().getX()];

        for(int i = -4; i<=4; i++){
            for(int j =-4; j<=4; j++){
                if(!(j <= 1 && j >= -1 && i <= 1 && i >= -1)){
                    int x = (baseLocation.co.getX() + i + width) % width;
                    int y = (baseLocation.co.getY() + j + height) % height;
                    gameGrid[y][x].rigTile = true;
                }
            }
        }

    }



    // --------------------------- Update functions which update some state ---------------------------------

    //Check if game behavior needs to be changed
    public void initializeTurn(BaseMoveInput input){

        if(input.context().turnNumber() < 90){
            currentBaseBehavior = BaseBehavior.EARLYGAME;
        }else if(input.context().turnNumber() < 1000){
            currentBaseBehavior = BaseBehavior.MIDGAME;
        }else{
            currentBaseBehavior = BaseBehavior.ENDGAME;
        }
        targetedTiles.clear();

        //Check if any unit died:
        for (UnitType type : units.keySet()) {
            List<Integer> idsToRemove = new ArrayList<>();
            for (Integer id : units.get(type).keySet()) {
                if (input.context().turnNumber() - units.get(type).get(id).tile.updatedTurn >= 2) {
                    idsToRemove.add(id); // Mark for removal
                }
            }

            // Remove collected keys
            for (Integer id : idsToRemove) {
                var group = units.get(type).get(id).unitGroup;
                if( group != null){
                    Log.debug("Disbanding unitGroup");
                    if(unitGroups.get(group).fighter1 != null){
                        unitGroups.get(group).fighter1.unitGroup = null;
                    }
                    if(unitGroups.get(group).fighter2 != null){
                        unitGroups.get(group).fighter2.unitGroup = null;
                    }
                    if(unitGroups.get(group).sapper != null){
                        unitGroups.get(group).sapper.unitGroup = null;
                    }
                    if(unitGroups.get(group).cleric != null){
                        unitGroups.get(group).cleric.unitGroup = null;
                    }
                    unitGroups.remove(group);
                }
                units.get(type).remove(id);
                if(id != -1){
                    Log.debug(type + " " + id + " died!");
                }
            }
        }

        knownResources = (int) Arrays.stream(gameGrid)
                .flatMap(Arrays::stream)
                .filter(tile -> tile.isResource)
                .count();

        //Check if we have to many sappers
        List<UnitInfo> rigBaseSappers = new ArrayList<>();
        List<UnitInfo> defuseMinesSappers = new ArrayList<>();

        for(UnitInfo unit : units.get(UnitType.SAPPER).values()){
            if(unit.behavior == UnitBehavior.RIG_BASE){
                rigBaseSappers.add(unit);
            }else if (unit.behavior == UnitBehavior.DEFUSE_MINES){
                defuseMinesSappers.add(unit);
            }
        }
        while(rigBaseSappers.size() > 1){
            unitsToRetire.add(rigBaseSappers.removeLast().id);
        }
        if(defuseMinesSappers.size() > 1){
            unitsToRetire.add(defuseMinesSappers.getLast().id);
        }
        //Check if we have to many clerics
        List<UnitInfo> clerics = units.get(UnitType.CLERIC).values().stream().toList();
        if(clerics.size() > 4 && input.faction().population() == input.faction().populationCap()-1){
            unitsToRetire.add(clerics.getLast().id);
        }



        Log.info("Base turn " + input.context().turnNumber() + " prev gold: " + previousGold + ", gold: " + input.faction().gold() + ", balance: " + (input.faction().gold() - previousGold + ", upkeep: " + input.faction().currentUpkeep()));
        //Calculate gold income
        goldBalance = input.faction().gold() - previousGold;
        previousGold = input.faction().gold();
        //check if we have to many workers
        if(input.faction().population() >= input.faction().populationCap()-2){
            if(goldBalance > 200 && units.get(UnitType.WORKER).size() > 15){
                unitsToRetire.add(units.get(UnitType.WORKER).values().stream().toList().getLast().id);
            }else if(goldBalance > 50 && units.get(UnitType.WORKER).size() > 30){
                unitsToRetire.add(units.get(UnitType.WORKER).values().stream().toList().getLast().id);
            }else if(input.faction().population() > 45 && (input.faction().gold() + (goldBalance * (2400 - input.context().turnNumber())) > 1000 || goldBalance > -50  )){
                unitsToRetire.add(units.get(UnitType.WORKER).values().stream().toList().getLast().id);
            } else if(input.faction().population() == input.faction().populationCap() && units.get(UnitType.PIONEER).size() > 12){
                unitsToRetire.add(units.get(UnitType.PIONEER).values().stream().toList().getLast().id);
            }
        }
    }

    /**
     * Updates the gameGrid with neighboring tiles and units Dict
     * Sets the unitBehavior from queuedBehavior
     *
     * @param input UnitMoveInput
     */
    public void updateCustomContext(UnitMoveInput input){
        //Update this and neighboring locations
        updateLocation(input.unitLocation(), input.context().turnNumber(), input.unit().type());
        boolean rigLocations = input.unitLocation().isResource() && input.unit().type() == UnitType.WORKER;
        for(Location loc: input.neighbouringLocations()){
            updateLocation(loc, input.context().turnNumber(), input.unit().type(), rigLocations);
        }

        //Check if unit is new, if so, set behavior.
        if(!units.get(input.unit().type()).containsKey(input.unit().id())){
            Log.debug("New " + input.unit().type() + ":" +  input.unit().id() +" unit detected, attaching " + queuedBehavior.get(input.unit().type()).peek());
            var behavior = queuedBehavior.get(input.unit().type()).poll();
            if(behavior == null){//If fore some reason no behavior is attached to a unit initialize it here (this should not happen except for the default 2 pioneers in the beginning)
                var type = input.unit().type();
                behavior = switch (type){
                    case WORKER -> UnitBehavior.FARM_GOLD;
                    case FIGHTER -> UnitBehavior.NEUTRALIZE_TERRITORY;
                    case SAPPER -> UnitBehavior.RIG_BASE;
                    case CLERIC -> UnitBehavior.CONVERT_OPPONENTS;
                    default -> UnitBehavior.EXPAND_TERRITORY;
                };
                Log.warn("No unit behavior found for " + input.unit().type() + " " + input.unit().id() + ", setting default behavior.");
            }
            units.get(input.unit().type()).put(input.unit().id(), new UnitInfo(input.unit().id(), behavior, gameGrid[input.unitLocation().getY()][input.unitLocation().getX()]));

        }else{
            units.get(input.unit().type()).get(input.unit().id()).update(gameGrid[input.unitLocation().getY()][input.unitLocation().getX()], input.unit());
        }
    }

    private void updateLocation(Location loc, Long turn, UnitType unit, boolean rigLocation){
        Tile old = gameGrid[loc.getY()][loc.getX()];
//        if(old.owner !)
        if( old.owner != (loc.getOwner().isEmpty() ? -1: loc.getOwner().get()) || old.updatedTurn == 0){
            baseProbability.updateProbabilities(loc, false);
        }else if(loc.getOccupyingUnit().isPresent() && loc.getOccupyingUnit().get().owner() != factionId){
            baseProbability.updateProbabilities(loc, true);
        }

        old.updatedTurn = turn;
        old.owner = (loc.getOwner().isPresent()) ? loc.getOwner().get() : -1;
        old.isResource = loc.isResource();
        old.isFortified = loc.isFortified();
        old.isBase = loc.isBase();
        old.occupyingUnit = loc.getOccupyingUnit();
        old.rigTile = rigLocation || old.rigTile;
        if(unit == UnitType.SAPPER){
            old.isRigged = Optional.of(loc.isRigged());
        }
        gameGrid[loc.getY()][loc.getX()] = old;


    }
    private void updateLocation(Location loc, Long turn, UnitType unit){
        updateLocation(loc, turn, unit, false);
    }

    // ---------------------------------- Small helper functions --------------------------------------------
    public int calculateDistance(Coordinate loc, Coordinate loc2) {
        // Direct distances in x and y directions
        int dx = Math.abs(loc.getX() - loc2.getX());
        int dy = Math.abs(loc.getY() - loc2.getY());

        // Wrapped distances in x and y directions
        int wrappedDx = Math.min(dx, width - dx); // Wrap around the width of the map
        int wrappedDy = Math.min(dy, height - dy); // Wrap around the height of the map

        // Return the maximum distance considering wrapping
        return Math.max(wrappedDx, wrappedDy);
    }

    /**
     * Will search for closest opponent and travel towards it
     *
     * @param input UnitMoveInput
     * @return Coordinate if any found
     */
    public Optional<Coordinate> findEnemy(UnitMoveInput input){
        Optional<Coordinate> target;

        target = findClosestMatchingTile(input.unitLocation(), tile -> tile.occupyingUnit.isPresent() && tile.occupyingUnit.get().owner() != factionId);

        return target;
    }

    public Optional<Coordinate> findMine(UnitMoveInput input){
        Optional<Coordinate> target;

        //Find the closest tile with mine
        target = findClosestMatchingTile(input.unitLocation(), tile -> tile.occupyingUnit.isPresent() && tile.occupyingUnit.get().owner() != factionId && tile.isRigged.isPresent() && tile.isRigged.get());
        if(target.isEmpty()){//Else find the closest tile where rigged is unknown
            target = findClosestMatchingTile(input.unitLocation(), tile -> tile.occupyingUnit.isPresent() && tile.occupyingUnit.get().owner() != factionId && tile.isRigged.isEmpty());
        }
        return target;

    }


    /**
     * Check if a neighboring unit needs healing and return that unit
     * @param input UnitMoveInput
     * @return Unit with low health
     */
    public Optional<Unit> friendlyUnitLowHealth(UnitMoveInput input){
        return input.neighbouringLocations().stream()
                .flatMap(loc -> loc.getOccupyingUnit().stream())
                .filter(occupyingUnit -> occupyingUnit.owner() == factionId && occupyingUnit.health() < input.context().unitBaseHealth().get(occupyingUnit.type())).min((unit1, unit2) -> Boolean.compare(unit1.type() == UnitType.FIGHTER, unit2.type() == UnitType.FIGHTER));
    }

    /**
     * Returns the closest co to the given co which meets a condition
     *
     * @param co coordinate of base tile
     * @param condition condition tile needs to match
     * @param limit limits the radius of tiles to search (optional)
     * @return coordinate
     */
    public Optional<Coordinate> findClosestMatchingTile(Coordinate co, Predicate<Tile> condition, int limit) {
        Coordinate closestCo = null;
        int counter = 0;

        // Spiral search
        for (int radius = 0; counter < Math.max(width, height) && counter <= limit; radius++) {
            boolean foundTile = false;

            counter++;

            // Iterate over the 8 directions in a spiral
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {

                    // Skip the inner square to avoid duplicate checks
                    if (Math.abs(dx) != radius && Math.abs(dy) != radius) continue;

                    int newX = Math.abs((co.getX() + dx + width) % width);
                    int newY = Math.abs((co.getY() + dy + height) % height);

                    Tile tile = gameGrid[newY][newX];

                    // Check if this tile meets the condition
                    if (condition.test(tile)) {
                        closestCo = tile.co;
                        foundTile = true;
                        break;
                    }
                }
                if (foundTile) break;
            }

            if (foundTile || radius > limit) {
                break;
            }
        }
        return Optional.ofNullable(closestCo);
    }
    public Optional<Coordinate> findClosestMatchingTile(Coordinate co, Predicate<Tile> condition){
        return findClosestMatchingTile(co, condition, Integer.MAX_VALUE);
    }




    /**
     * Checks if an enemy unit is in range
     *
     * @param input UnitMoveInput
     * @return Unit if enemy unit is present
     */
    public Optional<Unit> enemyInRange(UnitMoveInput input){
        return input.neighbouringLocations().stream()
                .flatMap(loc -> loc.getOccupyingUnit().stream())
                .filter(occupyingUnit -> occupyingUnit.owner() != factionId)
                .findAny();
    }

    /**
     * Checks if a tile can safely be stepped on and isn't occupied
     *
     * @param loc location
     * @param strict set to true if you want the unit to avoid bombs, will only travel to locations which a sapper has confirmed to be safe.
     * @return boolean
     */
    public boolean isLocationFree(Location loc, Boolean strict){
        //If location is occupied or targeted by unit or homeBase return false
        if(targetedTiles.containsKey(loc.getX() + "" + loc.getY()) || loc.getOccupyingUnit().isPresent() || (loc.isBase() && loc.getOwner().isPresent() && loc.getOwner().get() == factionId)){
            return false;
        }
        //If owner is our faction don't check for mines, return true
        if(loc.getOwner().isPresent() && loc.getOwner().get() == factionId){
            return true;
        }
        Tile tile = gameGrid[loc.getY()][loc.getX()];
        //If strict and isRigged is unknown or rigged, return false
        if(strict && (tile.isRigged.isEmpty() || tile.isRigged.get())){
            return false;
        }//Else return false if tile has confirmed mine
        else return tile.isRigged.isEmpty() || !tile.isRigged.get();

    }
    public boolean isLocationFree(Location loc){
        return isLocationFree(loc, false);
    }


    public <T> T randomListItem(List<T> input) {
        return input.get(rg.nextInt(input.size()));
    }

    public int turnsToKill(Unit attacker, Unit defender){
        int turns = 0;
        int health = defender.health();
        if(attacker.damage() == 0){
            return Integer.MAX_VALUE;
        }

        while (health >= 0){
            if(turns == 0 && defender.defenseBonus()){
                health -= (attacker.damage() / 2);
            }else{
                health -= attacker.damage();
            }
            turns++;
        }

        return defender.enlightened() ? turns +1: turns;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        CustomContext context = (CustomContext) o;
        return Objects.equals(currentGameId, context.currentGameId) && Arrays.deepEquals(gameGrid, context.gameGrid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentGameId, Arrays.deepHashCode(gameGrid));
    }

}
