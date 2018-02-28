import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.daoimpl.DAOFactoryImpl;

public class DAOFactoryProvider {

    private static DAOFactory factory = new DAOFactoryImpl();

    public static DAOFactory getDAOFactory() {
        return factory;
    }

}
