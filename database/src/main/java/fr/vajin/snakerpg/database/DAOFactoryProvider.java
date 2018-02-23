package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.daoimpl.DAOFactoryImpl;

public class DAOFactoryProvider {

    private static DAOFactory factory = new DAOFactoryImpl();

    public static DAOFactory getDAOFactory() {
        return factory;
    }

}
