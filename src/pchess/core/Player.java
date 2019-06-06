package pchess.core;

import pchess.core.enums.PlayerType;
import pchess.core.enums.PieceColor;
import pchess.games.PreChessGame;

/**
 * A player in prechess game. This class defines a human player, for computer
 * player see {@code ComputerPlayer} class.
 */
public class Player {

    /**
     * Pieces that this player owns.
     */
    private Piece[] pieces;

    /**
     * Defines if this player is next to move.
     */
    private boolean hasMove;

    /**
     * Defines player type, can be PlayerType.HUMAN or PlayerType.COMPUTER.
     */
    protected PlayerType playerType;

    /**
     * Defines piece color of pieces from this player.
     */
    private final PieceColor pieceColor;

    /**
     * Game that this player is playing.
     */
    protected final PreChessGame pGame;

    /**
     * Oponent's player.
     */
    private Player opponent;



    /**
     * Constructor. Makes a new player.
     *
     * @param pieceColor color for pieces owned by this player.
     * @param pGame game that this player is playing.
     */
    public Player(PieceColor pieceColor, PreChessGame pGame) {
        this.pieceColor = pieceColor;
        this.playerType = PlayerType.HUMAN;
        this.pGame = pGame;
    }

    /**
     * Makes this player next to move.
     */
    public void gainMove() {

        defLegalMoves();
        hasMove = true;
    }

    /**
     * Pass move to opponent player.
     */
    public void passMove() {
        hasMove = false;
        opponent.gainMove();
    }
    
    /**
     * Returns true if this player is next to make move.
     *
     * @return true if this player is next to make move.
     */
    public boolean hasMove() {
        return hasMove;
    }

    /**
     * Sets opponent's player.
     *
     * @param opponent opponent's player.
     */
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    /**
     * Gets opponent's player.
     *
     * @return opponent's player.
     */
    public Player getOpponent() {
        return opponent;
    }

    /**
     * Returns player type.
     *
     * @return player type (HUMAN or COMPUTER).
     */
    public PlayerType getPlayerType() {
        return playerType;
    }

    /**
     * Returns true if this player is computer.
     *
     * @return true if this player is computer.
     */
    public boolean isComputer() {
        return playerType == PlayerType.COMPUTER;
    }

    /**
     * Returns true if this player is human.
     *
     * @return true if this player is human.
     */
    public boolean isHuman() {
        return playerType == PlayerType.HUMAN;
    }

    /**
     * Returns player pieces.
     * 
     * @return player pieces. 
     */
    public Piece[] getPieces() {
        return pieces;
    }

    /**
     * Defines legal movements for this player.
     */
    public void defLegalMoves() {
        for (Piece p : pieces) {
            p.defLegalMove();
        }
    }

    /**
     * Returns total number of possible movements for this player.
     * 
     * @return total number of possible movements. 
     */
    public int getTotalLegalMoves() {
        int total = 0;
        for (Piece p : pieces) {
            p.defLegalMove();
            total += p.totalLegalMove();
        }
        return total;
    }

    /**
     * Returns total number of pieces that this player owns. Since player losses
     * pieces during game, in each call to this method, the returning value may
     * decrease.
     *
     * @return total number of pieces that this player owns.
     */
    public int getNumberOfPieces() {
        int n = 0;
        for (Piece p : pieces) {
            if (p.isLiving()) {
                n++;
            }
        }
        return n;
    }


    /**
     * Returns player piece color.
     * 
     * @return player piece color.
     */
    public PieceColor getPieceColor() {
        return pieceColor;
    }

    /**
     * Returns all possible moves to this player in that position.
     *
     * @return all possible moves to this player.
     */
    public Movement[] getMovements() {
        Movement[] mov = new Movement[getTotalLegalMoves()];
        int n = 0;
        for (Piece piece : pieces) {
            for (Square square : piece.getLegalMoves()) {
                mov[n] = new Movement(piece, square);
                n++;
            }
        }
        return mov;
    }

    /**
     * Sets pieces for this player.
     * 
     * @param pieces pieces that this player owns.
     */
    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }
    
    @Override
    public String toString(){
        return "Player " + pieceColor.name() + " " + playerType.name();
    }

    public void doMove(){
        
    }
}
