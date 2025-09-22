package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.MoveFactory;
import be.ugent.devops.services.logic.api.UnitMove;
import be.ugent.devops.services.logic.api.UnitMoveInput;
import be.ugent.devops.services.logic.api.UnitType;
import be.ugent.devops.services.logic.impl.CustomContext;
import io.quarkus.logging.Log;

public class ExpandTerritoryLogic extends AUnitLogic{
    public ExpandTerritoryLogic(MoveFactory moveFactory, CustomContext context) {
        super(moveFactory, context);
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        //Check if the unit is compatible
        if(input.unit().type() == UnitType.CLERIC || input.unit().type() == UnitType.SAPPER){
            return moveFactory.unitRetire();
        }

        if(input.unitLocation().isBase() && input.unitLocation().getOwner().isPresent() && input.unitLocation().getOwner().get() != customContext.factionId){
            return moveFactory.unitNeutralizeLocation();
        }

        var enemy = customContext.enemyInRange(input);

        if(enemy.isPresent()){
            return moveFactory.unitAttack(enemy.get());
        }

        return captureClosestTile(input);
    }
}
