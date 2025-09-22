package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;
import io.quarkus.logging.Log;

public class NeutralizeLogic extends AUnitLogic {
    public NeutralizeLogic(MoveFactory moveFactory, CustomContext context) {
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

        UnitMove move = neutralizeClosestTile(input);
        if(move.type() == UnitMoveType.IDLE){
            move = captureClosestTile(input);
        }
        if(move.type() == UnitMoveType.IDLE){
            return travel(input).orElse(move);
        }

        return move;
    }
}
