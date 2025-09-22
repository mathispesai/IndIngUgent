package be.ugent.devops.services.logic.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

/**
 * This class defines the message format for the unit move.
 * <p>
 * The unit move defines what the next move will be for a specific unit (generate income, attack, conquer a location...).
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record UnitMove(
        UnitMoveType type,
        Optional<Coordinate> targetLocation,
        Optional<Unit> targetUnit
) {

}
