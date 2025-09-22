package be.ugent.devops.services.logic;

import be.ugent.devops.services.logic.api.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class FactionLogicTest {

    private static final Map<UnitType, Integer> bogusUnitBaseHealth = Arrays.stream(UnitType.values()).collect(Collectors.toMap(k -> k, v -> 100));
    private static final Map<UnitType, Integer> bogusUnitCost = Arrays.stream(UnitType.values()).collect(Collectors.toMap(k -> k, v -> 100));
    private static final Map<UnitMoveType, Integer> bogusUnitMoveCost = Arrays.stream(UnitMoveType.values()).collect(Collectors.toMap(k -> k, v -> 10));
    private static final GameContext context = new GameContext(0, "test", 100, 50, bogusUnitBaseHealth, bogusUnitCost, bogusUnitMoveCost);

    @Inject
    FactionLogic logic;

    @Test
    public void testFactionBuildsUnit() {
        var baseLocation = new Location(0, 0, false, false, false, false, 0, null);
        // Faction with maximum amount of gold and sufficient population capacity
        var faction = new Faction(0, "TestFaction", baseLocation, Long.MAX_VALUE, 0, 200, 0, 20,
                0, 0, false, 1500);
        // The build slot of the faction is empty
        var input = new BaseMoveInput(context, faction, Optional.empty());
        var move = logic.nextBaseMove(input);

        // With these conditions, the Faction Logic should return a START_BUILDING_UNIT move
        // Assert if this true
        assertEquals(BaseMoveType.START_BUILDING_UNIT, move.type());
        // Assert that the provided unit type should not be empty
        assertTrue(move.unitToBuild().isPresent());
    }
}
