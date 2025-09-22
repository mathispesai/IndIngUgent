package be.ugent.devops.services.logic.impl;

import be.ugent.devops.services.logic.api.Coordinate;
import be.ugent.devops.services.logic.api.Unit;

import java.util.Optional;

//Contains mostly the same information as Location however not everything from this class is available to all units (for example isRigged) and only locations next to units are available.
// Here we save the latest known state of all locations (which might not be accurate anymore)
public class Tile {
    public String id;
    public Optional<Boolean> isRigged = Optional.empty();
    public Boolean rigTile = false; //Set to true if sapper should rig this tile
    public boolean isBase;
    public boolean isResource;
    public boolean isFortified;
    public int owner; //-1 if neutral, -2 if unknown
    public Optional<Unit> occupyingUnit = Optional.empty();
    public Long updatedTurn; //Turn when this tile was last updated
//    public Optional<Integer> targetedByUnit; //id of the unit that is targeting this tile
//    public Optional<UnitMoveType> targetedAction; //action that is being performed on tile by targetedByUnit
    public Coordinate co;

    public Tile(int x, int y){
        id = x + "" + y;
        isBase = false;
        isResource = false;
        isFortified = false;
        updatedTurn = 0L;
        owner = -2;
        isRigged = Optional.empty();
        occupyingUnit = Optional.empty();
        this.co = new Coordinate(x, y);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + co.getX() +
                ", y=" + co.getY() +
                ", owner=" + owner +
                '}';
    }
}
