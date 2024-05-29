package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor teamColor;
    ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.teamColor = pieceColor;
        this.pieceType = type;
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
// NOT NEEDED FOR PHASE 0
    }



    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if(pieceType == PieceType.KING)
            return new King(teamColor).pieceMoves(board, myPosition);
        if(pieceType == PieceType.QUEEN)
            return new Queen(teamColor).pieceMoves(board, myPosition);
        if(pieceType == PieceType.BISHOP)
            return new Bishop(teamColor).pieceMoves(board, myPosition);
        if(pieceType == PieceType.KNIGHT)
            return new Knight(teamColor).pieceMoves(board, myPosition);
        if(pieceType == PieceType.ROOK)
            return new Rook(teamColor).pieceMoves(board, myPosition);
        if(pieceType == PieceType.PAWN)
            return new Pawn(teamColor).pieceMoves(board, myPosition);
        else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && pieceType == that.pieceType;
    }


    protected Collection<ChessMove> generateMoves(ChessBoard board, ChessPosition myPosition, int[][] directions) {
        Set<ChessMove> validMoves = new HashSet<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = column + direction[1];

            while (newRow > 0 && newRow < 9 && newCol > 0 && newCol < 9) {
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece newPiece = board.getPiece(newPosition);

                if (newPiece == null || newPiece.getTeamColor() != this.teamColor) {
                    validMoves.add(new ChessMove(myPosition, newPosition));
                }

                if (newPiece != null) {
                    break;
                }
                newRow += direction[0];
                newCol += direction[1];
            }
        }
        return validMoves;
    }

    public Collection<ChessMove> generateMove(ChessBoard board, ChessPosition myPosition, int[][] directions) {
        Set<ChessMove> validMoves= new HashSet<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        for(int[] direction : directions){
            int newRow = row + direction[0];
            int newCol = column + direction[1];

            if(newRow > 0 && newRow < 9 && newCol > 0 && newCol < 9){
                ChessPosition newPosition = new ChessPosition(newRow,newCol);
                ChessPiece newPiece = board.getPiece(newPosition);

                if(newPiece == null || newPiece.getTeamColor() != this.teamColor){
                    validMoves.add(new ChessMove(myPosition,newPosition));
                }
            }
        }
        return validMoves;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }
}

