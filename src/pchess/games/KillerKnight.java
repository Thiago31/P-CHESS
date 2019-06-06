package pchess.games;

import pchess.core.enums.PieceType;
import pchess.core.enums.PlayerType;

/**
 * Killer Knight game.
 */
public class KillerKnight extends AbstractKiller{

    /**
     * Constructor. Makes a new KillerKnight game.
     * @param p1 player type for white.
     * @param p2 player type for black. 
     * @param diamonds number of diamonds.
     */
    public KillerKnight(PlayerType p1, PlayerType p2, int diamonds) {
        super(p1, p2, PieceType.KNIGHT, diamonds, "Killer Knight");
    }

    @Override
    public PreChessGame newGame(){
        return new KillerKnight(whitePlayer.getPlayerType(),
                blackPlayer.getPlayerType(), getNumberOfDiamonds());
    }    
}
