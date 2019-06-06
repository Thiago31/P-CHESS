package pchess.games;

import pchess.core.ChessBoard;
import pchess.core.Piece;
import pchess.core.enums.PieceColor;
import pchess.core.enums.PlayerType;

/**
 * King Duel game.
 */
public class KingDuel extends PreChessGame {

    /**
     * Maximum number of movements.
     */
    private final int maxMov;

    /**
     * Constructor. Makes a new King Duel Pawn Game.
     *
     * @param p1 white player type (human or computer).
     * @param p2 black player type (human or computer).
     * @param maxMov maximum number of movements.
     */
    public KingDuel(PlayerType p1, PlayerType p2, int maxMov) {
        super(p1, p2, "King Duel");
        chessBoard = new ChessBoard();
        this.maxMov = maxMov;
    }

    @Override
    protected void startPlayerPieces() {
        
        int column = (int)(8*Math.random());
        char c = (char)(97 + column);
        String[] wp = {"K" + c + "1"};
        String[] bp = {"K" + c + "8"};
        
        chessBoard.addPieces(wp, bp);
        whitePlayer.setPieces(chessBoard.getWhitePieces());
        blackPlayer.setPieces(chessBoard.getBlackPieces());

    }
    
    @Override
    public boolean isGameOver(ChessBoard board){
        
        if(getMovementCount() == maxMov){
            board.setWinner(PieceColor.BLACK, "black king blocks white king after "
            + maxMov + " movements");
            return true;
        }
        
        Piece whiteKing = board.getWhitePieces()[0];
        
        if(whiteKing.getSquare().getInternalRow() == 0){
            board.setWinner(PieceColor.WHITE, "White king arrives 8th rank");
            return true;
        }
        
        return false;
    }

    @Override
    public PreChessGame newGame() {
        return new KingDuel(whitePlayer.getPlayerType(),
                blackPlayer.getPlayerType(), maxMov);
    }
    
    @Override
    public int evaluate(ChessBoard board){
        Piece whiteKing = board.getWhitePieces()[0];
        Piece blackKing = board.getBlackPieces()[0];
        
        int whiteRow = whiteKing.getSquare().getRow();
        int whiteColumn = whiteKing.getSquare().getInternalColumn();
        
        int blackRow = blackKing.getSquare().getRow();
        int blackColumn = blackKing.getSquare().getInternalColumn();
        
        int f = (board.getCurrentPlayer() == PieceColor.WHITE ? 1 : -1) * 2;
        int rowDistance = (Math.abs(blackRow - whiteRow) % 2) * (10);
        int columnDistance = (Math.abs(blackColumn - whiteColumn)) * (-2);
        
        return f * whiteRow + rowDistance + columnDistance;
    }

}
