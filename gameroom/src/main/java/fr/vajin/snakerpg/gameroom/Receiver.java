package fr.vajin.snakerpg.gameroom;

public interface Receiver {

    void setNewConnectionHandler(NewConnectionHandler newConnectionHandler);

    void addPlayerHandler(PlayerHandler handler);

    void removePlayerHandler(PlayerHandler handler);

}
