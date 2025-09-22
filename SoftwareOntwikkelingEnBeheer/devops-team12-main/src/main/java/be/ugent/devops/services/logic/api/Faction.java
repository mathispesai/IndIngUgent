package be.ugent.devops.services.logic.api;

import java.util.Set;

/**
 * This class models information about a Faction.
 */
public record Faction(
        int id,
        String name,
        Location base,
        long gold,
        int bombs,
        int territorySize,
        int population,
        int populationCap,
        int kills,
        long score,
        boolean defeated,
        int currentUpkeep
) {
}
