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

}
