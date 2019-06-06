package pchess.core;

import pchess.core.enums.PieceType;
import pchess.core.enums.PieceColor;

/**
 * Standard pawn in chess game.
 */
public class Pawn extends Piece {

    /**
     * Defines movement route.
     */
    private final int dp;
    
    /**
     * Defines if pawn can move two squares in first movement.
     */
    private boolean firstMove;
    
    /**
     * Defines if pawn is promoted.
     */
    private boolean promoting;
    
    /**
     * Row where pawn is promoted.
     */
    private int upRow;

    /**
     * Constructor. Makes a new pawn.
     *
     * @param pc pawn color.
     * @param fm if true, pawn can move two squares in its first movement.
     */
    public Pawn(PieceColor pc, boolean fm){
        super(PieceType.PAWN, pc);
        dp = (pc == PieceColor.WHITE ? -1 : 1);

        firstMove = fm;
        promoting = false;
    }

    @Override
    public void defLegalMove() {
        if(!isLiving()){
            return;
        }
        legalMove.clear();
        protectedSquares.clear();
        ChessBoard board = atualSquare.getChessBoard();
        int r = atualSquare.getInternalRow();
        int c = atualSquare.getInternalColumn();

        int mc = board.getNColumns();

        if (upRow == r) {
            return;
        }

        Square up = board.getSquare(r + dp, c);
        if (!up.isOccupied()) {
            legalMove.add(up);
            if (firstMove) {
                Square up2 = board.getSquare(r + 2 * dp, c);
                if (!up2.isOccupied()) {
                    legalMove.add(up2);
                }
            }
        }

        int[] attack;
        if (c == 0) {
            attack = new int[1];
            attack[0] = c + 1;
        } else if (c == mc - 1) {
            attack = new int[1];
            attack[0] = c - 1;
        } else {
            attack = new int[2];
            attack[0] = c - 1;
            attack[1] = c + 1;
        }

        for (int j : attack) {
            Square attackedSquare = board.getSquare(r + dp, j);
            if (attackedSquare.isOccupied()) {
                Piece piece = attackedSquare.getPiece();
                if (piece.getPieceColor() != getPieceColor()) {
                    legalMove.add(attackedSquare);
                } else{
                    protectedSquares.add(attackedSquare);
                }
            } else{
                protectedSquares.add(attackedSquare);
            }
        }

    }

    @Override
    public void doMove(Square sq) {
        super.doMove(sq);
        firstMove = false;
        if(sq.getInternalRow() == upRow){
            promoting = true;
        }
    }
    
    @Override
    public void startSquare(Square square){
        super.startSquare(square);
        upRow = (dp == -1 ? 0 : square.getChessBoard().getNRows() - 1);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(3);
        if(firstMove){
            sb.append(PieceType.PAWN.getCharId());
        } else{
            sb.append(PieceType.PAWN_VAR.getCharId());
        }
        
        if(isLiving()){
            sb.append(getSquare().getName());
        } else{
            sb.append("X");
        }
        return sb.toString();
    }
    
    /**
     * Returns true if this pawn is promoted.
     * 
     * @return true if this pawn is promoted. 
     */
    public boolean isPromoted(){
        return promoting;
    }
}
