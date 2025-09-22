package be.ugent.devops.services.logic;

import be.ugent.devops.services.logic.api.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

@QuarkusTest
public class WorkerTest {
    private static final Map<UnitType, Integer> bogusUnitBaseHealth = Arrays.stream(UnitType.values()).collect(Collectors.toMap(k -> k, v -> 100));
    private static final Map<UnitType, Integer> bogusUnitCost = Arrays.stream(UnitType.values()).collect(Collectors.toMap(k -> k, v -> 100));
    private static final Map<UnitMoveType, Integer> bogusUnitMoveCost = Arrays.stream(UnitMoveType.values()).collect(Collectors.toMap(k -> k, v -> 10));
    private GameContext context = new GameContext(0, "test", 100, 50, bogusUnitBaseHealth, bogusUnitCost, bogusUnitMoveCost);
    Random rand = new Random();

    @Inject
    FactionLogic logic;

    //Do this to make sure every test is considered a new game by the algo
    @BeforeEach
    public void setUp() {
        context = new GameContext(0, "test" + rand.nextInt(10000), 100, 50, bogusUnitBaseHealth, bogusUnitCost, bogusUnitMoveCost);
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
    public void testWorkerShouldTravel() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var unit = new Unit(1, 0, UnitType.WORKER, 1, 4, false, false);
        var unitLocation = new Location(5, 5, false, false, false, false, 0, null);
        var neighbors = getNeighborLocations();
        neighbors.removeFirst();
        neighbors.add(new Location(4, 4, false, true, false, false, 0, null));

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, neighbors);

        var move = logic.nextUnitMove(input);

        Assertions.assertEquals(UnitMoveType.TRAVEL, move.type());
        Assertions.assertEquals(Optional.of(neighbors.getLast()), move.targetLocation());
    }

    @Test
    public void testWorkerShouldFarmGold() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var unit = new Unit(1, 0, UnitType.WORKER, 1, 4, false, false);
        var unitLocation = new Location(5, 5, false, true, false, false, 0, unit);

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, getNeighborLocations());

        var move = logic.nextUnitMove(input);

        Assertions.assertEquals(UnitMoveType.GENERATE_GOLD, move.type());
    }

    @Test
    public void testWorkerShouldNeutralize() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, 500L, 0, 200, 0, 20,
                0, 0, false, 0);

        var unit = new Unit(1, 0, UnitType.WORKER, 1, 4, false, false);
        var unitLocation = new Location(5, 5, false, true, false, false, null, unit);

        UnitMoveInput input = new UnitMoveInput(context, faction, unit, unitLocation, getNeighborLocations());

        var move = logic.nextUnitMove(input);

        Assertions.assertEquals(UnitMoveType.CONQUER_NEUTRAL_TILE, move.type());
    }
}
