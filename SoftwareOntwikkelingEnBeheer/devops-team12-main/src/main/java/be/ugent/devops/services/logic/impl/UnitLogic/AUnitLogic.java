package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.UnitInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class AUnitLogic {
    protected MoveFactory moveFactory;
    protected CustomContext customContext;
    protected static final Random rg = new Random();


    public AUnitLogic(MoveFactory moveFactory, CustomContext context){
        this.moveFactory = moveFactory;
        this.customContext = context;
    }

    public abstract UnitMove nextUnitMove(UnitMoveInput input);

    protected UnitMove convertUnit(Location loc){

        customContext.targetedTiles.put(loc.getX() + "" + loc.getY(), UnitMoveType.CONVERT);
        if(loc.getOccupyingUnit().isEmpty()){
            return moveFactory.unitIdle();
        }
        switch (loc.getOccupyingUnit().get().type()) {
            case PIONEER -> customContext.queuedBehavior.get(loc.getOccupyingUnit().get().type()).add(UnitBehavior.EXPAND_TERRITORY);
            case FIGHTER -> customContext.queuedBehavior.get(loc.getOccupyingUnit().get().type()).add(UnitBehavior.NEUTRALIZE_TERRITORY);
            case WORKER -> customContext.queuedBehavior.get(loc.getOccupyingUnit().get().type()).add(UnitBehavior.FARM_GOLD);
            case CLERIC -> customContext.queuedBehavior.get(loc.getOccupyingUnit().get().type()).add(UnitBehavior.CONVERT_OPPONENTS);
            case SAPPER -> customContext.queuedBehavior.get(loc.getOccupyingUnit().get().type()).add(customContext.units.get(UnitType.SAPPER).isEmpty() ? UnitBehavior.RIG_BASE: UnitBehavior.DEFUSE_MINES);
        };

        return moveFactory.unitConvert(loc.getOccupyingUnit().get());
    }

    protected boolean isHomeBase(Location location){
        return location.isBase() && location.getOwner().isPresent() && location.getOwner().get() == customContext.factionId;
    }


    /**
     * Travel to closest Unrigged tile
     *
     * @param input UnitMoveInput
     * @return UnitMove
     */
    protected UnitMove rigClosestTile(UnitMoveInput input){
        List<Location> closestUnclaimedLocToBase = input.neighbouringLocations()
                .stream()
                .filter(loc -> loc.getOwner().isPresent() && loc.getOwner().get() == customContext.factionId && !loc.isRigged() && !loc.isBase())
                .sorted(Comparator.comparingInt(loc -> customContext.calculateDistance(loc, input.faction().base()))).toList();

        if(!closestUnclaimedLocToBase.isEmpty()){
            for(Location loc: closestUnclaimedLocToBase){
                if(loc.getOccupyingUnit().isEmpty() && !customContext.targetedTiles.containsKey(loc.getX() + "" + loc.getY())){
                    customContext.targetedTiles.put(loc.getX() + "" + loc.getY(), UnitMoveType.TRAVEL);
                    return moveFactory.unitTravelTo(loc);
                }
            }
        }

        //If no neighboring locations are found search broader
        Optional<Coordinate> co = customContext.findClosestMatchingTile(input.unitLocation(), tile -> (tile.owner == customContext.factionId && !tile.isBase && (tile.isRigged.isEmpty() || !tile.isRigged.get() )&& !customContext.targetedTiles.containsKey(tile.id)));
        if(co.isPresent()){
            return moveTowards(input, co.get());
        }
        return moveFactory.unitIdle();

    }

    /**
     * Will claim territory or move towards unclaimed territory
     *
     * @param input UnitMoveInput
     * @return UnitMove
     */
    protected UnitMove captureClosestTile(UnitMoveInput input){
        //If unit standing on unconquered location capture it
        if(input.unitLocation().getOwner().isEmpty() || input.unitLocation().getOwner().get() != input.faction().id()){
            if(input.unitLocation().getOwner().isPresent()){
                return moveFactory.unitNeutralizeLocation();
            }else{
                return moveFactory.unitConquerLocation();
            }
        }
        //Else search for neighboring location to capture
        List<Location> closestUnclaimedLocToBase = input.neighbouringLocations()
                .stream()
                .filter(loc -> (loc.getOwner().isEmpty() || loc.getOwner().get() != customContext.factionId) && loc.getOccupyingUnit().isEmpty())
                .sorted(
                        Comparator.comparing(Location::isBase, Comparator.reverseOrder()) // Sort by isBase (true first)
                                .thenComparingInt(loc -> customContext.calculateDistance(loc, input.faction().base())) // Then by distance
                )
                .toList();

        if(!closestUnclaimedLocToBase.isEmpty()){
            for(Location loc: closestUnclaimedLocToBase){
                if(customContext.isLocationFree(loc)){
                    customContext.targetedTiles.put(loc.getX() + "" + loc.getY(), UnitMoveType.TRAVEL);
                    return moveFactory.unitTravelTo(loc);
                }
            }
        }

        //If no neighboring locations are found search broader
        Optional<Coordinate> co = customContext.findClosestMatchingTile(input.unitLocation(), tile -> (
                tile.owner == -1 &&
                        ( tile.isRigged.isEmpty() || !tile.isRigged.get()) &&
                        !customContext.targetedTiles.containsKey(tile.id)) && tile.occupyingUnit.isEmpty() );
        if(co.isPresent()){
            return moveTowards(input, co.get());
        }
        co = customContext.findClosestMatchingTile(input.unitLocation(), tile -> (
                tile.owner != customContext.factionId &&
                        ( tile.isRigged.isEmpty() || !tile.isRigged.get()) &&
                        !customContext.targetedTiles.containsKey(tile.id)) && tile.occupyingUnit.isEmpty());
        if(co.isPresent()){
            return moveTowards(input, co.get());
        }
        return moveFactory.unitIdle();
    }


    /**
     * Will find and claim enemy territory
     *
     * @param input UnitMoveInput
     * @return UnitMove
     *
     * @todo: handle if not territory found
     */
    protected UnitMove neutralizeClosestTile(UnitMoveInput input){
        //If unit standing on unconquered location capture it
        if(input.unitLocation().getOwner().isEmpty() || input.unitLocation().getOwner().get() != input.faction().id()){
            if(input.unitLocation().getOwner().isPresent()){
                return moveFactory.unitNeutralizeLocation();
            }else{
                return moveFactory.unitConquerLocation();
            }
        }
        //Else search for neighboring location to capture
//        List<Location> enemyTiles = input.neighbouringLocations()
//                .stream()
//                .filter(loc -> loc.getOwner().isPresent() && loc.getOwner().get() != customContext.factionId)
//                .sorted((loc1, loc2) -> Boolean.compare(loc2.isBase(), loc1.isBase())).toList();
        List<Location> enemyTiles = input.neighbouringLocations()
                .stream()
                .filter(loc -> loc.getOwner().isPresent() && loc.getOwner().get() != customContext.factionId)
                .sorted(
                        Comparator.comparing(Location::isBase, Comparator.reverseOrder()) // Sort by isBase (true first)
                                .thenComparingInt(loc -> customContext.calculateDistance(loc, input.faction().base())) // Then by distance
                )
                .toList();

        if(!enemyTiles.isEmpty()){
            for(Location loc: enemyTiles){
                if(customContext.isLocationFree(loc)){
                    customContext.targetedTiles.put(loc.getX() + "" + loc.getY(), UnitMoveType.TRAVEL);
                    return moveFactory.unitTravelTo(loc);
                }
            }
        }

        //If no neighboring locations are found search broader
        Optional<Coordinate> co = customContext.findClosestMatchingTile(input.unitLocation(), tile -> ( tile.owner != -2 && tile.owner != -1 && tile.owner != customContext.factionId && (tile.isRigged.isEmpty() || !tile.isRigged.get()) && !customContext.targetedTiles.containsKey(tile.id)) && tile.occupyingUnit.isEmpty());
        if(co.isPresent()){
            return moveTowards(input, co.get());
        }
        return captureClosestTile(input);
    }

    /**
     * Will path find to the closest tile with resource
     *
     * @param input UnitMoveInput
     * @return UnitMove
     */
    protected UnitMove navigateToResource(UnitMoveInput input){
        //Search neighboring tiles for resource
        List<Location> resourceTiles = input.neighbouringLocations()
                .stream()
                .filter(loc -> (loc.getOwner().isEmpty() || loc.getOwner().get() == customContext.factionId) && loc.isResource() && loc.getOccupyingUnit().isEmpty()).toList();

        //If neighboring tile has resource, travel to it and increase goldIncome
        if(!resourceTiles.isEmpty()){
            return moveFactory.unitTravelTo(resourceTiles.getFirst());
        }

        //Else search broader
        Optional<Coordinate> co = customContext.findClosestMatchingTile(input.unitLocation(), tile -> (tile.owner == customContext.factionId || tile.owner == -1 ) && !customContext.targetedTiles.containsKey(tile.id) && tile.isResource && tile.occupyingUnit.isEmpty());

        if(co.isPresent()){
            return moveTowards(input, co.get());
        }

        //If none found travel in random direction
        if(input.unitLocation().isBase()){
            return travel(input).orElse(moveFactory.unitGenerateGold());
        }
        return moveFactory.unitGenerateGold();
    }

    /**
     * Will travel to friendly unit with low health (if found)
     *
     * @param input UnitMoveInput
     * @return UnitMove if any found
     */
    protected Optional<UnitMove> travelToUnitWithLowHealth(UnitMoveInput input){
        Optional<Coordinate> target;

        target = customContext.findClosestMatchingTile(input.unitLocation(), tile -> tile.occupyingUnit.isPresent() && tile.occupyingUnit.get().owner() == customContext.factionId && tile.occupyingUnit.get().health() < input.context().unitBaseHealth().get(tile.occupyingUnit.get().type()));
        return target.map(coordinate -> moveTowards(input, coordinate));

    }

    /**
     *
     *
     * @param input UnitMoveInput
     * @return UnitMove
     */
    public Optional<UnitMove> travel(UnitMoveInput input) {
        var possibleMoves = input.neighbouringLocations().stream()
                .filter(loc -> !loc.isBase() || !loc.getOwner().equals(Optional.of(input.unit().owner()))) // Don't go back to own base.
                .filter(loc -> loc.getOccupyingUnit().isEmpty()) // The target location should not be occupied.
                .collect(Collectors.toList());
        return possibleMoves.isEmpty() ? Optional.empty() : Optional.of(moveFactory.unitTravelTo(customContext.randomListItem(possibleMoves)));
    }

    public UnitMove flee(UnitMoveInput input) {
        Location bestTile = input.unitLocation();
        double lowestDanger = Double.MAX_VALUE;

        for (Location candidate : input.neighbouringLocations()) {
            double danger = 0;

            // Calculate danger from all tiles with occupying units
            for (Location tile : input.neighbouringLocations()) {
                if (tile.getOccupyingUnit().isPresent() && tile.getOccupyingUnit().get().owner() == customContext.factionId) {
                    double distance = Math.min(Math.abs(candidate.getX() - tile.getX()),Math.abs(candidate.getY() - tile.getY()));
                    // Add to danger (closer units contribute more danger)
                    danger += 1.0 / distance;
                }
            }

            // Find the tile with the lowest danger
            if (danger < lowestDanger) {
                lowestDanger = danger;
                bestTile = candidate;
            }
        }

        return moveFactory.unitTravelTo(bestTile);
    }


    /**
     * Finds the next step to reach a destination. Will add both next step and destination to targetedTiles to prevent units going to same tile
     *
     * @param input UnitMoveInput
     * @param end the destination
     * @return unitTravelToAction or idle if unit is stuck
     */
    protected UnitMove moveTowards(UnitMoveInput input, Coordinate end, boolean strict) {
        if(input.unitLocation().getX() == end.getX() && input.unitLocation().getY() == end.getY()){
            return moveFactory.unitIdle();
        }

        // Sort neighboring locations based on Euclidean distance to the target
        List<Location> sortedLocations = input.neighbouringLocations().stream()
                .filter(loc -> customContext.isLocationFree(loc, strict)) // Keep only free locations
                .sorted(Comparator.comparingDouble(loc -> {
                    // Calculate Euclidean distance to the target
                    double dx = Math.min(Math.abs(loc.getX() - end.getX()), customContext.width - Math.abs(loc.getX() - end.getX()));
                    double dy = Math.min(Math.abs(loc.getY() - end.getY()), customContext.height - Math.abs(loc.getY() - end.getY()));

                    return dx * dx + dy * dy;
                }))
                .toList();

        // Move to the closest free location if any exist
        if(!sortedLocations.isEmpty()){
            customContext.targetedTiles.put(end.getX() + "" + end.getY(), UnitMoveType.TRAVEL);
            customContext.targetedTiles.put(sortedLocations.getFirst().getX() + "" + sortedLocations.getFirst().getY(), UnitMoveType.TRAVEL);
            return moveFactory.unitTravelTo(sortedLocations.getFirst()); // Return move to this location
        }

        // If no free location is found, idle
        return moveFactory.unitIdle();
    }
    public UnitMove moveTowards(UnitMoveInput input, Coordinate end) {
        return moveTowards(input, end, false);
    }


}
