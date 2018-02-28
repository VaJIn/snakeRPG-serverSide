package fr.vajin.snakerpg.gameroom;

import fr.vajin.snakerpg.engine.EntityObserver;
import fr.vajin.snakerpg.engine.GameEngine;
import fr.vajin.snakerpg.engine.GameEngineObserver;

import java.net.DatagramPacket;

public interface PlayerPacketCreator extends EntityObserver, GameEngineObserver {

    void setEngine(GameEngine engine);

    DatagramPacket getNextPacket();

    void acknowledgePacket(int idLastReceived, byte[] ackBitField);
}
