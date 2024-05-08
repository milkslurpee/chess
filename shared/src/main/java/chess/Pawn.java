package chess;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Pawn extends ChessPiece{

    ChessGame.TeamColor teamColor;

    public Pawn(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> validMoves = new HashSet<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        int forwardDirection;

        if (teamColor == ChessGame.TeamColor.WHITE) {
            forwardDirection = 1;
        } else {
            forwardDirection = -1;
        }

        int newRow = row + forwardDirection;
        int newCol = column;

        if (newRow > 0 && newRow < 9 && newCol > 0 && newCol < 9) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece newPiece = board.getPiece(newPosition);

            if ((newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)
                    || (newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)) {
                // Handle pawn promotion here
                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN)); // Promote to Queen by default
                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK)); // Promote to Rook
                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP)); // Promote to Bishop
                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT)); // Promote to Knight
            }

            if (newPiece == null) {
                // System.out.println("\n56\n");
                if((newPosition.getRow() != 8 && newPosition.getRow() != 1) && new ChessMove(myPosition, newPosition).getPromotionPiece() == null)
                    validMoves.add(new ChessMove(myPosition, newPosition));

                if ((row == 2 && teamColor == ChessGame.TeamColor.WHITE)
                        || (row == 7 && teamColor == ChessGame.TeamColor.BLACK)) {
                    int doubleMove = row + (forwardDirection * 2);
                    ChessPosition doublePosition = new ChessPosition(doubleMove, newCol);
                    ChessPiece doublePiece = board.getPiece(doublePosition);

                    if (doublePiece == null)
                        //System.out.println("\n65\n");
                        validMoves.add(new ChessMove(myPosition, doublePosition));
                }
            }

            newRow = row + forwardDirection;
            newPosition = new ChessPosition(newRow, newCol);
            if (newCol == 1) {
                newPosition = new ChessPosition(newRow, newCol + 1);
                if (newPosition.getColumn() > 0 && newPosition.getColumn() < 9) {
                    newPiece = board.getPiece(newPosition);
                    if(newPiece != null) {
                        if (newPiece.getTeamColor() != this.teamColor) {
                            if ((newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)
                                    || (newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)) {
                                // Handle pawn promotion here
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN)); // Promote to Queen by default
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK)); // Promote to Rook
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP)); // Promote to Bishop
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT)); // Promote to Knight
                            }
                            //System.out.println("\n76\n");
                            if ((newPosition.getRow() != 8 && newPosition.getRow() != 1) && new ChessMove(myPosition, newPosition).getPromotionPiece() == null)
                                validMoves.add(new ChessMove(myPosition, newPosition));
                        }
                    }
                }
            }
            if (newCol == 8) {
                newPosition = new ChessPosition(newRow, newCol - 1);
                if (newPosition.getColumn() > 0 && newPosition.getColumn() < 9) {
                    newPiece = board.getPiece(newPosition);
                    if(newPiece != null) {
                        if (newPiece.getTeamColor() != this.teamColor) {
                            if ((newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)
                                    || (newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)) {
                                // Handle pawn promotion here
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN)); // Promote to Queen by default
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK)); // Promote to Rook
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP)); // Promote to Bishop
                                validMoves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT)); // Promote to Knight
                            }

                            //System.out.println("\n84\n");
                            if ((newPosition.getRow() != 8 && newPosition.getRow() != 1) && new ChessMove(myPosition, newPosition).getPromotionPiece() == null)
                                validMoves.add(new ChessMove(myPosition, newPosition));
                        }
                    }
                }
            } else {
                ChessPosition leftTakeover = new ChessPosition(newRow, newCol - 1);
                ChessPosition rightTakeover = new ChessPosition(newRow, newCol + 1);
                if (leftTakeover.getColumn() > 0 && leftTakeover.getColumn() < 9) {
                    ChessPiece leftPiece = board.getPiece(leftTakeover);
                    if (leftPiece != null && leftPiece.getTeamColor() != this.teamColor) {
                        if ((newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)
                                || (newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)) {
                            // Handle pawn promotion here
                            validMoves.add(new ChessMove(myPosition, leftTakeover, PieceType.QUEEN)); // Promote to Queen by default
                            validMoves.add(new ChessMove(myPosition, leftTakeover, PieceType.ROOK)); // Promote to Rook
                            validMoves.add(new ChessMove(myPosition, leftTakeover, PieceType.BISHOP)); // Promote to Bishop
                            validMoves.add(new ChessMove(myPosition, leftTakeover, PieceType.KNIGHT)); // Promote to Knight
                        }

                        //System.out.println("\n92\n");
                        if((newPosition.getRow() != 8 && newPosition.getRow() != 1) && new ChessMove(myPosition, newPosition).getPromotionPiece() == null)
                            validMoves.add(new ChessMove(myPosition, leftTakeover));
                    }
                }
                if (rightTakeover.getColumn() > 0 && rightTakeover.getColumn() < 9) {
                    ChessPiece rightPiece = board.getPiece(rightTakeover);
                    if (rightPiece != null && rightPiece.getTeamColor() != this.teamColor) {
                        if ((newRow == 1 && teamColor == ChessGame.TeamColor.BLACK)
                                || (newRow == 8 && teamColor == ChessGame.TeamColor.WHITE)) {
                            // Handle pawn promotion here
                            validMoves.add(new ChessMove(myPosition, rightTakeover, PieceType.QUEEN)); // Promote to Queen by default
                            validMoves.add(new ChessMove(myPosition, rightTakeover, PieceType.ROOK)); // Promote to Rook
                            validMoves.add(new ChessMove(myPosition, rightTakeover, PieceType.BISHOP)); // Promote to Bishop
                            validMoves.add(new ChessMove(myPosition, rightTakeover, PieceType.KNIGHT)); // Promote to Knight
                        }
                        //System.out.println("\n98\n");
                        if((newPosition.getRow() != 8 && newPosition.getRow() != 1) && new ChessMove(myPosition, newPosition).getPromotionPiece() == null)
                            validMoves.add(new ChessMove(myPosition, rightTakeover));
                    }
                }
            }
        }
        return validMoves;
    }
}
