package models;

import chess.ChessGame;

/**
 * The GameModel class represents a game model that includes information about a chess game.
 */
public class GameModel {
    /**
     * The unique identifier for the game.
     */
    private int gameID;

    /**
     * The username of the player using white chess pieces.
     */
    private String whiteUsername;

    /**
     * The username of the player using black chess pieces.
     */
    private String blackUsername;

    /**
     * The name or title of the game.
     */
    private String gameName;

    /**
     * The actual ChessGame instance representing the state of the chess game.
     */
    private ChessGame game;

    /**
     * Constructs a new GameModel with the provided parameters.
     *
     * @param gameID The unique identifier for the game.
     * @param whiteUsername The username of the player using white chess pieces.
     * @param blackUsername The username of the player using black chess pieces.
     * @param gameName The name or title of the game.
     * @param game The ChessGame instance representing the state of the chess game.
     */
    public GameModel(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
