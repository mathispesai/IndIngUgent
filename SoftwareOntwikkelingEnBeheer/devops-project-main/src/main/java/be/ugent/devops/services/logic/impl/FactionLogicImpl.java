package be.ugent.devops.services.logic.impl;

import be.ugent.devops.services.logic.api.*;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class FactionLogicImpl implements FactionLogic {
    private static final Random rg = new Random();
    private static final double PIONEER_GENERATE_GOLD_CHANCE = 0.15;
    private String currentGameId;

    @Inject
    MoveFactory moveFactory;

    @Override
    public BaseMove nextBaseMove(BaseMoveInput input) {
        if (!input.context().gameId().equals(currentGameId)) {
            currentGameId = input.context().gameId();
            Log.infof("Start running game with id %s...", currentGameId);
        }

        return nextUnit(input.faction())
                .filter(type -> input.faction().gold() >= input.context().unitCost().get(type) && input.buildSlotState().isEmpty())
                .map(moveFactory::baseBuildUnit)
                .orElseGet(() -> input.buildSlotState()
                        .map(it -> moveFactory.baseContinueBuilding())
                        .orElseGet(moveFactory::baseReceiveIncome)
                );
    }

    @Override
    public UnitMove nextUnitMove(UnitMoveInput input) {
        return switch (input.unit().type()) {
            case PIONEER -> pioneerLogic(input);
            case FIGHTER -> soldierLogic(input);
            case WORKER -> workerLogic(input);
            case CLERIC -> moveFactory.unitIdle(); // TODO: extend with your own logic!
            case SAPPER -> moveFactory.unitIdle(); // TODO: extend with your own logic!
        };
    }

    private Optional<UnitType> nextUnit(Faction faction) {
        if (faction.population() < faction.populationCap()) {
            // TODO: add CLERIC and MINER to this list once you have logic for these!
            return Optional.of(randomListItem(List.of(UnitType.PIONEER, UnitType.WORKER, UnitType.FIGHTER)));
        } else {
            return Optional.empty();
        }
    }

    private UnitMove workerLogic(UnitMoveInput input) {
        var worker = input.unit();
        var workerLocation = input.unitLocation();

        // Always try to move away from our own base location and enemy locations
        if (workerLocation.isBase() || isHostileLocation(workerLocation, worker.owner())) {
            return travel(input).orElse(moveFactory.unitIdle());
        }

        // If not on resource, try moving to a resource (that is not in enemy territory)
        var resourceLocation = input.neighbouringLocations().stream()
                .filter(loc -> loc.isResource() && !isHostileLocation(loc, worker.owner()))
                .findAny();
        if (!workerLocation.isResource() && resourceLocation.isPresent() && resourceLocation.get().getOccupyingUnit().isEmpty()) {
            return moveFactory.unitTravelTo(resourceLocation.get());
        }

        // If on a neutral or owned resource
        if (workerLocation.isResource() && !isHostileLocation(workerLocation, worker.owner())) {
            // First capture if neutral
            if (workerLocation.getOwner().isEmpty()) {
                return moveFactory.unitConquerLocation();
            } else if (!workerLocation.isFortified()) {
                // Fortify this strategic location
                return moveFactory.unitFortifyLocation();
            } else {
                // Profit!
                return moveFactory.unitGenerateGold();
            }
        }

        // Otherwise: do random action and hope for the best!
        var action = randomListItem(List.of(UnitMoveType.TRAVEL, UnitMoveType.FORTIFY, UnitMoveType.CONQUER_NEUTRAL_TILE, UnitMoveType.GENERATE_GOLD));
        if (action.equals(UnitMoveType.FORTIFY) && workerLocation.getOwner().equals(Optional.of(worker.owner()))) {
            return moveFactory.unitFortifyLocation();
        } else if (action.equals(UnitMoveType.CONQUER_NEUTRAL_TILE) && workerLocation.getOwner().isEmpty()) {
            return moveFactory.unitConquerLocation();
        } else if (action.equals(UnitMoveType.GENERATE_GOLD) && Optional.of(worker.owner()).equals(workerLocation.getOwner())) {
            return moveFactory.unitGenerateGold();
        } else {
            // Travel
            return travel(input).orElse(moveFactory.unitIdle());
        }
    }

    private UnitMove pioneerLogic(UnitMoveInput input) {
        var pioneer = input.unit();
        var pioneerLocation = input.unitLocation();

        // If possible, conquer territory
        if (!Optional.of(pioneer.owner()).equals(pioneerLocation.getOwner())) {
            if (pioneerLocation.getOwner().isEmpty()) {
                return moveFactory.unitConquerLocation();
            } else {
                return moveFactory.unitNeutralizeLocation();
            }
        }

        // Attack enemies in range
        var enemyInRange = input.neighbouringLocations().stream()
                .flatMap(loc -> loc.getOccupyingUnit().stream())
                .filter(occupyingUnit -> occupyingUnit.owner() != pioneer.owner())
                .findAny();
        if (enemyInRange.isPresent()) {
            return moveFactory.unitAttack(enemyInRange.get());
        }

        // Otherwise, generate income a percentage of the time, else travel around
        if (rg.nextDouble() <= PIONEER_GENERATE_GOLD_CHANCE) {
            return moveFactory.unitGenerateGold();
        } else {
            return travel(input).orElse(moveFactory.unitGenerateGold());
        }
    }

    private UnitMove soldierLogic(UnitMoveInput input) {
        var soldier = input.unit();
        var soldierLocation = input.unitLocation();

        // Attack if enemy unit is near (should have priority as the soldier is the strongest unit)
        var enemyInRange = input.neighbouringLocations().stream()
                .flatMap(loc -> loc.getOccupyingUnit().stream())
                .filter(occupyingUnit -> occupyingUnit.owner() != soldier.owner())
                .findAny();
        if (enemyInRange.isPresent()) {
            return moveFactory.unitAttack(enemyInRange.get());
        }

        // Prepare defences for next encounter
        if (!soldier.defenseBonus()) {
            return moveFactory.unitPrepareDefense();
        }

        // If possible, conquer territory
        if (!Optional.of(soldier.owner()).equals(soldierLocation.getOwner())) {
            if (soldierLocation.getOwner().isEmpty()) {
                return moveFactory.unitConquerLocation();
            } else {
                return moveFactory.unitNeutralizeLocation();
            }
        }

        // Else try to travel
        return travel(input).orElse(moveFactory.unitPrepareDefense());
    }

    private <T> T randomListItem(List<T> input) {
        return input.get(rg.nextInt(input.size()));
    }

    private boolean isHostileLocation(Location location, Integer faction) {
        return location.getOwner().map(owner -> !owner.equals(faction)).orElse(false);
    }

    private Optional<UnitMove> travel(UnitMoveInput input) {
        var possibleMoves = input.neighbouringLocations().stream()
                .filter(loc -> !loc.isBase() || !loc.getOwner().equals(Optional.of(input.unit().owner()))) // Don't go back to own base.
                .filter(loc -> loc.getOccupyingUnit().isEmpty()) // The target location should not be occupied.
                .collect(Collectors.toList());
        return possibleMoves.isEmpty() ? Optional.empty() : Optional.of(moveFactory.unitTravelTo(randomListItem(possibleMoves)));
    }
}
