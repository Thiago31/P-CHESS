package pchess.core;

import pchess.core.enums.Difficulty;
import pchess.core.enums.PieceColor;
import pchess.core.enums.PlayerType;
import pchess.games.PreChessGame;

/**
 * A computer player. This class defines AI methods so that computer can
 * choose best move to do and play against human player.
 * Negamax algorithm is implemented.
 */
public class ComputerPlayer extends Player {

    /**
     * Game difficulty.
     */
    private static Difficulty difficulty = Difficulty.EASY;

    /**
     * Constructor. Makes a new computer player.
     * @param pieceColor color for pieces owned by this player.
     * @param pGame game that this player is playing.
     */
    public ComputerPlayer(PieceColor pieceColor, PreChessGame pGame) {
        super(pieceColor, pGame);
        this.playerType = PlayerType.COMPUTER;
    }

    @Override
    /**
     * Sends a message to computer to make a move. If difficulty is {@code 
     * Difficulty.HARD}, all movements will be calculated, otherwise,
     * sometimes computer will be random movements.
     */
    public void doMove(){
        Movement move;
        if (Math.random() > difficulty.getPercent()) {
            move = negamax(pGame.getChessBoard(), 4, 0);
        } else {
            move = randomMove();
        }
        move.doMove();
    }

    /**
     * Computes the best move to computer to play.
     * @param board board to be analised.
     * @param maxDepth max depth to search for.
     * @param currentDepth current depth in searching algorithm.
     * @return 
     */
    private Movement negamax(ChessBoard board, int maxDepth, int currentDepth) {

        if (pGame.isGameOver(board) || currentDepth == maxDepth) {
            Movement move = new Movement(null, null);
            move.setScore(pGame.evaluate(board) - currentDepth);
            return move;
        }

        Movement bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Movement move : board.getMoves()) {
            ChessBoard newBoard = board.makeMove(move);
            Movement currentMove = negamax(newBoard, maxDepth, currentDepth + 1);

            int currentScore = -currentMove.getScore();

            if (currentScore > bestScore) {

                bestScore = currentScore;
                bestMove = move;
                bestMove.setScore(bestScore);
            }
        }

        return bestMove;
    }

    /**
     * Select a random movement to computer to play.
     * @return a random movement.
     */
    private Movement randomMove() {

        Movement[] moves = getMovements();
        int m = moves.length;
        int sel = (int) (Math.random() * m);
        return moves[sel];
    }

    /**
     * Sets game difficulty.
     * @param difficulty difficulty level. Following values are allowed:
     * <ul>
     * <li>Difficulty.EASY</li>
     * <li>Difficulty.MEDIUM</li>
     * <li>Difficulty.HARD</li>
     * </ul>
     */
    public static void setDifficulty(Difficulty difficulty) {
        ComputerPlayer.difficulty = difficulty;
    }

}
