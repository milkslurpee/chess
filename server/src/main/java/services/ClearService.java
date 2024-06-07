package services;

import dataaccess.DataAccessException;
import dataaccess.db.*;

/**
 * The ClearService class provides a service for clearing data or operations.
 */
public class ClearService {
    DbAuthDAO authDAO;
    DbUserDAO userDAO;
    DbGameDAO gameDAO;
    public ClearService(DbAuthDAO authDAO, DbUserDAO userDAO, DbGameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Clears data or performs a specific operation.
     */
    public void clear() throws DataAccessException {
            authDAO.clear();
            gameDAO.clear();
            userDAO.clear();
    }
}