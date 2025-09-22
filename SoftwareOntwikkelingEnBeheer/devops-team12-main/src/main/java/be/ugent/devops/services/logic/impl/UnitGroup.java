package be.ugent.devops.services.logic.impl;

import be.ugent.devops.services.logic.api.GameContext;
import be.ugent.devops.services.logic.api.UnitType;

public class UnitGroup {
    public UnitInfo fighter1;
    public UnitInfo fighter2;
    public UnitInfo sapper;
    public UnitInfo cleric;

    public Tile target;
    public GroupAction action;

    public UnitGroup() {
        action = GroupAction.WAIT;
    }

    public boolean isGroupComplete(){
        return fighter1 != null && fighter2 != null && sapper != null && cleric != null;
    }

//    public boolean isUnitNextToTarget(Coordinate loc) {
//        return Math.abs(loc.getX() - target.co.getX()) <= 1 && Math.abs(loc.getY() - target.co.getY()) <= 1;
//    }

//    public boolean isGroupAtTarget(){
//        return isGroupComplete() && isUnitNextToTarget(fighter1.tile.co) && isUnitNextToTarget(fighter2.tile.co) && isUnitNextToTarget(cleric.tile.co) && isUnitNextToTarget(sapper.tile.co);
//    }

    public boolean isGroupPrepared(GameContext context){
        return fighter1.defenseBonus && fighter2.defenseBonus && cleric.enlightened && sapper.hp >= context.unitBaseHealth().get(UnitType.SAPPER) && fighter1.hp >= context.unitBaseHealth().get(UnitType.FIGHTER) && fighter2.hp >= context.unitBaseHealth().get(UnitType.FIGHTER);
    }
}
