package chess;

import java.util.Objects;

public class Move implements ChessMove{

    ChessPosition startingPos;

    ChessPosition endingPos;

    ChessPiece.PieceType promotionPiece;

    public Move(ChessPosition startingPos , ChessPosition endingPos){
        this.startingPos = startingPos;
        this.endingPos = endingPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(startingPos, move.startingPos) && Objects.equals(endingPos, move.endingPos) && promotionPiece == move.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingPos, endingPos, promotionPiece);
    }

    public Move(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.startingPos = startPosition;
        this.endingPos = endPosition;
        this.promotionPiece = promotionPiece;
    }

    @Override
    public ChessPosition getStartPosition() {
        return startingPos;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endingPos;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }
}
