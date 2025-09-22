package be.ugent.devops.services.logic.api;

/**
 * Enum describing the type of BaseMove
 */
public enum BaseMoveType {
    /**
     * Use the turn to generate income.
     */
    RECEIVE_INCOME,
    /**
     * Use the turn to start building a unit. In this case the unitToBuild parameter is NOT optional.
     */
    START_BUILDING_UNIT,
    /**
     * Use the turn to continue building the unit (all units take at least two turns before they are completed!).
     */
    CONTINUE_BUILDING_UNIT,
    /**
     * Use the turn to move the location of your base to a different conquered base location.
     */
    MOVE_BASE,
    /**
     * Use the turn to manufacture a bomb and add it to the Faction's stockpile.
     * Sapper units will consume one bomb from the stockpile when performing the DEPLOY_BOMB move.
     */
    MANUFACTURE_BOMB,
    /**
     * Do Nothing
     */
    IDLE
}
