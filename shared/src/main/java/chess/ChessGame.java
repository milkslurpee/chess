package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamColor;
    private ChessBoard board;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
// NOT NEEDED FOR PHASE 0
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamColor = team;
    }


    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (board.getPiece(startPosition) == null) {
            return null;
        }
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> potentialMoves = piece.pieceMoves(board, startPosition);
        Set<ChessMove> validMoves = new HashSet<>();
        TeamColor currentTeamColor = piece.getTeamColor();

        for (ChessMove move : potentialMoves) {
            // Temporarily make the move
            ChessPiece originalPiece = board.getPiece(move.getEndPosition());
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            board.addPiece(move.getStartPosition(), null);

            // Check if the king is not in check after the move
            if (!isInCheck(currentTeamColor)) {
                validMoves.add(move);
            }

            // Undo the move
            board.addPiece(move.getStartPosition(), board.getPiece(move.getEndPosition()));
            board.addPiece(move.getEndPosition(), originalPiece);
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */


    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece tempPiece = null;
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        if (getTeamTurn() != board.getPiece(move.getStartPosition()).getTeamColor()) {
            throw new InvalidMoveException("Not the correct team's turn");
        }
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (!validMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move");
        }
        else {
            if(move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                System.out.println("promotion to rook");
                board.addPiece(move.getEndPosition(), new Rook(teamColor));
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                System.out.println("promotion to queen");
                board.addPiece(move.getEndPosition(), new Queen(teamColor));
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                System.out.println("promotion to bishop");
                board.addPiece(move.getEndPosition(), new Bishop(teamColor));
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                System.out.println("promotion to knight");
                board.addPiece(move.getEndPosition(), new Knight(teamColor));
            } else{
                if(board.getPiece(move.getEndPosition()) != null){
                    tempPiece = board.getPiece(move.getEndPosition());
                }
                board.addPiece(move.getEndPosition(), piece);
            }
            board.addPiece(move.getStartPosition(), null);
            if(isInCheck(teamColor)){
                board.addPiece(move.getEndPosition(), tempPiece);
                board.addPiece(move.getStartPosition(), piece);
                throw new InvalidMoveException("Sorry, this move puts you in check");
            }
            if(getTeamTurn() == TeamColor.WHITE){
                teamColor = TeamColor.BLACK;
            }else{
                teamColor = TeamColor.WHITE;
            }
        }
        printBoard();
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = kingsPosition(teamColor);
        if (kingPosition == null) {
            return false; // King not found
        }

        TeamColor opponentTeam = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        Collection<ChessPosition> enemyPieceLocations = enemyPieces();

        for (ChessPosition enemyPosition : enemyPieceLocations) {
            ChessPiece enemyPiece = getBoard().getPiece(enemyPosition);
            Collection<ChessMove> enemyMoves = enemyPiece.pieceMoves(getBoard(), enemyPosition);

            for (ChessMove enemyMove : enemyMoves) {
                if (enemyMove.getEndPosition().equals(kingPosition)) {
                    return true; // The King is in check
                }
            }
        }

        return false; // The King is not in check
    }

    public ChessPosition kingsPosition(TeamColor teamColor){
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPosition);

                if (piece != null && piece.getTeamColor() == teamColor
                        && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    return currentPosition; // Found the allied King
                }
            }
        }
        return null;
    }


    public Set<ChessPosition> enemyPieces() {
        Set<ChessPosition> enemyPieceLocations = new HashSet<>();
        TeamColor currentTeamColor = getTeamTurn();

        // Iterate through the entire board
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPosition);

                if (piece != null && piece.getTeamColor() != currentTeamColor) {
                    enemyPieceLocations.add(currentPosition);
                }
            }
        }

        return enemyPieceLocations;
    }

    public Set<ChessPosition> alliedPieces() {
        Set<ChessPosition> alliedPieceLocations = new HashSet<>();
        TeamColor currentTeamColor = getTeamTurn();

        // Iterate through the entire board
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPosition);
                if (piece != null && piece.getTeamColor() == currentTeamColor) {
                    alliedPieceLocations.add(currentPosition);
                }
            }
        }

        return alliedPieceLocations;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition kingPosition = kingsPosition(teamColor);

        if (kingPosition == null) {
            return false;
        }

        if (!isInCheck(teamColor)) {
            return false;
        }

        // Get all possible moves for allied pieces
        Collection<ChessPosition> alliedPieceLocations = alliedPieces();

        for (ChessPosition fromPosition : alliedPieceLocations) {
            for (ChessMove move : validMoves(fromPosition)) {
                // Temporarily make the move
                ChessPiece originalPiece = board.getPiece(move.getEndPosition());
                board.addPiece(move.getEndPosition(), board.getPiece(fromPosition));
                board.addPiece(fromPosition, null);

                // Check if the King is still in check
                if (!isInCheck(teamColor)) {
                    // The King is no longer in check, so it's not checkmate
                    // Undo the move
                    board.addPiece(fromPosition, board.getPiece(move.getEndPosition()));
                    board.addPiece(move.getEndPosition(), originalPiece);
                    return false;
                }

                // Undo the move
                board.addPiece(fromPosition, board.getPiece(move.getEndPosition()));
                board.addPiece(move.getEndPosition(), originalPiece);
            }
        }

        // If no valid move can remove the check, it's checkmate
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(isInCheck(teamColor) || kingsPosition(teamColor) == null){
            return false;
        }
        Set<ChessPosition> allies = alliedPieces();
        for(ChessPosition position : allies) {
            ChessPiece piece = board.getPiece(position);
            Set<ChessMove> moves = (Set<ChessMove>) piece.pieceMoves(board, position);
            for (ChessMove move : moves) {
                board.addPiece(move.getEndPosition(), piece);
                board.addPiece(move.getStartPosition(), null);
                if (!isInCheck(teamColor)) {
                    return false;
                }
                board.addPiece(move.getEndPosition(), null);
                board.addPiece(move.getStartPosition(), piece);
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


    public void printBoard() {
        for (int row = 8; row >= 1; row--) {
            System.out.print(row + "   "); // Print the row number

            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPosition);

                if (piece == null) {
                    System.out.print(" - "); // Empty square
                } else {
                    // Use the appropriate piece symbol based on team color
                    if (piece.getTeamColor() == TeamColor.WHITE) {
                        System.out.print(" " + piece.getPieceType().getWhitePieceSymbol(piece.getPieceType()) + " ");
                    } else {
                        System.out.print(" " + piece.getPieceType().getBlackPieceSymbol(piece.getPieceType()) + " ");
                    }
                }
            }

            System.out.println();
        }

        System.out.println("\n     A  B  C  D  E  F  G  H\n\n\n"); // Print column labels
    }
}
