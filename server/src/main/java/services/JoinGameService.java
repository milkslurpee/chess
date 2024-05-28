package services;

import chess.ChessGame;
import dataaccess.*;
import dataaccess.DataAccessException;
import models.Authtoken;
import models.GameModel;
import requests.JoinGameRequest;
import responses.joinGameResponse;

/**
 * The JoinGameService class provides a service for joining an existing game.
 */
public class JoinGameService {
    AuthDAO authDAO;
    UserDAO userDAO;
    GameDAO gameDAO;
    public JoinGameService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Joins an existing game based on the provided request.
     *
     * @param request The request containing details for joining the game.
     * @return A joinGameResponse indicating the success of the joining operation.
     */
    public joinGameResponse joinGame(JoinGameRequest request, String auth) {

        try {
            int gameID = request.getGameID();
            GameModel game = null;
            game = gameDAO.read(gameID);
            String username = request.getUserName();
            Authtoken authToken = new Authtoken(username, auth);
            ChessGame.TeamColor playerColor = request.getColor();

            if (game == null) {
                 return new joinGameResponse("Error: Game does not exist");
            }
            if(!authDAO.validToken(authToken)){
                return new joinGameResponse("Error: Unauthorized user");
            }
            if (game.getWhiteUsername() == null && playerColor == ChessGame.TeamColor.WHITE) {
                game.setWhiteUsername(username);
                gameDAO.insert(game);
            }
            else if (game.getBlackUsername() == null && playerColor == ChessGame.TeamColor.BLACK) {
                game.setBlackUsername(username);
                gameDAO.insert(game);
            }
            else if (playerColor == null) {
                if (game.getWhiteUsername() == null) {
                    game.setWhiteUsername(username); // The first observer takes the white side
                } else if (game.getBlackUsername() == null) {
                    game.setBlackUsername(username); // The second observer takes the black side
                }
                System.out.println("User joined as observer");
                return new joinGameResponse(null);
            }
            else if(game.getWhiteUsername() != null && game.getBlackUsername() != null){
                return new joinGameResponse("Error: Game is full");
            }

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return new joinGameResponse(null);
    }
}