package be.ugent.devops.services.logic.api;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

/**
 * Use this class to construct Base and Unit moves.
 */
@ApplicationScoped
public class MoveFactory {

    /**
     * Start building a new unit.
     *
     * @param unitToBuild The type of unit to build.
     * @return A BaseMove instance
     */
    public BaseMove baseBuildUnit(UnitType unitToBuild) {
        return new BaseMove(BaseMoveType.START_BUILDING_UNIT, Optional.of(unitToBuild), Optional.empty());
    }

    /**
     * Continue building the unit currently in the Base building slot.
     *
     * @return A BaseMove instance
     */
    public BaseMove baseContinueBuilding() {
        return new BaseMove(BaseMoveType.CONTINUE_BUILDING_UNIT, Optional.empty(), Optional.empty());
    }

    /**
     * Use the Base to receive additional income for the turn.
     *
     * @return A BaseMove instance
     */
    public BaseMove baseReceiveIncome() {
        return new BaseMove(BaseMoveType.RECEIVE_INCOME, Optional.empty(), Optional.empty());
    }

    /**
     * Relocate the Base during this turn. WARNING: this resets the building slot.
     * The target location must be a Location having a Base and belong to the Faction doing the move.
     *
     * @param coordinate The coordinate of the Location to move to.
     * @return A BaseMove instance
     */
    public BaseMove baseRelocate(Coordinate coordinate) {
        return new BaseMove(BaseMoveType.MOVE_BASE, Optional.empty(), Optional.of(coordinate));
    }

    /**
     * Manufacture a bomb at the Base.
     *
     * @return A BaseMove instance
     */
    public BaseMove manufactureBomb() {
        return new BaseMove(BaseMoveType.MANUFACTURE_BOMB, Optional.empty(), Optional.empty());
    }

    /**
     * Move the Unit to a Location.
     *
     * @param coordinate The coordinate fo the Location to travel to.
     * @return A UnitMove instance
     */
    public UnitMove unitTravelTo(Coordinate coordinate) {
        return new UnitMove(UnitMoveType.TRAVEL, Optional.of(coordinate), Optional.empty());
    }

    /**
     * Use the Unit to neutralize a Location currently occupied by an enemy Faction.
     * (Fortified Locations will need to be neutralized twice!)
     *
     * @return A UnitMove instance
     */
    public UnitMove unitNeutralizeLocation() {
        return new UnitMove(UnitMoveType.NEUTRALIZE_ENEMY_TILE, Optional.empty(), Optional.empty());
    }

    /**
     * Use the Unit to conquer a Location.
     * You can only conquer neutral Locations.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitConquerLocation() {
        return new UnitMove(UnitMoveType.CONQUER_NEUTRAL_TILE, Optional.empty(), Optional.empty());
    }

    /**
     * Use the Unit to fortify a Location.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitFortifyLocation() {
        return new UnitMove(UnitMoveType.FORTIFY, Optional.empty(), Optional.empty());
    }

    /**
     * Use the unit to generate additional income.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitGenerateGold() {
        return new UnitMove(UnitMoveType.GENERATE_GOLD, Optional.empty(), Optional.empty());
    }

    /**
     * Use the Unit to attack an enemy Unit.
     *
     * @param target The unit to attack
     * @return A UnitMove instance
     */
    public UnitMove unitAttack(Unit target) {
        return new UnitMove(UnitMoveType.ATTACK, Optional.empty(), Optional.of(target));
    }

    /**
     * Use the Unit to setup defenses.
     * A Unit with prepared defenses will only take half of the damage on the next attack.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitPrepareDefense() {
        return new UnitMove(UnitMoveType.PREPARE_DEFENSE, Optional.empty(), Optional.empty());
    }

    /**
     * Use the Unit to pray.
     * A Unit that prayed reaches an enlightened state, allowing it to convert enemy units.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitPray() {
        return new UnitMove(UnitMoveType.PRAY, Optional.empty(), Optional.empty());
    }

    /**
     * Use the Unit to heal a friendly unit.
     *
     * @param target The unit to heal.
     * @return A UnitMove instance
     */
    public UnitMove unitHeal(Unit target) {
        return new UnitMove(UnitMoveType.HEAL, Optional.empty(), Optional.of(target));
    }

    /**
     * Use the Unit to convert an enemy Unit.
     * The unit doing the conversion must be in an ENLIGHTENED state (see unitPray())
     *
     * @param target The unit to convert
     * @return A UnitMove instance
     */
    public UnitMove unitConvert(Unit target) {
        return new UnitMove(UnitMoveType.CONVERT, Optional.empty(), Optional.of(target));
    }

    /**
     * Use the Unit to deploy a mine at its current location.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitDeployBomb() {
        return new UnitMove(UnitMoveType.DEPLOY_BOMB, Optional.empty(), Optional.empty());
    }

    /**
     * Use the Unit to defuse a mine at its current location.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitDefuseMine() {
        return new UnitMove(UnitMoveType.CLEAR_BOMB, Optional.empty(), Optional.empty());
    }


    /**
     * Retire the Unit.
     * The Unit will be removed from the game, freeing up population and reducing the Faction upkeep.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitRetire() {
        return new UnitMove(UnitMoveType.RETIRE, Optional.empty(), Optional.empty());
    }

    /**
     * Do nothing.
     *
     * @return A UnitMove instance
     */
    public UnitMove unitIdle() {
        return new UnitMove(UnitMoveType.IDLE, Optional.empty(), Optional.empty());
    }
}
