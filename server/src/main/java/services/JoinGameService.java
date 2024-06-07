package services;

import dataaccess.DataAccessException;
import dataaccess.db.*;
import models.Authtoken;
import models.GameModel;
import requests.JoinGameRequest;

import java.util.Objects;

/**
 * The JoinGameService class provides a service for joining an existing game.
 */
public class JoinGameService {
    dbAuthDAO authDAO;
    dbUserDAO userDAO;
    dbGameDAO gameDAO;
    GameModel gameToBeJoined;


    /**
     * Joins an existing game based on the provided request.
     *
     * @param request The request containing details for joining the game.
     * @return A joinGameResponse indicating the success of the joining operation.
     */
    public void join(dbGameDAO games, dbAuthDAO authTokens, String authToken, JoinGameRequest request) throws DataAccessException {
        if (request.gameID() == -1) {
            throw new DataAccessException("bad request");
        }

        // Validate auth token
        Authtoken userJoingAuth = authTokens.read(authToken);
        String newPlayer = userJoingAuth.getUserName();

        // Read the game from the database
        GameModel gameToBeJoined = games.read(request.gameID());
        if (gameToBeJoined == null) {
            throw new DataAccessException("bad request");
        }

        // Validate player color
        if (request.playerColor() == null || !(request.playerColor().equals("WHITE") || request.playerColor().equals("BLACK"))) {
            throw new DataAccessException("bad request");
        }

        // Update the game model with the new player
        if (request.playerColor().equals("WHITE")) {
            if (gameToBeJoined.getWhiteUsername() != null) {
                throw new DataAccessException("already taken");
            }
            gameToBeJoined.setWhiteUsername(newPlayer);
        } else {
            if (gameToBeJoined.getBlackUsername() != null) {
                throw new DataAccessException("already taken");
            }
            gameToBeJoined.setBlackUsername(newPlayer);
        }

        // Save the updated game model to the database
        games.updateGame(gameToBeJoined.getGameID(), gameToBeJoined);
    }

}