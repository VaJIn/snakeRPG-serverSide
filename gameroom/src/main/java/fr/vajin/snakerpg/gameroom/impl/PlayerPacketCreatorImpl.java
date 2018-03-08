package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.Maps;
import fr.vajin.snakerpg.engine.Entity;
import fr.vajin.snakerpg.engine.GameEngine;
import fr.vajin.snakerpg.engine.utilities.Position;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Map;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator{

    private GameEngine gameEngine;
    private Map<Integer,Entity> entities;
    private int idProtocol;
    private int lastIdReceived;
    private byte [] ackBitfield;

    public PlayerPacketCreatorImpl(int idProtocol){

        this.entities = Maps.newHashMap();
        this.idProtocol = idProtocol;
    }


    @Override
    public void setEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gameEngine.addGameEngineObserver(this);
    }

    @Override
    public DatagramPacket getNextPacket() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        stream.write(idProtocol);

        stream.write(this.lastIdReceived);

        for(Entity entity : entities.values()){

            while(entity.getEntityTilesInfosIterator().hasNext()){

                Entity.EntityTileInfo tileInfo = entity.getEntityTilesInfosIterator().next();

                try {

                    stream.write(tileInfo.getRessourceKey().getBytes());
                    stream.write(tileInfo.getPosition().getX());
                    stream.write(tileInfo.getPosition().getY());
                    stream.write(tileInfo.getId());

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }

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
        entity.removeObserver(this);
        this.entities.remove(entity.getEntityId());

    }

    @Override
    public void notifyStateChange(Entity entity, int what) {

        //TODO

    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {
        //TODO
        switch (what){
            case Entity.NEW_COVERED_POSITION:
                break;
            case Entity.NOT_COVER_POSITION_ANYMORE:
                break;
            case Entity.ONE_LESS_COVER_ON_POSITION:
                break;
        }
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
