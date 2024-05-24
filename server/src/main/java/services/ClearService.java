package services;

import dataaccess.*;
import responses.clearResponse;

/**
 * The ClearService class provides a service for clearing data or operations.
 */
public class ClearService {
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    public ClearService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Clears data or performs a specific operation.
     */
    public void clear() {
        try {
            authDAO.clear();
            gameDAO.clear();
            userDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}