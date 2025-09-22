package be.ugent.devops.services.logic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.BaseLogic.BaseBehavior;
import be.ugent.devops.services.logic.impl.CustomContext;
import be.ugent.devops.services.logic.impl.Tile;
import be.ugent.devops.services.logic.impl.UnitInfo;
import be.ugent.devops.services.logic.impl.UnitLogic.UnitBehavior;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class ClericMoveTest {
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
    public AbstractList<Location> getNeighborLocations(){
        var LU = new Location(4, 4, false, false, false, false, 0, null);
        var LM = new Location(4, 5, false, false, false, false, 0, null);
        var LD = new Location(4, 6, false, false, false, false, 0, null);
        var MU = new Location(5, 4, false, false, false, false, 0, null);
        var MD = new Location(5, 6, false, false, false, false, 0, null);
        var RU = new Location(6, 4, false, false, false, false, 0, null);
        var RM = new Location(6, 5, false, false, false, false, 0, null);
        var RD = new Location(6, 6, false, false, false, false, 0, null);

        return new ArrayList<>(Arrays.asList(LU, LM, LD, MU, MD, RU, RM, RD));
    }

    @Test
    public void testClericShouldPray() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var unit = new Unit(1, 0, UnitType.CLERIC, 1, 4, false, false);
        var unitLocation = new Location(5, 5, false, false, false, false, 2, unit);

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, getNeighborLocations());

        var move = logic.nextUnitMove(input);

        Assertions.assertEquals(UnitMoveType.PRAY, move.type());
    }

    @Test
    public void testClericShouldConvert() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var neighbors = getNeighborLocations();
        neighbors.removeFirst();
        var enemy = new Unit(34, 4, UnitType.PIONEER, 2, 3, false, false);
        neighbors.add(new Location(4, 4, false, false, false, false, null, enemy));

        var unit = new Unit(1, 0, UnitType.CLERIC, 1, 4, false, true);
        var unitLocation = new Location(5, 5, false, false, false, false, 2, unit);

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, neighbors);

        var move = logic.nextUnitMove(input);
        Assertions.assertEquals(UnitMoveType.CONVERT, move.type());
    }

    @Test
    public void testClericShouldPray2() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var neighbors = getNeighborLocations();
        neighbors.removeFirst();
        var enemy = new Unit(34, 4, UnitType.WORKER, 2, 3, false, false);
        neighbors.add(new Location(4, 4, false, false, false, false, null, enemy));

        var unit = new Unit(1, 0, UnitType.CLERIC, 1, 4, false, false);
        var unitLocation = new Location(5, 5, false, false, false, false, 2, unit);

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, neighbors);

        var move = logic.nextUnitMove(input);
        Assertions.assertEquals(UnitMoveType.PRAY, move.type());
    }

    @Test
    public void testClericShouldTravel() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var neighbors = getNeighborLocations();
        neighbors.removeFirst();
        var enemy = new Unit(34, 4, UnitType.FIGHTER, 2, 3, false, false);
        neighbors.add(new Location(4, 4, false, false, false, false, null, enemy));

        var unit = new Unit(1, 0, UnitType.CLERIC, 1, 4, false, false);
        var unitLocation = new Location(5, 5, false, false, false, false, 2, unit);

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, neighbors);

        var move = logic.nextUnitMove(input);
        Assertions.assertEquals(UnitMoveType.TRAVEL, move.type());
    }

    @Test
    public void testSupportClericShouldConvert() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var neighbors = getNeighborLocations();
        neighbors.removeFirst();
        var enemy = new Unit(34, 4, UnitType.PIONEER, 2, 3, false, false);
        neighbors.add(new Location(4, 4, false, false, false, false, null, enemy));

        var unit = new Unit(1, 0, UnitType.CLERIC, 1, 4, false, true);
        var unitLocation = new Location(5, 5, false, false, false, false, 2, unit);

        customContext.reset(context, BaseBehavior.MIDGAME, faction, new MoveFactory());
        customContext.units.get(UnitType.CLERIC).put(1, new UnitInfo(1, UnitBehavior.SUPPORT_FIGHTER, new Tile(5, 5)));

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, neighbors);

        var move = logic.nextUnitMove(input);
        Assertions.assertEquals(UnitMoveType.CONVERT, move.type());
    }

    @Test
    public void testSupportClericShouldPray() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var unit = new Unit(1, 0, UnitType.CLERIC, 1, 4, false, false);
        var unitLocation = new Location(5, 5, false, false, false, false, 2, unit);

        customContext.reset(context, BaseBehavior.MIDGAME, faction, new MoveFactory());
        customContext.units.get(UnitType.CLERIC).put(1, new UnitInfo(1, UnitBehavior.SUPPORT_FIGHTER, new Tile(5, 5)));

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, getNeighborLocations());

        var move = logic.nextUnitMove(input);

        Assertions.assertEquals(UnitMoveType.PRAY, move.type());
    }

    @Test
    public void testSupportClericShouldHeal() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var unit = new Unit(1, 0, UnitType.CLERIC, 1, 4, false, true);
        var unitLocation = new Location(5, 5, false, false, false, false, 2, unit);

        customContext.reset(context, BaseBehavior.MIDGAME, faction, new MoveFactory());
        customContext.units.get(UnitType.CLERIC).put(1, new UnitInfo(1, UnitBehavior.SUPPORT_FIGHTER, new Tile(5, 5)));

        var neighbors = getNeighborLocations();
        neighbors.removeFirst();
        neighbors.add(new Location(4, 4, false, false, false, false, null, new Unit(2, 0, UnitType.FIGHTER, 3, 1, false, false)));

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, neighbors);

        var move = logic.nextUnitMove(input);

        Assertions.assertEquals(UnitMoveType.HEAL, move.type());
    }


}
