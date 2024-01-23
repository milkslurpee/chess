package chess;

import chess.*;

public class Board implements ChessBoard{

    private ChessPiece[][] chessBoard = new ChessPiece[8][8];
    public Board(){

    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard[position.getRow()-1][position.getColumn()-1];
    }

    @Override
    public void resetBoard() {
        chessBoard[7][0] = new Rook(ChessGame.TeamColor.BLACK);
        chessBoard[7][1] = new Knight(ChessGame.TeamColor.BLACK);
        chessBoard[7][2] = new Bishop(ChessGame.TeamColor.BLACK);
        chessBoard[7][3] = new Queen(ChessGame.TeamColor.BLACK);
        chessBoard[7][4] = new King(ChessGame.TeamColor.BLACK);
        chessBoard[7][5] = new Bishop(ChessGame.TeamColor.BLACK);
        chessBoard[7][6] = new Knight(ChessGame.TeamColor.BLACK);
        chessBoard[7][7] = new Rook(ChessGame.TeamColor.BLACK);

        for(int i = 0; i < 8; i++){
            chessBoard[6][i] = new Pawn(ChessGame.TeamColor.BLACK);
        }

        for(int i = 5; i > 1; i--){
            for(int j = 0; j < 8; j++){
                chessBoard[i][j] = null;
            }
        }

        for(int i = 0; i < 8; i++){
            chessBoard[1][i] = new Pawn(ChessGame.TeamColor.WHITE);
        }

        chessBoard[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        chessBoard[0][1] = new Knight(ChessGame.TeamColor.WHITE);
        chessBoard[0][2] = new Bishop(ChessGame.TeamColor.WHITE);
        chessBoard[0][3] = new Queen(ChessGame.TeamColor.WHITE);
        chessBoard[0][4] = new King(ChessGame.TeamColor.WHITE);
        chessBoard[0][5] = new Bishop(ChessGame.TeamColor.WHITE);
        chessBoard[0][6] = new Knight(ChessGame.TeamColor.WHITE);
        chessBoard[0][7] = new Rook(ChessGame.TeamColor.WHITE);
    }
}
