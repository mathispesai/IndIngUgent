package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.UnitInfo;

import java.util.Comparator;

public class SupportFighterLogic extends AUnitLogic {
    public SupportFighterLogic(MoveFactory moveFactory, CustomContext context) {
        super(moveFactory, context);
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        if(input.unit().type() != UnitType.CLERIC){
            return moveFactory.unitRetire();
        }

        var unitLoc = input.neighbouringLocations().stream()
                .filter(loc -> loc.getOccupyingUnit().isPresent() && loc.getOccupyingUnit().get().owner() != customContext.factionId)
                .findAny();
        //If enemy present and enlightened, convert if possible
        if(unitLoc.isPresent() && input.unit().enlightened() && input.faction().population() <= input.faction().populationCap() && input.faction().gold() >= input.context().unitMoveCost().get(UnitMoveType.CONVERT)){
            return convertUnit(unitLoc.get());
        }

        //If not enlightened, pray
        if(!input.unit().enlightened()){
            return moveFactory.unitPray();
        }

        //See if neighboring unit needs healing
        var unit = customContext.friendlyUnitLowHealth(input);
        if(unit.isPresent()){
            return moveFactory.unitHeal(unit.get());
        }

        var fighter = customContext.units.get(UnitType.FIGHTER).values().stream().sorted(Comparator.comparingInt(f -> (customContext.calculateDistance(f.tile.co, input.unitLocation())))).toList();
        if(!fighter.isEmpty() && fighter.getFirst().id != -1){

            return moveTowards(input, fighter.getFirst().tile.co);
        }

        return travelToUnitWithLowHealth(input).orElse(moveTowards(input, customContext.baseProbability.getMaxProbabilityLocation()));
    }
}
