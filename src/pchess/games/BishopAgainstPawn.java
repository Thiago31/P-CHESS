package pchess.games;

import pchess.core.enums.PieceColor;
import pchess.core.enums.PieceType;
import pchess.core.enums.PlayerType;

/**
 * Bishops Against Pawns game.
 */
public class BishopAgainstPawn extends MinorPieceAgainstPawn {

    /**
     * Constructor. Makes a new Bishop Against Pawn Game.
     *
     * @param p1 white player type (human or computer).
     * @param p2 black player type (human or computer).
     * @param bishopColor defines what player will play with bishops.
     */
    public BishopAgainstPawn(PlayerType p1, PlayerType p2, PieceColor bishopColor) {
        super(p1, p2, bishopColor, PieceType.BISHOP, new char[]{'b', 'c'},
        "Bishops Against Pawns");
    }

    @Override
    public PreChessGame newGame() {
        return new BishopAgainstPawn(whitePlayer.getPlayerType(),
                blackPlayer.getPlayerType(), getMinorPieceColor());
    }

}
