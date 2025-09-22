package be.ugent.devops.services.logic.impl;

import be.ugent.devops.services.logic.api.Unit;
import be.ugent.devops.services.logic.impl.UnitLogic.UnitBehavior;

import java.util.UUID;

public class UnitInfo {
    public int id;
    public UnitBehavior behavior;
    public Tile tile;
    public UUID unitGroup = null;
    public int hp = 1;
    public boolean enlightened = false;
    public boolean defenseBonus = false;

    public UnitInfo(int id, UnitBehavior behavior, Tile tile) {
        this.id = id;
        this.behavior = behavior;
        this.tile = tile;
    }

    public void update(Tile tile, Unit unit){
        this.tile = tile;
        this.hp = unit.health();
        this.enlightened = unit.enlightened();
        this.defenseBonus = unit.defenseBonus();
    }
}
