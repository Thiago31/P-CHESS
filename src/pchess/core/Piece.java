package pchess.core;

import pchess.core.enums.PieceType;
import pchess.core.enums.PieceColor;
import java.util.ArrayList;

/**
 * Super class to construct all piece types.
 */
public abstract class Piece{

    /**
     * Square where this piece stays.
     */
    protected Square atualSquare;

    /**
     * List of piece legal moves.
     */
    protected final ArrayList<Square> legalMove;
    
    /**
     * List of protected squares.
     */
    protected final ArrayList<Square> protectedSquares;

    /**
     * Piece type.
     */
    private final PieceType pType;

    /**
     * Piece color.
     */
    private final PieceColor pColor;

    /**
     * Defines if this piece is captured.
     */
    private boolean isCaptured;

    /**
     * Defines if this piece is in game.
     */
    private boolean living;

    /**
     * Constructor. Makes a new piece.
     *
     * @param pType piece type.
     * @param pColor piece color.
     */
    public Piece(PieceType pType, PieceColor pColor){
        this.pType = pType;
        this.pColor = pColor;
        
        legalMove = new ArrayList<>();
        protectedSquares = new ArrayList<>();
        living = false;
    }
    
    /**
     * Starts this piece in a game.
     * 
     * @param square square to put this piece. 
     */
    public void startSquare(Square square){
        living = true;
        this.atualSquare = square;
        atualSquare.addPiece(this);
    }


    /**
     * Returns true if this piece is in game.
     * 
     * @return true if this piece is in game, false
     * if this piece is captured.
     */
    public boolean isLiving(){
        return living;
    }
    
    /**
     * Returns piece type.
     * @return piece type.
     */
    public PieceType getPieceType() {
        return pType;
    }

    /**
     * Defines legal movements to this piece.
     */
    public abstract void defLegalMove();

    /**
     * Defines legal movements to this piece.
     *
     * @param vectorMove vector that defines movement orientations.
     */
    protected void defLegalMove(int[][] vectorMove) {
        defLegalMove(vectorMove, false);
    }

    /**
     * Defines legal movements to this piece.
     *
     * @param vectorMove vector that defines movement orientations.
     * @param shortMove if true, piece makes short movement, if false, piece
     * makes long movement.
     */
    protected void defLegalMove(int[][] vectorMove, boolean shortMove) {
        if(isCaptured()){return;}
        if(!living){return;}
        legalMove.clear();
        protectedSquares.clear();

        ChessBoard board = atualSquare.getChessBoard();
        int mr = board.getNRows();
        int mc = board.getNColumns();
        int ai = atualSquare.getInternalRow();
        int aj = atualSquare.getInternalColumn();

        for (int[] dm : vectorMove) {
            int di = dm[0];
            int dj = dm[1];
            for (int i = ai + di, j = aj + dj;
                    i > -1 && i < mr && j > -1 && j < mc;
                    i += di, j += dj) {
                Square destiny = board.getSquare(i, j);
                if (!destiny.isOccupied()) {
                    legalMove.add(destiny);
                } else {
                    Piece piece = destiny.getPiece();
                    if (piece.getPieceColor() != getPieceColor()) {
                        legalMove.add(destiny);
                        if(piece.getPieceType() == PieceType.KING && !shortMove){
                            protectedSquares.add(board.getSquare(i + di, j + dj));
                        }
                    } else{
                        protectedSquares.add(destiny);
                    }
                    break;
                }

                if (shortMove) {
                    break;
                }

            }
        }
    }

    /**
     * Returns number of legal movements for this piece.
     *
     * @return number of legal movements for this piece.
     */
    public int totalLegalMove() {
        return legalMove.size();
    }

    /**
     * Returns true if this piece can move to given square.
     * @param square square to test if this piece can move to.
     * @return true if this piece can move to given square.
     */
    public boolean isLegalMove(Square square) {
        return legalMove.contains(square);
    }

    /**
     * Returns a list of squares that this piece can move to.
     * @return list of squares that this piece can move to.
     */
    public ArrayList<Square> getLegalMoves() {
        return legalMove;
    }
    
    /**
     * Returns a list of squares protected by this piece.
     * @return list of squares that this piece protects.
     */
    public ArrayList<Square> getProtectedSquares(){
        return protectedSquares;
    }

    /**
     * Shows if this piece is captured.
     *
     * @return true if this piece is captured and removed from game, false
     * otherwise.
     */
    public boolean isCaptured() {
        return isCaptured;
    }
    
    /**
     * Removes this piece from game.
     */
    public void removeFromGame(){
        isCaptured = true;
        living = false;
        legalMove.clear();
        protectedSquares.clear();
        atualSquare = null;
    }

    /**
     * Returns square that this piece stays on.
     * @return square that this piece stays on.
     */
    public Square getSquare() {
        return atualSquare;
    }

    /**
     * Moves this piece to a new square. If new square is occupied by
     * opponent piece, removes opponent piece from game.
     * 
     * @param sq new square to put this piece. 
     */
    public void doMove(Square sq) {
        Square oldSquare = this.getSquare();

        oldSquare.removePiece();
        sq.addPiece(Piece.this);
        atualSquare = sq;

    }

    /**
     * Returns true if this piece is blocked, that is, piece has no legal
     * movement to do.
     * 
     * @return true if this piece is blocked. 
     */
    public boolean isBlocked() {
        return legalMove.isEmpty();
    }

    /**
     * Returns piece color. Return can be either {@code PieceColor.WHITE}
     * or {@code PieceColor.BLACK}.
     * 
     * @return piece color.
     */
    public PieceColor getPieceColor() {
        return pColor;
    }

    /**
     * Returns piece code. This code is used to GUI components to select 
     * a gliff to draw this piece.
     * 
     * @return piece code. 
     */
    public int getPieceCode(){
        return pType.getId() + pColor.getId();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(3);
        sb.append(pType.getCharId());
        if(living){
            sb.append(atualSquare.getName());
        } else{
            sb.append("X");
        }
        return sb.toString();
    }

}
