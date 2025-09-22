package be.ugent.devops.services.logic.impl.BaseLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.UnitLogic.UnitBehavior;
import io.quarkus.logging.Log;

public class EarlyGameBaseLogic extends ABaseLogic {

    public EarlyGameBaseLogic(MoveFactory moveFactory, CustomContext context, BaseBehavior baseBehavior){
        super(moveFactory, context, baseBehavior);
    }


    //At the start we want to grow as fast as possible. That's why we create workers at first and expand territory if we need to increase unit cap
    @Override
    public BaseMove nextBaseMove(BaseMoveInput input) {
        var move = standardBaseMoveChecks(input);
        if(move.isPresent()){
            return move.get();
        }

        // -------- Choice of next unit -------
        //If the gold income of  our current workers is less than upkeep and there are fewer workers then known resources, create worker (except first 30 turns)
        if(customContext.goldBalance < -50 && enoughGold(input.faction().gold(), input.context().unitCost().get(UnitType.WORKER)) && input.context().turnNumber() > 50 && customContext.knownResources > customContext.units.get(UnitType.WORKER).size()){
            return buildUnit(input, UnitType.WORKER, UnitBehavior.FARM_GOLD);
        }//Else create pioneer to capture territory, we want enough gold for the worker unit so it won't skip over previous statement every time and never create workers
        else if( enoughGold(input.faction().gold(),input.context().unitCost().get(UnitType.WORKER))){
            return buildUnit(input, UnitType.PIONEER, UnitBehavior.EXPAND_TERRITORY);
        }
        Log.debug("Generating gold END");
        return moveFactory.baseReceiveIncome();
    }

}
