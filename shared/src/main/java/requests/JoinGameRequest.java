package requests;

import chess.ChessGame;

/**
 * The JoinGameRequest class represents a request to join a game with a specified team color and game ID.
 */
public record JoinGameRequest(String playerColor, int gameID) {
}