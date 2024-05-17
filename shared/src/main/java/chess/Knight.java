package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Knight extends ChessPiece{

    public Knight(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> validMoves= new HashSet<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        int[][] directions = {{2,1},{1,2},{-2,1},{-1,2},{2,-1},{1,-2},{-2,-1},{-1,-2}};

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
}
