package be.ugent.devops.services.logic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.BaseLogic.BaseBehavior;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.Tile;
import be.ugent.devops.services.logic.impl.UnitInfo;
import be.ugent.devops.services.logic.impl.UnitLogic.UnitBehavior;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class MidgameBaseLogicTest {
    Random rand = new Random();

    private static final Map<UnitType, Integer> bogusUnitBaseHealth = Arrays.stream(UnitType.values()).collect(Collectors.toMap(k -> k, v -> 5));
    private static final Map<UnitType, Integer> bogusUnitCost = Map.of(
            UnitType.FIGHTER, 700,
            UnitType.SAPPER, 850,
            UnitType.CLERIC, 500,
            UnitType.PIONEER, 200,
            UnitType.WORKER, 350
    );

    private static final Map<UnitMoveType, Integer> bogusUnitMoveCost = Arrays.stream(UnitMoveType.values()).collect(Collectors.toMap(k -> k, v -> 10));
    private static GameContext context = new GameContext(300, "test", 100, 50, bogusUnitBaseHealth, bogusUnitCost, bogusUnitMoveCost);
    private final CustomContext customContext = CustomContext.getInstance();

    @Inject
    FactionLogic logic;


    @BeforeEach
    public void setUp() {
        context = new GameContext(300, "test" + rand.nextInt(10000), 100, 50, bogusUnitBaseHealth, bogusUnitCost, bogusUnitMoveCost);
    }

    @Test
    public void testFactionGeneratesGold() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction_turn1 = new Faction(0, "TestFaction", baseLocation, 100L, 0, 200, 0, 20,
                0, 0, false, 120);

        var move = logic.nextBaseMove(new BaseMoveInput(context, faction_turn1, Optional.empty()));

        assertEquals(BaseMoveType.RECEIVE_INCOME, move.type());
    }

    @Test
    public void testFactionContinuesBuilding() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction_turn1 = new Faction(0, "TestFaction", baseLocation, 80L, 0, 200, 18, 20,
                0, 0, false, 100);
        var faction_turn2 = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 18, 20,
                0, 0, false, 100);
        // The build slot of the faction is empty
        logic.nextBaseMove(new BaseMoveInput(context, faction_turn1, Optional.empty()));
        var move = logic.nextBaseMove(new BaseMoveInput(context, faction_turn2, Optional.of(new BuildSlotState(2, UnitType.PIONEER))));

        // With these conditions, the Faction Logic should return a START_BUILDING_UNIT move
        // Assert if this true
        assertEquals(BaseMoveType.CONTINUE_BUILDING_UNIT, move.type());
    }

    @Test
    public void testFactionBuildsSapper() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction_turn1 = new Faction(0, "TestFaction", baseLocation, 1000L, 0, 200, 18, 20,
                0, 0, false, 100);

        customContext.reset(context, BaseBehavior.MIDGAME, faction_turn1, new MoveFactory());

        var move = logic.nextBaseMove(new BaseMoveInput(context, faction_turn1, Optional.empty()));

        assertEquals(BaseMoveType.START_BUILDING_UNIT, move.type());
        assertTrue(move.unitToBuild().isPresent());
        assertEquals(UnitType.SAPPER, move.unitToBuild().get());
    }

    @Test
    public void testFactionBuildsBomb() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction_turn1 = new Faction(0, "TestFaction", baseLocation, 1000L, 0, 200, 18, 20,
                0, 0, false, 100);

        customContext.reset(context, BaseBehavior.MIDGAME, faction_turn1, new MoveFactory());
        var tile = new Tile(0, 0);
        tile.updatedTurn = context.turnNumber();
        customContext.units.get(UnitType.SAPPER).put(1, new UnitInfo(1, UnitBehavior.RIG_BASE, tile));

        var move = logic.nextBaseMove(new BaseMoveInput(context, faction_turn1, Optional.empty()));

        assertEquals(BaseMoveType.MANUFACTURE_BOMB, move.type());
    }

    @Test
    public void testFactionBuildsCleric() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction_turn1 = new Faction(0, "TestFaction", baseLocation, 500L, 2, 200, 18, 20,
                0, 0, false, 100);

        customContext.reset(context, BaseBehavior.MIDGAME, faction_turn1, new MoveFactory());
        var tile = new Tile(0, 0);
        tile.updatedTurn = context.turnNumber();
        customContext.units.get(UnitType.SAPPER).put(1, new UnitInfo(1, UnitBehavior.RIG_BASE, tile));

        var move = logic.nextBaseMove(new BaseMoveInput(context, faction_turn1, Optional.empty()));

        assertEquals(BaseMoveType.START_BUILDING_UNIT, move.type());
        assertTrue(move.unitToBuild().isPresent());
        assertEquals(UnitType.CLERIC, move.unitToBuild().get());
    }

    @Test
    public void testFactionBuildsPioneer() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction_turn1 = new Faction(0, "TestFaction", baseLocation, 500L, 2, 200, 18, 20,
                0, 0, false, 100);

        customContext.reset(context, BaseBehavior.MIDGAME, faction_turn1, new MoveFactory());
        var tile = new Tile(0, 0);
        tile.updatedTurn = context.turnNumber();
        customContext.units.get(UnitType.SAPPER).put(1, new UnitInfo(1, UnitBehavior.RIG_BASE, tile));
        customContext.units.get(UnitType.CLERIC).put(1, new UnitInfo(1, UnitBehavior.CONVERT_OPPONENTS, tile));

        var move = logic.nextBaseMove(new BaseMoveInput(context, faction_turn1, Optional.empty()));

        assertEquals(BaseMoveType.START_BUILDING_UNIT, move.type());
        assertTrue(move.unitToBuild().isPresent());
        assertEquals(UnitType.PIONEER, move.unitToBuild().get());
    }

    @Test
    public void testFactionBuildsFighter() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction_turn1 = new Faction(0, "TestFaction", baseLocation, 1000L, 2, 200, 18, 20,
                0, 0, false, 100);

        customContext.reset(context, BaseBehavior.MIDGAME, faction_turn1, new MoveFactory());

        var tile = new Tile(0, 0);
        tile.updatedTurn = context.turnNumber();
        customContext.units.get(UnitType.SAPPER).put(1, new UnitInfo(1, UnitBehavior.RIG_BASE, tile));
        customContext.units.get(UnitType.CLERIC).put(1, new UnitInfo(1, UnitBehavior.CONVERT_OPPONENTS, tile));
        customContext.units.get(UnitType.PIONEER).put(1, new UnitInfo(1, UnitBehavior.EXPAND_TERRITORY, tile));
        customContext.units.get(UnitType.PIONEER).put(2, new UnitInfo(1, UnitBehavior.EXPAND_TERRITORY, tile));
        customContext.units.get(UnitType.PIONEER).put(3, new UnitInfo(1, UnitBehavior.EXPAND_TERRITORY, tile));


        var move = logic.nextBaseMove(new BaseMoveInput(context, faction_turn1, Optional.empty()));

        assertEquals(BaseMoveType.START_BUILDING_UNIT, move.type());
        assertTrue(move.unitToBuild().isPresent());
        assertEquals(UnitType.FIGHTER, move.unitToBuild().get());

    }


}
