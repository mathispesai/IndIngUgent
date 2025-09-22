package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;
import io.quarkus.logging.Log;

public class ConvertOpponentsLogic extends AUnitLogic {
    public ConvertOpponentsLogic(MoveFactory moveFactory, CustomContext context) {
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
        //If converting not possible, flee
        if(unitLoc.isPresent() && unitLoc.get().getOccupyingUnit().isPresent() && unitLoc.get().getOccupyingUnit().get().type() != UnitType.WORKER && unitLoc.get().getOccupyingUnit().get().type() != UnitType.CLERIC){
            return flee(input);
        }

        var enemyCo = customContext.findEnemy(input);

        //If standing on base, move
        if(input.unitLocation().isBase()){
            return enemyCo.isPresent() ? moveTowards(input, enemyCo.get()): travel(input).orElse(moveFactory.unitPray());
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
        if(enemyCo.isPresent() && input.faction().population() < input.faction().populationCap()){
            return moveTowards(input, enemyCo.get());
        }
        return travelToUnitWithLowHealth(input).orElse(moveTowards(input, customContext.baseProbability.getMaxProbabilityLocation()));
    }
}
