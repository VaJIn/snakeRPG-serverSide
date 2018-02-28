package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.Maps;
import fr.vajin.snakerpg.engine.Entity;
import fr.vajin.snakerpg.engine.GameEngine;
import fr.vajin.snakerpg.engine.utilities.Position;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.util.Map;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator{

    private GameEngine gameEngine;
    private Map<Integer,Entity> entities;

    public PlayerPacketCreatorImpl(){

        this.entities = Maps.newHashMap();
    }


    @Override
    public void setEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gameEngine.addGameEngineObserver(this);
    }

    @Override
    public DatagramPacket getNextPacket() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();


        byte [] data = stream.toByteArray();
        DatagramPacket packet = new DatagramPacket(data,data.length);

        return packet;
    }

    @Override
    public void acknowledgePacket(int idLastReceived, byte[] ackBitField) {

    }


    //FROM INDIVIDUAL ENTITY

    @Override
    public void notifyDestroyed(Entity entity) {
        this.entities.remove(entity.getEntityId());
    }

    @Override
    public void notifyStateChange(Entity entity, int what) {

        //TODO

    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {
        //TODO
    }

    @Override
    public void notifySpriteChange(int id, Position newPosition, String newResource) {
//useless
    }

    //FROM GAME ENGINE

    @Override
    public void notifyNewEntity(Entity entity) {

        entities.put(entity.getEntityId(),entity);
        entity.registerObserver(this);

    }

    @Override
    public void notifyRemovedEntity(Entity entity) {

    }

    @Override
    public void notifyGameEnd() {
        //TODO
    }
}
