package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Knight extends ChessPiece{

    public Knight(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {{2,1},{1,2},{-2,1},{-1,2},{2,-1},{1,-2},{-2,-1},{-1,-2}};
        return generateMove(board, myPosition, directions);
    }
}
