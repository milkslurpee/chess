package services;
import dataaccess.GameDAO;
import models.GameModel;
import responses.ListResponse;

import java.util.List;

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
    public ListResponse list() {
        List<GameModel> GameList;
        GameList = gameDAO.getGameList();
        return new ListResponse(null, GameList);
    }
}