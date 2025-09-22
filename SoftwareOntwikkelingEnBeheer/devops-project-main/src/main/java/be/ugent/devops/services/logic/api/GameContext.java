package be.ugent.devops.services.logic.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * This class is used to represent basic information for a Game Session.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GameContext(
        long turnNumber,
        String gameId,
        int mapWidth,
        int mapHeight,
        Map<UnitType, Integer> unitBaseHealth,
        Map<UnitType, Integer> unitCost,
        Map<UnitMoveType, Integer> unitMoveCost
) {
}
