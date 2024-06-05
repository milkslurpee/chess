package services;
import dataaccess.memory.MemoryGameDAO;
import models.GameModel;
import responses.ListResponse;

import java.util.List;

/**
 * The ListGameService class creates a service for listing all the games.
 */
public class ListGameService {
    MemoryGameDAO gameDAO;
    public ListGameService(MemoryGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    /**
     * Lists all the games.
     *
     * @return A listResponse indicating the success of the listing operation.
     */
    public ListResponse list() {
        List<GameModel> gameList;
        gameList = gameDAO.getGameList();
        return new ListResponse(null, gameList);
    }
}