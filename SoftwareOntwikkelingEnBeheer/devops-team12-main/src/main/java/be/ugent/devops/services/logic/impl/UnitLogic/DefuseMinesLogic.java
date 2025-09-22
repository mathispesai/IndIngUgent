package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.GroupAction;

import java.util.List;

public class DefuseMinesLogic extends AUnitLogic {
    public DefuseMinesLogic(MoveFactory moveFactory, CustomContext context) {
        super(moveFactory, context);
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        if(input.unit().type() != UnitType.SAPPER){
            return moveFactory.unitRetire();
        }

        var enemy = customContext.enemyInRange(input);
        if(enemy.isPresent()){
            return moveFactory.unitAttack(enemy.get());
        }

        //If standing on base, move
        if(input.unitLocation().isBase()){
            return travel(input).orElse(moveFactory.unitPray());
        }

        if(input.unitLocation().isRigged() && input.unitLocation().getOwner().isPresent() && input.unitLocation().getOwner().get() != customContext.factionId){
            return moveFactory.unitDefuseMine();
        }

        List<Location> riggedLocations = input.neighbouringLocations()
                .stream()
                .filter(loc -> loc.getOwner().isPresent() && loc.getOwner().get() != customContext.factionId && loc.isRigged() && loc.getOccupyingUnit().isEmpty())
                .toList();
        if(riggedLocations.size() > 1){
            return moveFactory.unitTravelTo(riggedLocations.getFirst());
        }

        var co = customContext.findMine(input);
        if(co.isPresent()){
            return moveTowards(input, co.get());
        }
        return moveFactory.unitIdle();
    }
}
