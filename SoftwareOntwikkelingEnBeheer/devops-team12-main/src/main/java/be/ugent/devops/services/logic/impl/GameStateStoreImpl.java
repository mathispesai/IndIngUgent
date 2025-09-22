package be.ugent.devops.services.logic.impl;
import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.vertx.core.json.Json;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@ApplicationScoped
public class GameStateStoreImpl implements GameStateStore {

    @ConfigProperty(name = "game.state.path")
    String gameStatePath;

    private static final String FILENAME = "gamestate.json";

    private int lastWrittenHashCode = 0;

    @Override
    public void write(CustomContext gameState) {
        // Compute the hashcode to determine if writing is necessary
        int currentHashCode = Objects.hash(gameState);
        if (currentHashCode == lastWrittenHashCode) {
            return;
        }
        lastWrittenHashCode = currentHashCode;

        String json = Json.encodePrettily(gameState);

        Path filePath = Paths.get(gameStatePath, FILENAME);

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, json.getBytes());
        } catch (IOException e) {
            Log.error("Failed to write GameState to file: " + filePath, e);
        }
    }

    @Override
    public CustomContext read() {
        Path filePath = Paths.get(gameStatePath, FILENAME);

        if (!Files.exists(filePath)) {
            throw new RuntimeException("GameState file not found: " + filePath);
        }

        try {
            String json = Files.readString(filePath);
            return Json.decodeValue(json, CustomContext.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read GameState from file: " + filePath, e);
        }
    }

    public void onStop(@Observes ShutdownEvent event) {
        // Attempt to save the GameState before shutdown
        try {
            CustomContext gameState = CustomContext.getInstance();
            write(gameState);
        } catch (Exception e) {
            System.err.println("Failed to save GameState during shutdown: " + e.getMessage());
        }
    }
}
