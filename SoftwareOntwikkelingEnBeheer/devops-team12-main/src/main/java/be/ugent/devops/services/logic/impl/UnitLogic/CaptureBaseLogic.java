package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;

public class CaptureBaseLogic extends AUnitLogic {
    public CaptureBaseLogic(MoveFactory moveFactory, CustomContext context) {
        super(moveFactory, context);
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        //Check if the unit is compatible
        if(input.unit().type() != UnitType.FIGHTER && input.unit().type() != UnitType.PIONEER){
            return moveFactory.unitRetire();
        }

        if(input.unitLocation().isBase() && input.unitLocation().getOwner().isPresent() && input.unitLocation().getOwner().get() != customContext.factionId){
            return moveFactory.unitNeutralizeLocation();
        }

        var enemy = customContext.enemyInRange(input);
        if(enemy.isPresent()){
            return moveFactory.unitAttack(enemy.get());
        }

        if(input.unit().type() == UnitType.FIGHTER && !input.unit().defenseBonus()){
            return moveFactory.unitPrepareDefense();
        }

        if(input.unitLocation().getOwner().isEmpty()){
            return moveFactory.unitConquerLocation();
        }else if(input.unitLocation().getOwner().get() != customContext.factionId){
            return moveFactory.unitNeutralizeLocation();
        }

        if(input.unit().health() < input.context().unitBaseHealth().get(input.unit().type()) && !input.neighbouringLocations().stream().filter(loc -> (loc.getOccupyingUnit().isPresent() && loc.getOccupyingUnit().get().type() == UnitType.CLERIC)).toList().isEmpty()){
            return moveFactory.unitIdle();
        }

        var base = input.neighbouringLocations().stream().filter(loc -> (loc.isBase() && loc.getOwner().isPresent() && loc.getOwner().get() != customContext.factionId)).findFirst().orElse(null);
        if(base != null){
            return moveFactory.unitTravelTo(base);
        }

        return moveTowards(input, customContext.baseProbability.getMaxProbabilityLocation());
    }
}
