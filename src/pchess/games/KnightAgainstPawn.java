package pchess.games;

import pchess.core.enums.PieceType;
import pchess.core.enums.PlayerType;
import pchess.core.enums.PieceColor;

/**
 * Knights Against Pawns game.
 */
public class KnightAgainstPawn extends MinorPieceAgainstPawn {

    /**
     * Constructor. Makes a new Knight Against Pawn Game.
     *
     * @param p1 white player type (human or computer).
     * @param p2 black player type (human or computer).
     * @param knightColor defines what player will play with bishops.
     */
    public KnightAgainstPawn(PlayerType p1, PlayerType p2,
            PieceColor knightColor) {
        super(p1, p2, knightColor, PieceType.KNIGHT, new char[]{'b', 'd'},
        "Knights Against Pawns");
    }

    @Override
    public PreChessGame newGame(){
        return new KnightAgainstPawn(whitePlayer.getPlayerType(),
                blackPlayer.getPlayerType(), getMinorPieceColor());
    }
    
}
