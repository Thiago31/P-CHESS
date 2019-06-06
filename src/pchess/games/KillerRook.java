package pchess.games;

import pchess.core.enums.PieceType;
import pchess.core.enums.PlayerType;

/**
 * Killer Rock game.
 */
public class KillerRook extends AbstractKiller {

    /**
     * Constructor. Makes a new KillerRook game.
     *
     * @param p1 player type for white.
     * @param p2 player type for black.
     * @param diamonds number of diamonds.
     */
    public KillerRook(PlayerType p1, PlayerType p2, int diamonds) {
        super(p1, p2, PieceType.ROCK, diamonds, "Killer Rook");
    }

    @Override
    public PreChessGame newGame(){
        return new KillerRook(whitePlayer.getPlayerType(),
                blackPlayer.getPlayerType(), getNumberOfDiamonds());
    }
    
}
