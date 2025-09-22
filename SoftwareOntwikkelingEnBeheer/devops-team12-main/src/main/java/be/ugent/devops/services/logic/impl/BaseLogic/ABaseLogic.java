package be.ugent.devops.services.logic.impl.BaseLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.Tile;
import be.ugent.devops.services.logic.impl.UnitInfo;
import be.ugent.devops.services.logic.impl.UnitLogic.UnitBehavior;
import io.quarkus.logging.Log;

import java.util.Objects;
import java.util.Optional;


public abstract class ABaseLogic {
    protected MoveFactory moveFactory;
    protected CustomContext customContext;
    protected BaseBehavior baseBehavior;
    private Tile originalBaseTile;
    protected int buildedBombs = 0;


    public ABaseLogic(MoveFactory moveFactory, CustomContext context, BaseBehavior baseBehavior){
        this.baseBehavior = baseBehavior;
        this.moveFactory = moveFactory;
        this.customContext = context;
        this.originalBaseTile = context.baseLocation;
    }
    //Global helper functions

    /**
     * Check if faction has enough gold, takes goldBalance into account
     */
    protected boolean enoughGold(Long gold, int amount){
        return (gold + customContext.goldBalance) > amount;
    }

    /**
     * Returns a baseBuildUnit and sets the unitBehavior for this unit
     *
     * @param type UnitType
     * @param unitBehavior UnitBehavior enum
     * @return BaseMove
     */
    protected BaseMove buildUnit(BaseMoveInput input, UnitType type, UnitBehavior unitBehavior){
        customContext.previousGold -= input.context().unitCost().get(type);
        customContext.queuedBehavior.get(type).add(unitBehavior);
        //Make a dummy unit in the units list, this is because it takes 2 turns for the unit to register so next baseMove will not be aware of it yet.
        //This prevents base making to many of a certain unit
        customContext.units.get(type).put(-1, new UnitInfo(-1, unitBehavior, new Tile(0, 0)));
        return moveFactory.baseBuildUnit(type);
    }

    protected BaseMove buildBomb(){
        customContext.previousGold -= 500;
        buildedBombs += 1;
        return moveFactory.manufactureBomb();
    }

    protected boolean canBuildUnit(BaseMoveInput input, UnitType type){
        if(this instanceof EarlyGameBaseLogic){
            return input.faction().population() < input.faction().populationCap() && input.faction().gold() >= input.context().unitCost().get(type);
        }else{
            return input.faction().population() < (input.faction().populationCap() -1) && input.faction().gold() >= input.context().unitCost().get(type);
        }
    }

    public abstract BaseMove nextBaseMove(BaseMoveInput input);

    public Optional<BaseMove> standardBaseMoveChecks(BaseMoveInput input){
        if(originalBaseTile == null){
            this.originalBaseTile = customContext.baseLocation;
        }

        var closestEnemy = customContext.findClosestMatchingTile(originalBaseTile.co, tile -> (tile.occupyingUnit.isPresent() && tile.occupyingUnit.get().owner() != customContext.factionId), 6);

        if(closestEnemy.isPresent()){
            customContext.panicMode = true;
            if(input.faction().base().getOccupyingUnit().isPresent() && input.faction().base().getOccupyingUnit().get().owner() != customContext.factionId){
                var loc = customContext.findClosestMatchingTile(customContext.baseLocation.co, tile -> (tile.isBase && tile.owner == customContext.factionId && !Objects.equals(tile.id, customContext.baseLocation.id)));
                if(loc.isPresent()){
                    customContext.baseLocation = customContext.gameGrid[loc.get().getY()][loc.get().getX()];
                    return Optional.ofNullable(moveFactory.baseRelocate(loc.get()));
                }

            }
        }else{
            if(!Objects.equals(customContext.baseLocation.id, originalBaseTile.id ) && input.context().turnNumber() - originalBaseTile.updatedTurn < 3 && originalBaseTile.owner == customContext.factionId){
                customContext.baseLocation = originalBaseTile;
                return Optional.ofNullable(moveFactory.baseRelocate(originalBaseTile.co));
            }
            customContext.panicMode = false;
        }
        //If we don't have enough gold for current upkeep generate gold
        if(input.faction().gold() + customContext.goldBalance < 50){
            return Optional.ofNullable(moveFactory.baseReceiveIncome());
        }
        //If we have enough gold for upkeep continue building (if build slot is filled)
        if(input.buildSlotState().isPresent()){
//            if(input.faction().base().getOccupyingUnit().isPresent() && input.buildSlotState().get().turnsLeft() < 2){
//                customContext.unitsToRetire.add(input.faction().base().getOccupyingUnit().get().id());
//            }
            return Optional.ofNullable(moveFactory.baseContinueBuilding());
        }

        if(closestEnemy.isPresent()){
            if(canBuildUnit(input, UnitType.FIGHTER)){
                return Optional.ofNullable(buildUnit(input, UnitType.FIGHTER, UnitBehavior.PROTECT_BASE));
            }else if(canBuildUnit(input, UnitType.CLERIC)){
                return Optional.ofNullable(buildUnit(input, UnitType.CLERIC, UnitBehavior.CONVERT_OPPONENTS));
            }else if(canBuildUnit(input, UnitType.PIONEER)){
                return Optional.ofNullable(buildUnit(input, UnitType.PIONEER, UnitBehavior.PROTECT_BASE));
            }else if(input.faction().population() == input.faction().populationCap()){
                customContext.unitsToRetire.add(customContext.units.get(UnitType.PIONEER).values().stream().toList().getFirst().id);
            }
        }

        return Optional.empty();

    }

}
