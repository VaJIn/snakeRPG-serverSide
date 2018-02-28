package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.engine.Entity;
import fr.vajin.snakerpg.engine.GameEngine;
import fr.vajin.snakerpg.engine.utilities.Position;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;

import java.net.DatagramPacket;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator{
    @Override
    public void setEngine(GameEngine gameEngine) {

    }

    @Override
    public DatagramPacket getNextPacket() {
        return null;
    }

    @Override
    public void acknowledgePacket(int idLastReceived, byte[] ackBitField) {

    }

    @Override
    public void notifyDestroyed(Entity entity) {

    }

    @Override
    public void notifyStateChange(Entity entity, int what) {

    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {

    }

    @Override
    public void notifySpriteChange(int id, Position newPosition, String newResource) {

    }

    @Override
    public void notifyNewEntity(Entity entity) {

    }

    @Override
    public void notifyRemovedEntity(Entity entity) {

    }

    @Override
    public void notifyGameEnd() {

    }
}
