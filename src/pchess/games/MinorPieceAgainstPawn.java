package pchess.games;

import pchess.core.ChessBoard;
import pchess.core.Piece;
import pchess.core.Player;
import pchess.core.enums.PieceColor;
import pchess.core.enums.PieceType;
import pchess.core.enums.PlayerType;

/**
 * This abstract class implements common roles to games "Bishops Against Pawns"
 * and "Knight Against Pawns".
 */
public abstract class MinorPieceAgainstPawn extends PreChessGame {

    /**
     * Piece type for minor piece.
     */
    private final PieceType minorPiece;
    
    /**
     * Minor piece color.
     */
    private final PieceColor minorPieceColor;

    /**
     * Last rank, defines rank that pawns must arrive to win game.
     */
    private final int winRank;

    /**
     * Columns to put minor pieces.
     */
    private final char[] columns;

    /**
     * Constructor. Makes a new Minor Piece Against Pawn Game.
     *
     * @param p1 white player type (human or computer).
     * @param p2 black player type (human or computer).
     * @param minorPieceColor defines what player will play with minor pieces.
     * @param minorPiece minor piece type.
     * @param columns columns to put minor pieces.
     * @param name game name.
     */
    public MinorPieceAgainstPawn(PlayerType p1, PlayerType p2,
            PieceColor minorPieceColor, PieceType minorPiece,
            char[] columns, String name) {
        super(p1, p2, name);
        chessBoard = new ChessBoard(5, 5);

        this.minorPiece = minorPiece;
        this.minorPieceColor = minorPieceColor;

        if (minorPieceColor == PieceColor.WHITE) {
            winRank = 1;
        } else {
            winRank = 5;
        }

        this.columns = columns;
    }

    @Override
    protected void startPlayerPieces() {
        
        String[] minorpieces = new String[2];
        String[] pawns = new String[5];
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < minorpieces.length; i++){
            sb.delete(0, sb.length());
            minorpieces[i] = sb.append(minorPiece.getCharId()).append(columns[i]).append(winRank).toString();
        }
        
        for(int i = 0; i < 5; i++){
            sb.delete(0, sb.length());
            char c = (char)(97 + i);
            pawns[i] = sb.append("V").append(c).append(6 - winRank).toString();
        }
        
        if(winRank == 1){
            chessBoard.addPieces(minorpieces, pawns);
        } else{
            chessBoard.addPieces(pawns, minorpieces);
        }
        
        whitePlayer.setPieces(chessBoard.getWhitePieces());
        blackPlayer.setPieces(chessBoard.getBlackPieces());
    }

    @Override
    public boolean isGameOver(ChessBoard board){
        Piece[] pawns, minor;
        
        if(board.getWhitePieces().length == 2){
            minor = board.getWhitePieces();
            pawns = board.getBlackPieces();
        } else{
            minor = board.getBlackPieces();
            pawns = board.getWhitePieces();
        }
        
        int nPawns = 0;
        int nMinor = 0;
        boolean lastRank = false;
        int pawnMoves = 0;
        
        for (Piece pawn : pawns) {
            if (pawn.isLiving()) {
                nPawns++;
                if (pawn.getSquare().getInternalRow() == -winRank + 5){
                lastRank = true;
                    for(Piece m : minor){
                        if(m.isLiving()){
                            m.defLegalMove();
                            if(m.isLegalMove(pawn.getSquare())){
                                lastRank = false;
                            }
                        }
                    }
                }
                pawn.defLegalMove();
                pawnMoves += pawn.totalLegalMove();
            }
        }
        
        for (Piece minor1 : minor) {
            if (minor1.isLiving()) {
                nMinor++;
            }
        }
        
        if(nPawns == 0){
            board.setWinner(minor[0].getPieceColor(),
                    minorPiece.toString() + "s captured all pawns.");
            return true;
        }
        
        if(nMinor == 0){
            board.setWinner(pawns[0].getPieceColor(),
                    "Pawns captured all " + minorPiece.toString() + "s.");
            return true;
        }
        
        if(lastRank){
            board.setWinner(pawns[0].getPieceColor(),
                    "A pawn arrives last rank.");
            return true;
        }
        
        if(pawnMoves == 0 && board.getCurrentPlayer() == pawns[0].getPieceColor()){
            board.setWinner(minor[0].getPieceColor(),
                    "Pawns has no legal move to play.");
            return true;
        }
        
        return false;
    }
    
    @Override
    public int evaluate(ChessBoard board){
        
        Piece[] pawns, minors;
        int pawnVal, minorVal ;


        if(board.getWhitePieces().length == 2){
            minors = board.getWhitePieces();
            pawns = board.getBlackPieces();
        } else{
            minors = board.getBlackPieces();
            pawns = board.getWhitePieces();
        }
        
        if (board.getCurrentPlayer() == pawns[0].getPieceColor()) {
            pawnVal = 10;
            minorVal = -25;
        } else{
            pawnVal = -10;
            minorVal = 25;
        }

        int nPawns = 0;
        int nMinor = 0;
        boolean lastRank = false;
        int pawnMoves = 0;
        
        for (Piece pawn : pawns) {
            if (pawn.isLiving()) {
                nPawns++;
                if (pawn.getSquare().getInternalRow() == -winRank + 5){
                lastRank = true;
                    for(Piece m : minors){
                        if(m.isLiving()){
                            m.defLegalMove();
                            if(m.isLegalMove(pawn.getSquare())){
                                lastRank = false;
                            }
                        }
                    }
                }
                pawn.defLegalMove();
                pawnMoves += pawn.totalLegalMove();
            }
        }
        
        for (Piece minor1 : minors) {
            if (minor1.isLiving()) {
                nMinor++;
            }
        }
        
        if(nPawns == 0){
            return (pawns[0].getPieceColor() == board.getCurrentPlayer() ? -1000 : 1000);
        }
        
        if(nMinor == 0){
            return (minors[0].getPieceColor() == board.getCurrentPlayer()? -1000 : 1000);
        }
        
        if(lastRank){
            return (pawns[0].getPieceColor() == board.getCurrentPlayer() ? 1000 : -1000);
        }
        
        if(pawnMoves == 0 && board.getCurrentPlayer() == pawns[0].getPieceColor()){
            return (pawns[0].getPieceColor() == board.getCurrentPlayer() ? -1000 : 1000);
        }
        
        return nPawns*pawnVal + nMinor*minorVal;
    }
    
    /**
     * Returns minor piece color.
     * 
     * @return minor piece color. 
     */
    public PieceColor getMinorPieceColor(){
        return minorPieceColor;
    }

}
