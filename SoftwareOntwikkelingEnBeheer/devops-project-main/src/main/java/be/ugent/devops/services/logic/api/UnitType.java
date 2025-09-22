package be.ugent.devops.services.logic.api;

/**
 * Enum describing the type of Unit.
 */
public enum UnitType {
    /**
     * A Pioneer is the general purpose unit of a Faction's army.
     * Soldiers are more effective at combat and workers more effective at generating resources,
     * but Pioneers can do a bit of both.
     */
    PIONEER,
    /**
     * A Worker is a utility unit. It specializes in generating additional income (a Worker can occupy a resource location).
     * However, Worker units cannot perform hostile actions (conquering locations, attacking enemy units).
     */
    WORKER,
    /**
     * A Fighter specializes in combat. It has additional health and can get defensive bonuses.
     * However, Fighter units cannot generate income.
     */
    FIGHTER,
    /**
     * A Cleric is an additional utilitarian Unit. Its role is supporting your Faction by healing friendly units and
     * trying to convert enemy units to your Faction’s side.
     */
    CLERIC,
    /**
     * A Sapper is a combat support Unit. It can deploy and defuse bombs. Sapper units are immune to bombs,
     * but any other unit stepping on a location rigged with a bomb that is not in friendly territory,
     * is immediately removed from the game.
     */
    SAPPER
}
