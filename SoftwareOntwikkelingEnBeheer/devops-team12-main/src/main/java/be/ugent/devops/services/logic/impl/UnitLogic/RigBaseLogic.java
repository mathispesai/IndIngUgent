package be.ugent.devops.services.logic.impl.UnitLogic;

import be.ugent.devops.services.logic.api.*;
import be.ugent.devops.services.logic.impl.CustomContext;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RigBaseLogic extends AUnitLogic {
    public RigBaseLogic(MoveFactory moveFactory, CustomContext context) {
        super(moveFactory, context);
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        //Check if the unit is compatible
        if(input.unit().type() != UnitType.SAPPER){
            return moveFactory.unitRetire();
        }

        var enemy = customContext.enemyInRange(input);

        if(enemy.isPresent()){
            return moveFactory.unitAttack(enemy.get());
        }

        Optional<Coordinate> target = Optional.empty();


        if(customContext.gameGrid[input.unitLocation().getY()][input.unitLocation().getX()].rigTile && !input.unitLocation().isFortified() && !input.unitLocation().isRigged() && !input.unitLocation().isResource() && !input.unitLocation().isBase() && input.unitLocation().getOwner().isPresent() && input.unitLocation().getOwner().get() == customContext.factionId){
            target = Optional.of(input.unitLocation());
        }
        if(target.isEmpty()){
            List<Location> tiles = input.neighbouringLocations().stream()
                .filter(loc -> customContext.gameGrid[loc.getY()][loc.getX()].rigTile && loc.getOwner().isPresent() && loc.getOwner().get() == customContext.factionId && !loc.isBase() && !loc.isResource() && !loc.isFortified() && loc.getOccupyingUnit().isEmpty() && !loc.isRigged())
                .sorted(Comparator.comparingInt(loc -> customContext.calculateDistance(loc, input.faction().base()))).toList();
            if(!tiles.isEmpty()){
                target = Optional.ofNullable(tiles.getFirst());
            }
        }
        if(target.isEmpty()){
            target = customContext.findClosestMatchingTile(customContext.baseLocation.co, t -> (t.owner == customContext.factionId && !t.isBase && !t.isResource && !t.isFortified && (t.occupyingUnit.isEmpty()|| t.occupyingUnit.get().id() == input.unit().id() ) && (t.isRigged.isEmpty() || !t.isRigged.get()) && t.rigTile ));
        }

        if(target.isPresent() && input.unitLocation().getX() == target.get().getX() && input.unitLocation().getY() == target.get().getY()){
            if(input.faction().bombs() > 0){
                return moveFactory.unitDeployBomb();
            }
            return moveFactory.unitIdle();
        }

        return target.isPresent()? moveTowards(input, target.get()) : rigClosestTile(input);
    }
}
