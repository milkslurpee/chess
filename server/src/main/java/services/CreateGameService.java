package services;

import chess.ChessGame;
import dataaccess.*;
import models.GameModel;
import requests.CreateGameRequest;
import responses.createGameResponse;

/**
 * The CreateGameService class provides a service for creating a new game.
 */

public class CreateGameService {
    AuthDAO authDAO;
    UserDAO userDAO;
    GameDAO gameDAO;
    public CreateGameService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Creates a new game based on the provided request.
     *
     * @param request The request containing details for creating the game.
     * @return A createGameResponse indicating the success of the creation operation.
     */
    public createGameResponse createGame(CreateGameRequest request) {
        String gameName = request.getGameName();

        int newGameID = generateUniqueGameID();

        ChessGame newChessGame = new ChessGame();

        GameModel newGame = new GameModel(newGameID, "White Player", "Black Player", gameName, newChessGame);

        try {
            GameDAO gameDAO = new GameDAO();
            gameDAO.insert(newGame);
            return new createGameResponse(null);
        } catch (DataAccessException e) {
            return new createGameResponse("Failed to create the game");
        }
    }

    // Method to generate a unique game ID
    private int generateUniqueGameID() {
        return (int) (Math.random() * 1000);
    }
}