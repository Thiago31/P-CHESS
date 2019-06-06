package pchess.core;

/**
 * This class represents a movement in game. It is used by {@code 
 * ComputerPlayer} and {@code ChessBoard} classes to implement artificial 
 * intelligence to play game.
 */
public class Movement{
    
    /**
     * Piece to move.
     */
    private final Piece piece;
    
    /**
     * Square to move piece to.
     */
    private final Square square;
    
    /**
     * Movement score.
     */
    private int score;

    /**
     * Constructor. Makes a movement with a piece and a destination square.
     * No score is initialy defined, since it is computed by each prechess
     * game and set with setScore() method.
     * 
     * @param piece piece to move.
     * @param square destination square. 
     */
    public Movement(Piece piece, Square square) {
        this.piece = piece;
        this.square = square;
        score = 0;
    }

    /**
     * Returns piece to move.
     * 
     * @return piece to move. 
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Returns destination square.
     * 
     * @return destination square. 
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Returns movement score.
     * 
     * @return movement score. 
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets movement score.
     * 
     * @param score movement score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Makes movement. This method delegates for {@code piece} do move.
     */
    public void doMove(){
        piece.doMove(square);
    }
    
    @Override
    public String toString(){
        if(piece == null){
            return "no movement -> score: " + score;
        }
        return piece.getPieceType().name() + " " + piece.getSquare().getName() +
                " to " + square.getName() + " (score = " + score + ")";
    }
}
