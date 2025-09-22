package be.ugent.devops.services.logic.api;

import java.util.List;

/**
 * This class defines the message format of the UnitMove request (input).
 * <p>
 * It contains all the basic information necessary for the FactionLogic implementation to make its decision
 * on what the next move should be for a specific Unit.
 */
public record UnitMoveInput(
        GameContext context,
        Faction faction,
        Unit unit,
        Location unitLocation,
        List<Location> neighbouringLocations
) {
}
