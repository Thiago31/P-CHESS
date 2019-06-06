package pchess.games;

import pchess.core.ChessBoard;
import pchess.core.ComputerPlayer;
import pchess.core.Piece;
import pchess.core.enums.PieceColor;
import pchess.core.Player;
import pchess.core.enums.PlayerType;

/**
 * Super class for all prechessgames.
 */
public abstract class PreChessGame {

    /**
     * Chessboard.
     */
    protected ChessBoard chessBoard;

    /**
     * Player with white pieces.
     */
    protected Player whitePlayer;

    /**
     * Player with black pieces.
     */
    protected Player blackPlayer;

    /**
     * Player that has move.
     */
    private Player currentPlayer;

    /**
     * Message that defines win.
     */
    protected String winMessage;

    /**
     * Game name.
     */
    private final String name;

    /**
     * Movement counter.
     */
    private int countMove;

    /**
     * Constructor. Initiates a prechessgame.
     *
     * @param p1 player type for white, can be PlayerType.HUMAN or
     * PlayerType.COMPUTER.
     * @param p2 player type for black, can be PlayerType.HUMAN or
     * PlayerType.BLACK.
     * @param name game name.
     */
    public PreChessGame(PlayerType p1, PlayerType p2, String name) {

        if (p1 == PlayerType.HUMAN) {
            whitePlayer = new Player(PieceColor.WHITE, this);
        } else {
            whitePlayer = new ComputerPlayer(PieceColor.WHITE, this);
        }

        if (p2 == PlayerType.HUMAN) {
            blackPlayer = new Player(PieceColor.BLACK, this);
        } else {
            blackPlayer = new ComputerPlayer(PieceColor.BLACK, this);
        }

        whitePlayer.setOpponent(blackPlayer);
        blackPlayer.setOpponent(whitePlayer);

        currentPlayer = whitePlayer;

        countMove = 0;
        
        this.name = name;

    }

    /**
     * Returns white player.
     *
     * @return white player.
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Returns black player.
     *
     * @return black player.
     */
    public Player getBlackPlayer() {
        return blackPlayer;
    }

    /**
     * Returns player with specified piece color.
     * 
     * @param pc piece color of player to be returned.
     * @return player with specified piece color.
     */
    public Player getPlayer(PieceColor pc) {
        if (pc == PieceColor.WHITE) {
            return whitePlayer;
        } else {
            return blackPlayer;
        }
    }

    /**
     * Returns current player, that is, player that has move.
     *
     * @return current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns game name.
     *
     * @return game name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns movement count. Movement is defined as white move followed by
     * black move.
     * 
     * @return movement count.
     */
    public int getMovementCount() {
        return countMove / 2;
    }

    /**
     * Starts pieces for both white and black players.
     */
    protected abstract void startPlayerPieces();

    /**
     * Returns chessboard.
     *
     * @return chessboard.
     */
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    /**
     * Starts this prechess game.
     */
    public void startGame() {
        startPlayerPieces();
        whitePlayer.gainMove();
    }

    /**
     * Passes move to next player.
     */
    public void passMove() {
        currentPlayer.passMove();
        currentPlayer = currentPlayer.getOpponent();
        chessBoard.passMoveTo(currentPlayer);
        countMove++;
    }

    /**
     * Returns true if is gameover.
     *
     * @return true if is gameover.
     */
    public boolean isGameOver() {
        return isGameOver(chessBoard);
    }

    /**
     * Returns true if is gameover to specified chessboard position.
     * This method is used by AI in order to find gameover positions.
     * 
     * @param board Chessboard position to be tested for gameover.
     * @return true if is gameover. 
     */
    abstract public boolean isGameOver(ChessBoard board);

    /**
     * Returns a new instance of this Pre Chess Game.
     *
     * @return a new instance of this Pre Chess Game.
     */
    abstract public PreChessGame newGame();

    /**
     * Returns winner message.
     *
     * @return winner message.
     */
    public String getWinnerMessage() {
        return chessBoard.getWinnerMessage();
    }

    /**
     * Evaluates chessboard position. This method is used by AI to find
     * best positions.
     * 
     * @param board chessboard position to be evaluated.
     * @return 
     */
    abstract public int evaluate(ChessBoard board);

    /**
     * Returns number of living pieces.
     * 
     * @param pieces piece list.
     * @return number of living pieces.
     */
    public int countLivingPieces(Piece[] pieces) {
        int c = 0;
        for (Piece piece : pieces) {
            if (piece.isLiving()) {
                c++;
            }
        }
        return c;
    }

    /**
     * Returns a new list containing only living pieces.
     * 
     * @param pieces piece list to be filtered.
     * @return a new list containing only living pieces.
     */
    public Piece[] getLivingPieces(Piece[] pieces) {
        int c = countLivingPieces(pieces);
        Piece[] livingPieces = new Piece[c];
        int n = 0;
        for (Piece piece : pieces) {
            if (piece.isLiving()) {
                livingPieces[n] = piece;
                n++;
            }
        }
        return livingPieces;
    }

}
