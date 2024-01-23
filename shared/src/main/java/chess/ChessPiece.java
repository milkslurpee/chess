package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * 
 * Note: You can add to this interface, but you should not alter the existing
 * methods.
 */
public interface ChessPiece {

    /**
     * The various different chess piece options
     */
    enum PieceType {
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
    ChessGame.TeamColor getTeamColor();

    /**
     * @return which type of chess piece this piece is
     */
    PieceType getPieceType();

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     * 
     * @return Collection of valid moves
     */
    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}
