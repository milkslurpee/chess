package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Bishop extends ChessPiece{
    //hello

    public Bishop(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        Set<ChessMove> validMoves= new HashSet<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        int[][] directions = {{-1,1},{1,1},{1,-1},{-1,-1}};

        for(int[] diagonal : directions){
            int newRow = row + diagonal[0];
            int newCol = column + diagonal[1];

            while(newRow > 0 && newRow < 9 && newCol > 0 && newCol < 9){
                ChessPosition newPosition = new ChessPosition(newRow,newCol);
                ChessPiece newPiece = board.getPiece(newPosition);

                if(newPiece == null || newPiece.getTeamColor() != this.teamColor){
                    validMoves.add(new ChessMove(myPosition,newPosition));
                }

                if(newPiece != null){
                    break;
                }
                newRow += diagonal[0];
                newCol += diagonal[1];
            }
        }
        return validMoves;
    }
}
