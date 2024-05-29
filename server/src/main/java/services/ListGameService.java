package services;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import models.GameModel;
import responses.listResponse;

import java.util.List;
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
        List<GameModel> GameList;
        GameList = gameDAO.getGameList();
        return new listResponse(null, GameList);
    }
}