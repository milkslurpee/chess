package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
    }

    public ChessPiece() {

    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN;

        public String getWhitePieceSymbol(PieceType piece) {
            if(piece == KING)
                return "K";
            if(piece == QUEEN)
                return "Q";
            if(piece == BISHOP)
                return "B";
            if(piece == KNIGHT)
                return "N";
            if(piece == ROOK)
                return "R";
            if(piece == PAWN)
                return "P";
            else return null;
        }

        public String getBlackPieceSymbol(PieceType piece) {
            if(piece == KING)
                return "k";
            if(piece == QUEEN)
                return "q";
            if(piece == BISHOP)
                return "b";
            if(piece == KNIGHT)
                return "n";
            if(piece == ROOK)
                return "r";
            if(piece == PAWN)
                return "p";
            else return null;
        }
    }



    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }
}
