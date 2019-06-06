package pchess.games;

import pchess.core.ChessBoard;
import pchess.core.Pawn;
import pchess.core.Piece;
import pchess.core.enums.PieceColor;
import pchess.core.enums.PlayerType;

/**
 * Pawn battle game.
 */
public class PawnBattle extends PreChessGame{

    /**
     * Constructor. Makes a new Pawn Battle Pawn Game.
     *
     * @param p1 white player type (human or computer).
     * @param p2 black player type (human or computer).
     * @param columnNumber number of columns.
     */    
    public PawnBattle(PlayerType p1, PlayerType p2, int columnNumber){
        super(p1, p2, "Pawn Battle");
        chessBoard = new ChessBoard(8, columnNumber);
    }
    
    @Override
    protected void startPlayerPieces() {
        
        String[] wp = new String[chessBoard.getNColumns()];
        String[] bp = new String[chessBoard.getNColumns()];

        for(int j = 0; j < chessBoard.getNColumns(); j++){
            char c = (char)(97 + j);
            wp[j] = "P" + c + "2";
            bp[j] = "P" + c + "7";
        }
        
        chessBoard.addPieces(wp, bp);
        whitePlayer.setPieces(chessBoard.getWhitePieces());
        blackPlayer.setPieces(chessBoard.getBlackPieces());

    }
    
    @Override
    public boolean isGameOver(ChessBoard board){
        
        int wCount = countLivingPieces(board.getWhitePieces());
        if(wCount == 0){
            board.setWinner(PieceColor.BLACK, "all white pawns are captured");
            return true;
        }
        
        int bCount = countLivingPieces(board.getBlackPieces());
        if(bCount == 0){
            board.setWinner(PieceColor.WHITE, "all black pawns are captured");
            return true;
        }
        
        for(Piece wp : getLivingPieces(board.getWhitePieces())){
            Pawn pawn = (Pawn)wp;
            if(pawn.isPromoted()){
                board.setWinner(PieceColor.WHITE, "white pawn arrives last rank");
                return true;
            }
        }
        
        for(Piece bp : getLivingPieces(board.getBlackPieces())){
            Pawn pawn = (Pawn)bp;
            if(pawn.isPromoted()){
                board.setWinner(PieceColor.BLACK, "black pawn arrives last rank");
                return true;
            }
        }
        
        if(board.hasNoMoviments(board.getCurrentPlayer())){
            board.setWinner(PieceColor.BLACK, "white player can't move his pawns");
            return true;
        }
        
        if(board.hasNoMoviments(board.getCurrentPlayer())){
            board.setWinner(PieceColor.WHITE, "black player can't move his pawns");
            return true;
        }

        return false;
    }

    @Override
    public PreChessGame newGame(){
        return new PawnBattle(whitePlayer.getPlayerType(), 
        blackPlayer.getPlayerType(), chessBoard.getNColumns());
    }
    
    @Override
    public int evaluate(ChessBoard board){
        
        Piece[] thisPawns, opponentPawns;
        PieceColor thisColor, opponentColor;
        if(board.getCurrentPlayer() == PieceColor.WHITE){
            thisPawns = board.getWhitePieces();
            opponentPawns = board.getBlackPieces();
            thisColor = PieceColor.WHITE;
            opponentColor = PieceColor.BLACK;
        } else{
            thisPawns = board.getBlackPieces();
            opponentPawns = board.getWhitePieces();
            thisColor = PieceColor.BLACK;
            opponentColor = PieceColor.WHITE;
        }
        
        if(countLivingPieces(thisPawns) == 0){
            return -1000;
        }
        
        if(countLivingPieces(opponentPawns) == 0){
            return 1000;
        }
        
        if(board.hasNoMoviments(thisColor)){
            return -1000;
        }
        
        if(board.hasNoMoviments(opponentColor)){
            return 1000;
        }
        
        int thisCount = getLivingPieces(thisPawns).length;
        int opponentCount = getLivingPieces(opponentPawns).length;
        
        for(Piece piece : getLivingPieces(thisPawns)){
            Pawn pawn = (Pawn)piece;
            if(pawn.isPromoted()){
                return 1000;
            }
        }
        
        for(Piece piece : getLivingPieces(opponentPawns)){
            Pawn pawn = (Pawn)piece;
            if(pawn.isPromoted()){
                return -1000;
            }
        }        
        
        return 100 * thisCount - 100 * opponentCount;
    }
}
