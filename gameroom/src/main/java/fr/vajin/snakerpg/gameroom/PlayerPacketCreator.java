package fr.vajin.snakerpg.gameroom;

import fr.vajin.snakerpg.engine.EntityObserver;
import fr.vajin.snakerpg.engine.GameEngine;
import fr.vajin.snakerpg.engine.GameEngineObserver;

import java.net.DatagramPacket;

/**
 * Prend les updates du moteur & controlleur, anisi que les informations d'acknoledgement
 * des packets précedement envoyé, et préparent ainsi les prochains packets à être envoyés.
 */
public interface PlayerPacketCreator extends EntityObserver, GameEngineObserver {

    /**
     * @param gameEngine
     */
    void setEngine(GameEngine gameEngine);

    /**
     * Permet d'obtenir le prochain paquet à envoyer.
     *
     * @return le prochain paquet à envoyer.
     */
    DatagramPacket getNextPacket();

    /**
     * @param idLastReceived
     * @param ackBitField
     */
    void acknowledgePacket(int idLastReceived, byte[] ackBitField);
}
