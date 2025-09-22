package be.ugent.devops.services.logic.impl.BaseLogic;

public enum BaseBehavior {
    EARLYGAME, //For the first X turns (focus on expansion and unit building)
    MIDGAME,
    ENDGAME,
    BASIC, //The basic logic given at the beginning
}
