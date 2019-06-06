package pchess.core;

import pchess.core.enums.PieceType;
import pchess.core.enums.PieceColor;

/**
 * Standard king in chess game.
 */
public class King extends Piece {

    /**
     * Vector that defines king movements.
     */
    private static final int[][] KING_MOVE
            = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
            {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    /**
     * Constructor. Makes a new king.
     *
     * @param pc king color.
     */
    public King(PieceColor pc){
        super(PieceType.KING, pc);
    }

    @Override
    public void defLegalMove() {
        defLegalMove(KING_MOVE, true);
        findKingForbiddenMove();
    }

    /**
     * Defines king moves without to worry about opponent pieces.
     */
    private void defLegalMove0() {
        defLegalMove(KING_MOVE, true);
    }

    /**
     * King can't occupy square attacked by opponent piece. Removes attacked
     * squares from king legal move list.
     */
    private void findKingForbiddenMove() {
        Piece[] opponents;
        if(getPieceColor() == PieceColor.WHITE){
            opponents = getSquare().getChessBoard().getBlackPieces();
        } else{
            opponents = getSquare().getChessBoard().getWhitePieces();
        }
        for (Piece piece : opponents) {
            if(piece.isCaptured()){continue;}
            if(!piece.isLiving()){continue;}
            if (piece instanceof King) {
                King king = (King) piece;
                king.defLegalMove0();
            } else {
                piece.defLegalMove();
            }
            for (Square square : piece.getLegalMoves()) {
                if (legalMove.contains(square) && 
                        piece.getPieceType() != PieceType.PAWN) {
                    legalMove.remove(square);
                }
            }
            for(Square square : piece.getProtectedSquares()){
                if(legalMove.contains(square)){
                    legalMove.remove(square);
                }
            }
        }
    }

}
