package requests;

import chess.ChessGame;

/**
 * The JoinGameRequest class represents a request to join a game with a specified team color and game ID.
 */
public class JoinGameRequest {
    /**
     * The team color (e.g., white or black) that the player wants to join as.
     */
    private ChessGame.TeamColor color;

    /**
     * The unique identifier of the game to join.
     */
    private int gameID;

    private String userName;

    /**
     * Constructs a new JoinGameRequest with the provided team color and game ID.
     *
     * @param color The team color (e.g., white or black) that the player wants to join as.
     * @param gameID The unique identifier of the game to join.
     */
    public JoinGameRequest(ChessGame.TeamColor color, int gameID, String userName) {
        this.color = color;
        this.gameID = gameID;
        this.userName = userName;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }

    public int getGameID() {
        return gameID;
    }

    public String getUserName() {return userName;}

    public void setColor(ChessGame.TeamColor color) {
        this.color = color;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setUserName(String userName){this.userName = userName;}
}