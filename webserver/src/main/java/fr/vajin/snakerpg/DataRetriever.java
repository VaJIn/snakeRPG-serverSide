package fr.vajin.snakerpg;


import fr.vajin.snakerpg.beans.GameBeans;
import fr.vajin.snakerpg.beans.PlayerBean;

public interface DataRetriever {

    PlayerBean getPlayer(int id);

    GameBeans getGame(int id);

}
