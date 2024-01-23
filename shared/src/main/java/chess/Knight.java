package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Knight implements ChessPiece{

    ChessGame.TeamColor teamColor;
    public Knight(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> validMoves= new HashSet<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        int[][] directions = {{2,1},{1,2},{-2,1},{-1,2},{2,-1},{1,-2},{-2,-1},{-1,-2}};
        for(int[] direction : directions){
            int newRow = row + direction[0];
            int newCol = column + direction[1];

            if(newRow > 0 && newRow < 9 && newCol > 0 && newCol < 9){
                ChessPosition newPosition = new Position(newRow,newCol);
                ChessPiece newPiece = board.getPiece(newPosition);

                if(newPiece == null || newPiece.getTeamColor() != this.teamColor){
                    validMoves.add(new Move(myPosition,newPosition));
                }
            }
        }
        return validMoves;
    }
}
