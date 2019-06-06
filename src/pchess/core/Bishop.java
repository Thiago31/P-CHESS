package pchess.core;

import pchess.core.enums.PieceType;
import pchess.core.enums.PieceColor;

/**
 * Standard bishop in chess game.
 */
class Bishop extends Piece {

    /**
     * Vector that defines bishop movements.
     */
    private static final int[][] BISHOP_MOVE
            = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    /**
     * Constructor. Makes a new bishop.
     *
     * @param pc bishop color.
     */
    public Bishop(PieceColor pc){
        super(PieceType.BISHOP, pc);
    }

    @Override
    public void defLegalMove() {
        defLegalMove(BISHOP_MOVE);
    }

}
