package be.ugent.devops.services.logic.impl.BaseLogic;

import be.ugent.devops.services.logic.api.BaseMove;
import be.ugent.devops.services.logic.api.BaseMoveInput;
import be.ugent.devops.services.logic.api.MoveFactory;
import be.ugent.devops.services.logic.api.UnitType;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.UnitGroup;
import be.ugent.devops.services.logic.impl.UnitLogic.UnitBehavior;
import io.quarkus.logging.Log;

import java.util.Random;
import java.util.UUID;

public class EndgameBaseLogic extends ABaseLogic {
    private final int NR_OF_CONVERTERS = 2; //Amount of clerics with convertOpponents behavior
    private UnitType nextUnit = null;
    private UnitBehavior nextUnitBehavior = null;
    private double fighterPioneerRatio = 0.24;
    private Random random = new Random();

    public EndgameBaseLogic(MoveFactory moveFactory, CustomContext context, BaseBehavior baseBehavior) {
        super(moveFactory, context, baseBehavior);
    }

    @Override
    public BaseMove nextBaseMove(BaseMoveInput input) {
        var move = standardBaseMoveChecks(input);
        if(move.isPresent()){
            return move.get();
        }


        if(nextUnit == null || nextUnitBehavior == null){
            var pioneers = customContext.units.get(UnitType.PIONEER).values().stream().toList().size();
            var neutralizers = customContext.units.get(UnitType.FIGHTER).values().stream().filter(c -> (c.behavior == UnitBehavior.NEUTRALIZE_TERRITORY)).toList().size();
            //Build 1 sapper to rig tiles around base
            if(customContext.goldBalance > -150 && customContext.units.get(UnitType.SAPPER).isEmpty()){
                nextUnit = UnitType.SAPPER;
                nextUnitBehavior = UnitBehavior.RIG_BASE;
            }
            //Build bombs to protect base
            else if(customContext.goldBalance > -150 && !customContext.units.get(UnitType.SAPPER).isEmpty() && buildedBombs <= 14 && input.faction().bombs() == 0){
                if(enoughGold(input.faction().gold(), 500)){
                    return buildBomb();
                }
                return moveFactory.baseReceiveIncome();
            }
            else if(customContext.goldBalance < -150  && customContext.knownResources > customContext.units.get(UnitType.WORKER).size() && (customContext.units.get(UnitType.WORKER).size() <= (input.faction().population()*2)/3  || customContext.goldBalance < -400)){
                nextUnit = UnitType.WORKER;
                nextUnitBehavior = UnitBehavior.FARM_GOLD;
            }
            else if(customContext.units.get(UnitType.CLERIC).values().stream().filter(c -> (c.behavior == UnitBehavior.CONVERT_OPPONENTS)).toList().size() < NR_OF_CONVERTERS){
                nextUnit = UnitType.CLERIC;
                nextUnitBehavior = UnitBehavior.CONVERT_OPPONENTS;
            }
            else if(pioneers == 0 || (double) neutralizers /pioneers > fighterPioneerRatio){
                nextUnit = UnitType.PIONEER;
                nextUnitBehavior = UnitBehavior.EXPAND_TERRITORY;
            }
            else{
                nextUnit = UnitType.FIGHTER;
                nextUnitBehavior = UnitBehavior.NEUTRALIZE_TERRITORY;
            }
        }

        if(canBuildUnit(input, nextUnit)){
            var queuedMove = buildUnit(input, nextUnit, nextUnitBehavior);
            nextUnit = null;
            nextUnitBehavior = null;
            return queuedMove;
        }else{
            if(input.faction().population() >= input.faction().populationCap() -1){
                if(enoughGold(input.faction().gold(), 500)){
                    return buildBomb();
                }
            }
            if(random.nextInt(3) == 1 && enoughGold(input.faction().gold(), 500) && input.faction().bombs() == 0){
                return buildBomb();
            }
            return moveFactory.baseReceiveIncome();
        }
    }
}
