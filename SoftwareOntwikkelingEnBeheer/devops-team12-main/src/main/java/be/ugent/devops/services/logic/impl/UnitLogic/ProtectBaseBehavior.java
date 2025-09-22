package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.MoveFactory;
import be.ugent.devops.services.logic.api.UnitMove;
import be.ugent.devops.services.logic.api.UnitMoveInput;
import be.ugent.devops.services.logic.api.UnitType;
import be.ugent.devops.services.logic.impl.CustomContext;

public class ProtectBaseBehavior extends AUnitLogic {
    public ProtectBaseBehavior(MoveFactory moveFactory, CustomContext context) {
        super(moveFactory, context);
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        if(input.unit().type() == UnitType.CLERIC){
            customContext.units.get(input.unit().type()).get(input.unit().id()).behavior = UnitBehavior.CONVERT_OPPONENTS;
            return moveFactory.unitPray();
        }

        var enemy = customContext.enemyInRange(input);

        if(enemy.isPresent()){
            return moveFactory.unitAttack(enemy.get());
        }

        var enemyCo = customContext.findEnemy(input);

        if(input.unitLocation().isBase()){
            return enemyCo.isPresent() ? moveTowards(input, enemyCo.get()): travel(input).orElse(moveFactory.unitIdle());
        }

        if(!input.unit().defenseBonus() && input.unit().type() == UnitType.FIGHTER){
            return moveFactory.unitPrepareDefense();
        }

        if(enemyCo.isPresent()){
            return moveTowards(input, enemyCo.get());
        }

        return captureClosestTile(input);
    }
}
