package be.ugent.devops.services.logic.impl;

public interface GameStateStore {
    void write(CustomContext gameState);

    CustomContext read();
}
