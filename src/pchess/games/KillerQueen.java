package pchess.games;

import pchess.core.enums.PieceType;
import pchess.core.enums.PlayerType;

/**
 * Killer Queen game.
 */
public class KillerQueen extends AbstractKiller {

    /**
     * Constructor. Makes a new KillerQueen game.
     *
     * @param p1 player type for white.
     * @param p2 player type for black.
     * @param diamonds number of diamonds.
     */
    public KillerQueen(PlayerType p1, PlayerType p2, int diamonds) {
        super(p1, p2, PieceType.QUEEN, diamonds, "Killer Queen");
    }

    @Override
    public PreChessGame newGame() {
        return new KillerQueen(whitePlayer.getPlayerType(),
                blackPlayer.getPlayerType(), getNumberOfDiamonds());
    }

}
