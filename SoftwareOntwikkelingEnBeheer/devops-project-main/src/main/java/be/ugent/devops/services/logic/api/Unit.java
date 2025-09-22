package be.ugent.devops.services.logic.api;

/**
 * This class defines a Unit in the game world.
 */
public record Unit(
        int id,
        int owner,
        UnitType type,
        int damage,
        int health,
        boolean defenseBonus,
        boolean enlightened
) {
}
