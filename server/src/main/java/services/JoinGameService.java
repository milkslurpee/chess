package services;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.DataAccessException;
import models.Authtoken;
import models.GameModel;
import requests.JoinGameRequest;
import responses.joinGameResponse;

import java.util.Objects;

/**
 * The JoinGameService class provides a service for joining an existing game.
 */
public class JoinGameService {
    AuthDAO authDAO;
    UserDAO userDAO;
    GameDAO gameDAO;
    ServiceUtils utils = new ServiceUtils();
    GameModel gameToBeJoined;


    /**
     * Joins an existing game based on the provided request.
     *
     * @param request The request containing details for joining the game.
     * @return A joinGameResponse indicating the success of the joining operation.
     */
    public void join(GameDAO games, AuthDAO authTokens, String authToken, JoinGameRequest request) throws DataAccessException {

        utils.verifyAuth(authTokens, authToken);
        games.read(request.gameID());
        gameToBeJoined = games.read(request.gameID());
        if (Objects.equals(request.gameID(), "0") || request.playerColor()==null){
            throw new DataAccessException("bad request");
        }
        if(!(request.playerColor().equals("WHITE") || request.playerColor().equals("BLACK"))){
            throw new DataAccessException("bad request");
        }

        Authtoken userJoingAuth = authTokens.read(authToken);
        GameModel updatedGame = getUpdatedGame(request, userJoingAuth);
        games.updateGame(gameToBeJoined.getGameID(), updatedGame);
    }

    private GameModel getUpdatedGame(JoinGameRequest request, Authtoken userJoingAuth) throws DataAccessException {
        String newPlayer = userJoingAuth.getUserName();

        GameModel updatedGame;
        if(request.playerColor().equals("WHITE")){
            if (gameToBeJoined.getWhiteUsername() != null){
                throw new DataAccessException("already taken");
            }
            updatedGame = new GameModel(gameToBeJoined.getGameID(), newPlayer, gameToBeJoined.getBlackUsername(), gameToBeJoined.getBlackUsername(), gameToBeJoined.getGame());
        }
        else{
            if (gameToBeJoined.getBlackUsername() != null){
                throw new DataAccessException("already taken");
            }
            updatedGame = new GameModel(gameToBeJoined.getGameID(), gameToBeJoined.getWhiteUsername(), newPlayer, gameToBeJoined.getGameName(), gameToBeJoined.getGame());
        }
        return updatedGame;
    }
}