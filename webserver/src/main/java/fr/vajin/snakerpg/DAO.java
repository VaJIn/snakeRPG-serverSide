package fr.vajin.snakerpg;

import fr.vajin.snakerpg.database.DataBaseAccess;
import fr.vajin.snakerpg.database.test.TestDataBaseAccess;

public class DAO {

    private DataBaseAccess accessor;

    private static DAO ourInstance = new DAO();

    public static DAO getInstance() {
        return ourInstance;
    }

    private DAO() {
        accessor = new TestDataBaseAccess();
    }

    public DataBaseAccess getAccessor() {
        return accessor;
    }
}