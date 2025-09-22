package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;

public class FarmGoldLogic extends AUnitLogic{
    public FarmGoldLogic(MoveFactory moveFactory, CustomContext context) {
        super(moveFactory, context);
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        //Check if the unit is compatible
        if(input.unit().type() != UnitType.WORKER){
            return moveFactory.unitRetire();
        }

        if(isHomeBase(input.unitLocation())){
            var move = navigateToResource(input);
            if(move.type() == UnitMoveType.IDLE){
                return travel(input).orElse(moveFactory.unitIdle());
            }
            return navigateToResource(input);
//            return navigateToResource(input);
        }

        var enemy = customContext.enemyInRange(input);

        if(enemy.isPresent() && enemy.get().type() != UnitType.WORKER){
            customContext.units.get(input.unit().type()).get(input.unit().id()).tile.owner = -1;
            return flee(input);
        }

        //Else make money
        if(input.unitLocation().isResource() && (input.unitLocation().getOwner().isEmpty() || input.unitLocation().getOwner().get() == customContext.factionId)){
            if(input.unitLocation().getOwner().isEmpty()){
                return moveFactory.unitConquerLocation();
            }
            return moveFactory.unitGenerateGold();
        }

        //Else travel to resource tile
        return navigateToResource(input);
    }


}
