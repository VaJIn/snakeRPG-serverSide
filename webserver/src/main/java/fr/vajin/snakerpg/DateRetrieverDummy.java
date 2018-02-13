package fr.vajin.snakerpg;


import fr.vajin.snakerpg.beans.GameBeans;
import fr.vajin.snakerpg.beans.PlayerBean;

import java.util.Random;

public class DateRetrieverDummy implements DataRetriever {

    Random r = new Random();

    @Override
    public PlayerBean getPlayer(int id) {
        PlayerBean p = new PlayerBean();
        p.setId(id);
        p.setBestScore(r.nextInt());
        return p;
    }

    @Override
    public GameBeans getGame(int id) {
        GameBeans g = new GameBeans();
        g.setId(id);
        return g;
    }
}
