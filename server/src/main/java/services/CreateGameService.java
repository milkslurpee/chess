package services;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.db.DbAuthDAO;
import dataaccess.db.DbGameDAO;
import dataaccess.db.DbUserDAO;
import models.GameModel;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * The CreateGameService class provides a service for creating a new game.
 */

public class CreateGameService {
    DbAuthDAO authDAO;
    DbUserDAO userDAO;
    DbGameDAO gameDAO;
    public CreateGameService(DbAuthDAO authDAO, DbUserDAO userDAO, DbGameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Creates a new game based on the provided request.
     *
     * @param authToken The authorization token.
     * @param request   The request containing details for creating the game.
     * @return A createGameResponse indicating the success of the creation operation.
     */
    public CreateGameResponse createGame(String authToken, CreateGameRequest request) throws DataAccessException {

        try {
            authDAO.read(authToken);
        } catch (DataAccessException e) {
            return new CreateGameResponse("Error: unauthorized");
        }

        String gameName = request.getGameName();
        if (gameName == null || gameName.isEmpty()) {
            return new CreateGameResponse("Error: bad request");
        }

        int newGameID = generateUniqueGameID();
        ChessGame newChessGame = new ChessGame();
        GameModel newGame = new GameModel(newGameID, null, null, gameName, newChessGame);

        try {
            gameDAO.insert(newGame);
            return new CreateGameResponse(newGameID);
        } catch (DataAccessException e) {
            return new CreateGameResponse("Error: failed to create the game");
        }
    }

    // Method to generate a unique game ID
    private int generateUniqueGameID() {
        return (int) (Math.random() * 1000);
    }
}
