package pchess.core;

import pchess.core.enums.PieceType;
import pchess.core.enums.PieceColor;

/**
 * Standard queen in chess game.
 */
public class Queen extends Piece {

    /**
     * Vector that defines queen movements.
     */
    private static final int[][] QUEEN_MOVE
            = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
            {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    /**
     * Constructor. Makes a new queen.
     *
     * @param pc queen color.
     */
    public Queen(PieceColor pc){
        super(PieceType.QUEEN, pc);
    }
            

    @Override
    public void defLegalMove() {
        defLegalMove(QUEEN_MOVE);
    }

}
