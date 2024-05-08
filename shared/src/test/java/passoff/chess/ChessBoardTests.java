package passoff.chess;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static passoff.chess.TestUtilities.loadBoard;

public class ChessBoardTests {

    @Test
    @DisplayName("Add and Get Piece")
    public void getAddPiece() {
        ChessPosition position = new ChessPosition(4, 4);
        ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);

        var board = new ChessBoard();
        board.addPiece(position, piece);

        ChessPiece foundPiece = board.getPiece(position);

        Assertions.assertEquals(piece.getPieceType(), foundPiece.getPieceType(),
                "ChessPiece returned by getPiece had the wrong piece type");
        Assertions.assertEquals(piece.getTeamColor(), foundPiece.getTeamColor(),
                "ChessPiece returned by getPiece had the wrong team color");
    }


    @Test
    @DisplayName("Reset Board")
    public void defaultGameBoard() {
        var expectedBoard = loadBoard("""
                |r|n|b|q|k|b|n|r|
                |p|p|p|p|p|p|p|p|
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                |P|P|P|P|P|P|P|P|
                |R|N|B|Q|K|B|N|R|
                """);

        var actualBoard = new ChessBoard();
        actualBoard.resetBoard();

        printBoard(actualBoard);
        System.out.println("\n\n");
        printBoard(expectedBoard);

        Assertions.assertEquals(expectedBoard, actualBoard);
    }

    public void printBoard(ChessBoard board) {
        for (int row = 8; row >= 1; row--) {
            System.out.print(row + "   "); // Print the row number

            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPosition);

                if (piece == null) {
                    System.out.print(" - "); // Empty square
                } else {
                    // Use the appropriate piece symbol based on team color
                    if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
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


    @Test
    @DisplayName("Piece Move on All Pieces")
    public void pieceMoveAllPieces() {
        var board = new ChessBoard();
        board.resetBoard();
        for(int i = 1; i <= 8; i++) {
            for(int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if(piece != null) Assertions.assertDoesNotThrow(() -> piece.pieceMoves(board, position));
            }
        }
    }

}