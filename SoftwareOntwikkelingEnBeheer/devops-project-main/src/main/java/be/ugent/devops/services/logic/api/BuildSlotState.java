package be.ugent.devops.services.logic.api;

/**
 * This class defines a Faction build slot.
 * <p>
 * A build slot is used to model the building process of a new unit.
 * It indicates what type of unit is being build and how many turns are required before the build is complete.
 *
 * @param turnsLeft The number of turns left before the build process is complete. Upon completion, the new unit will be spawned at the Faction base location (if it is not occupied by another unit).
 * @param unitType  The type of unit that is being build.
 */
public record BuildSlotState(
        int turnsLeft,
        UnitType unitType
) {
}
