package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Bishop extends ChessPiece{
    //hello beans

    public Bishop(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {{-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
        return generateMoves(board, myPosition, directions);
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
}
