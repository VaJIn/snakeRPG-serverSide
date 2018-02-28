package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.io.ByteSource;
import fr.vajin.snakerpg.gameroom.NewConnectionHandler;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.Receiver;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.util.Map;
import java.util.Scanner;

public class ReceiverImpl implements Receiver {

    final int idProtocol;
    Map<Integer, PlayerHandler> playerHandlerMap;
    NewConnectionHandler newConnectionHandler;

    public ReceiverImpl(int idProtocol, Map<Integer, PlayerHandler> playerHandlerMap, NewConnectionHandler newConnectionHandler) {
        this.idProtocol = idProtocol;
        this.playerHandlerMap = playerHandlerMap;
        this.newConnectionHandler = newConnectionHandler;
    }

    @Override
    public void receivePacket(DatagramPacket packet) {
        byte[] data = packet.getData();

        try {
            InputStream stream = ByteSource.wrap(data).openStream();
            Scanner scanner = new Scanner(stream);

            int idProtocol = scanner.nextInt();
            if (idProtocol != this.idProtocol) {
                return; //Discard the packet
            } else {
                //TODO

            }
        } catch (IOException e) {
        }
    }

    @Override
    public void setNewConnectionHandler(NewConnectionHandler newConnectionHandler) {

    }

    @Override
    public void addPlayerHandler(PlayerHandler handler) {

    }

    @Override
    public void removePlayerHandler(PlayerHandler handler) {

    }
}
