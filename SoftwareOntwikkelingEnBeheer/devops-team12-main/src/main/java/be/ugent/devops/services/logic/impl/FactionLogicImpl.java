package be.ugent.devops.services.logic.impl;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.BaseLogic.*;
import be.ugent.devops.services.logic.impl.UnitLogic.*;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;

import java.util.*;

@ApplicationScoped
public class FactionLogicImpl implements FactionLogic {
    @Inject
    MoveFactory moveFactory;

    @Inject
    MeterRegistry registry;

    @Inject
    GameStateStore gameStateStore;

    CustomContext customContext = CustomContext.getInstance();

    //Add all the implementations for the behaviors in this dict
    private final Map<BaseBehavior, ABaseLogic> baseLogics = new HashMap<>();
    private final Map<UnitBehavior, AUnitLogic> unitLogics = new HashMap<>();

    @PostConstruct
    public void initialize() {
        // Initialize behavior mappings here
        baseLogics.put(BaseBehavior.EARLYGAME, new EarlyGameBaseLogic(moveFactory, customContext, BaseBehavior.EARLYGAME));
        baseLogics.put(BaseBehavior.MIDGAME, new MidgameBaseLogic(moveFactory, customContext, BaseBehavior.MIDGAME));
        baseLogics.put(BaseBehavior.ENDGAME, new EndgameBaseLogic(moveFactory, customContext, BaseBehavior.ENDGAME));

        unitLogics.put(UnitBehavior.FARM_GOLD, new FarmGoldLogic(moveFactory, customContext));
        unitLogics.put(UnitBehavior.EXPAND_TERRITORY, new ExpandTerritoryLogic(moveFactory, customContext));
        unitLogics.put(UnitBehavior.NEUTRALIZE_TERRITORY, new NeutralizeLogic(moveFactory, customContext));
        unitLogics.put(UnitBehavior.RIG_BASE, new RigBaseLogic(moveFactory, customContext));
        unitLogics.put(UnitBehavior.CONVERT_OPPONENTS, new ConvertOpponentsLogic(moveFactory, customContext));
        unitLogics.put(UnitBehavior.CAPTURE_BASE, new CaptureBaseLogic(moveFactory, customContext));
        unitLogics.put(UnitBehavior.DEFUSE_MINES, new DefuseMinesLogic(moveFactory, customContext));
        unitLogics.put(UnitBehavior.PROTECT_BASE, new ProtectBaseBehavior(moveFactory, customContext));
        unitLogics.put(UnitBehavior.SUPPORT_FIGHTER, new SupportFighterLogic(moveFactory, customContext));
    }

    @Produces  // Declare as Producer Method
    @Singleton // Make sure only one instance is created
    public CustomContext initializeCustomContext() {
        Log.info("Fetching initial game-state from store.");
        try{
            CustomContext storedContext = gameStateStore.read();
            if (storedContext != null) {
                CustomContext.setInstance(storedContext);
            }
            return CustomContext.getInstance();
        }catch (Exception e){
            Log.error(e);
            return CustomContext.getInstance();
        }
    }

    private void initializeGame(GameContext context, Faction faction){
        Log.infof("Start running game with id %s...", customContext.currentGameId);
        if(customContext == null){
            customContext = initializeCustomContext();
        }
        customContext.reset(context, BaseBehavior.EARLYGAME, faction, moveFactory);
    }


    @Override
    public BaseMove nextBaseMove(BaseMoveInput input) {
        try{
            if (!input.context().gameId().equals(customContext.currentGameId) || input.context().turnNumber() < 2L) {
                initializeGame(input.context(), input.faction());
            }

            //Different checks at the start of each turn (changes game behavior)
            customContext.initializeTurn(input);

            gameStateStore.write(customContext);

            BaseMove move;

            switch (customContext.currentBaseBehavior){
                case MIDGAME ->  move = baseLogics.get(BaseBehavior.MIDGAME).nextBaseMove(input);
                case ENDGAME ->  move = baseLogics.get(BaseBehavior.ENDGAME).nextBaseMove(input);
                default ->  move = baseLogics.get(BaseBehavior.EARLYGAME).nextBaseMove(input);
            }

            if(move.type() == BaseMoveType.RECEIVE_INCOME){
                customContext.previousGold += 500;
            }

            return registry.timer("base_execution_time", "behavior", customContext.currentBaseBehavior.toString())
                    .record(() -> move);

        }catch (Exception e){
            Log.error(e);
            return moveFactory.baseReceiveIncome();
        }
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        try{
            if (!input.context().gameId().equals(customContext.currentGameId)) {
                initializeGame(input.context(), input.faction());
            }

            if(customContext.unitsToRetire.contains(input.unit().id())){
                Log.debug("Retiring " + input.unit().type() + input.unit().id());
                return moveFactory.unitRetire();
            }

            //Update all neighboring tile and unitCount dict
            customContext.updateCustomContext(input);

            if(customContext.panicMode && input.unit().type() != UnitType.CLERIC && input.unit().type() != UnitType.WORKER && input.unit().type() != UnitType.PIONEER ){
                return unitLogics.get(UnitBehavior.PROTECT_BASE).nextUnitMove(input);
            }

            return registry.timer("move_execution_time", "unit", input.unit().type().name(), "behavior", customContext.units.get(input.unit().type()).get(input.unit().id()).behavior.toString())
                    .record(() -> switch (customContext.units.get(input.unit().type()).get(input.unit().id()).behavior) {
                case UnitBehavior.FARM_GOLD -> unitLogics.get(UnitBehavior.FARM_GOLD).nextUnitMove(input);
                case UnitBehavior.NEUTRALIZE_TERRITORY -> unitLogics.get(UnitBehavior.NEUTRALIZE_TERRITORY).nextUnitMove(input);
                case UnitBehavior.RIG_BASE -> unitLogics.get(UnitBehavior.RIG_BASE).nextUnitMove(input);
                case UnitBehavior.CONVERT_OPPONENTS -> unitLogics.get(UnitBehavior.CONVERT_OPPONENTS).nextUnitMove(input);
                case UnitBehavior.DEFUSE_MINES -> unitLogics.get(UnitBehavior.DEFUSE_MINES).nextUnitMove(input);
                case UnitBehavior.CAPTURE_BASE -> unitLogics.get(UnitBehavior.CAPTURE_BASE).nextUnitMove(input);
                case UnitBehavior.PROTECT_BASE -> unitLogics.get(UnitBehavior.PROTECT_BASE).nextUnitMove(input);
                case UnitBehavior.SUPPORT_FIGHTER -> unitLogics.get(UnitBehavior.SUPPORT_FIGHTER).nextUnitMove(input);
                default -> unitLogics.get(UnitBehavior.EXPAND_TERRITORY).nextUnitMove(input);
            });

        }catch (Exception e){
            Log.error(e);
            return moveFactory.unitRetire();
        }

    }
}
