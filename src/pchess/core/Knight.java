package pchess.core;

import pchess.core.enums.PieceType;
import pchess.core.enums.PieceColor;

/**
 * Standard knight in chess game.
 */
class Knight extends Piece {

    /**
     * Vector that defines knight movements.
     */
    private static final int[][] KNIGHT_MOVE
            = { {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1} };

    /**
     * Constructor. Makes a new knight.
     *
     * @param pc knight color.
     */    
    public Knight(PieceColor pc){
        super(PieceType.KNIGHT, pc);
    }

    @Override
    public void defLegalMove() {
        defLegalMove(KNIGHT_MOVE, true);
    }

}
