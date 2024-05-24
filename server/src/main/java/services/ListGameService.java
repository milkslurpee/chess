package services;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import models.GameModel;
import responses.listResponse;

import java.util.Map;

/**
 * The ListGameService class creates a service for listing all the games.
 */
public class ListGameService {
    GameDAO gameDAO;
    public ListGameService(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    /**
     * Lists all the games.
     *
     * @return A listResponse indicating the success of the listing operation.
     */
    public listResponse list() {
        Map<Integer, GameModel> map;
        try {
            map = gameDAO.getGameMap();
        } catch (DataAccessException e) {
            return new listResponse(false, "No games to list", null);
        }
        return new listResponse(true, "Games listed successfully", map);
    }
}