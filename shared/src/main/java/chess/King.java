package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class King extends ChessPiece{

    public King(ChessGame.TeamColor teamColor){
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
        return generateMove(board, myPosition, directions);
    }
}
