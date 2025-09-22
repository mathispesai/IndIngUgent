package be.ugent.devops.services.logic.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

/**
 * This class defines the message format for the base move.
 * <p>
 * The base move determines if the Faction uses the turn to generate income or to start building or continue
 * building a unit, or one of the other possible actions.
 *
 * @param type         The type of BaseMove
 * @param unitToBuild  Optional field representing the type of unit to build. Only contains a value if type == START_BUILDING_UNIT.
 * @param baseLocation Optional field representing the location of the base. Only contains a value if type == MOVE_BASE.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record BaseMove(
        BaseMoveType type,
        Optional<UnitType> unitToBuild,
        Optional<Coordinate> baseLocation
) {
}
