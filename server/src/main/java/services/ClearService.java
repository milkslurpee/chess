package services;

import dataaccess.DataAccessException;
import dataaccess.db.*;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;

/**
 * The ClearService class provides a service for clearing data or operations.
 */
public class ClearService {
    dbAuthDAO authDAO;
    dbUserDAO userDAO;
    dbGameDAO gameDAO;
    public ClearService(dbAuthDAO authDAO, dbUserDAO userDAO, dbGameDAO gameDAO) {
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