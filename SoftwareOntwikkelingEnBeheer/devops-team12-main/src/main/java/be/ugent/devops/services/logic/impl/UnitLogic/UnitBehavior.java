package be.ugent.devops.services.logic.impl.UnitLogic;

public enum UnitBehavior {
    FARM_GOLD, //Find and collect resources
    EXPAND_TERRITORY, //Capture closest tile
    NEUTRALIZE_TERRITORY, //Capture closest enemy tile
    RIG_BASE, //Place mines around the base
    CONVERT_OPPONENTS, //Cleric will search for and convert opponents
    DEFUSE_MINES, //Sapper will go around the map and difuse mines
    SUPPORT_FIGHTER, //
    CAPTURE_BASE,
    PROTECT_BASE,
}
