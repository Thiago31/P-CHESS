package pchess.core;

import pchess.core.enums.PieceType;
import pchess.core.enums.PieceColor;

/**
 * Standard rook in chess game.
 */
class Rook extends Piece {

    /**
     * Vector that defines rock movements.
     */
    private static final int[][] ROCK_MOVE
            = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * Constructor. Makes a new rock.
     *
     * @param pc rook color.
     */
    public Rook(PieceColor pc){
        super(PieceType.ROCK, pc);
    }
           

    @Override
    public void defLegalMove() {
        defLegalMove(ROCK_MOVE);
    }
}
