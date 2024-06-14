import chess.*;
import ui.ClientUi;

public class Main {
    public static void main(String[] args)  {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client HELLO" + piece);
        ClientUi client = new ClientUi();
        client.startMenu();
    }
}