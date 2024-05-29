package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Pawn extends ChessPiece {


    public Pawn(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> validMoves = new HashSet<>();
        int row = myPosition.row;
        int col = myPosition.column;
        int forwardDirection;
        if(teamColor == ChessGame.TeamColor.BLACK)
            forwardDirection = -1;
        else forwardDirection = 1;
        int newRow = row + forwardDirection;
        ChessPosition newPosition = new ChessPosition(newRow, col);
        ChessPiece newPiece = board.getPiece(newPosition);

        if(newRow > 0 && newRow < 9) {
            if (newPiece == null){
                if((newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)
                        || (newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)){
                    validMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    validMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                }
                else validMoves.add(new ChessMove(myPosition, newPosition));
                if((row == 2 && teamColor == ChessGame.TeamColor.WHITE)
                        || (row == 7 && teamColor == ChessGame.TeamColor.BLACK)){
                    ChessPosition doubleMove = new ChessPosition(row + forwardDirection*2, col);
                    ChessPiece doublePiece = board.getPiece(doubleMove);
                    if (doublePiece == null)
                        validMoves.add(new ChessMove(myPosition, doubleMove));
                }
            }

            if(col > 1 && col < 8) {
                ChessPosition leftPos = new ChessPosition(newRow, col - 1);
                ChessPiece leftTakeover = board.getPiece(leftPos);
                if(leftTakeover != null && leftTakeover.getTeamColor() != teamColor){
                    if((newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)
                            || (newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)){
                        validMoves.add(new ChessMove(myPosition, leftPos, PieceType.QUEEN));
                        validMoves.add(new ChessMove(myPosition, leftPos, PieceType.BISHOP));
                        validMoves.add(new ChessMove(myPosition, leftPos, PieceType.ROOK));
                        validMoves.add(new ChessMove(myPosition, leftPos, PieceType.KNIGHT));
                    }
                    else validMoves.add(new ChessMove(myPosition, leftPos));
                }

                ChessPosition rightPos = new ChessPosition(newRow, col + 1);
                ChessPiece rightTakeover = board.getPiece(rightPos);
                if(rightTakeover != null && rightTakeover.getTeamColor() != teamColor){
                    if((newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)
                            || (newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)){
                        validMoves.add(new ChessMove(myPosition, rightPos, PieceType.QUEEN));
                        validMoves.add(new ChessMove(myPosition, rightPos, PieceType.BISHOP));
                        validMoves.add(new ChessMove(myPosition, rightPos, PieceType.ROOK));
                        validMoves.add(new ChessMove(myPosition, rightPos, PieceType.KNIGHT));
                    }
                    else validMoves.add(new ChessMove(myPosition, rightPos));
                }

            }
        }
        return validMoves;
    }
}