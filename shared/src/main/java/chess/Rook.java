package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import chess.Queen;

public class Rook extends ChessPiece{

    public Rook(ChessGame.TeamColor teamColor){this.teamColor = teamColor;}


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        return generateMoves(board, myPosition, directions);
    }
}
