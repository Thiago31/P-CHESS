package pchess.games;

import pchess.core.ChessBoard;
import pchess.core.Piece;
import pchess.core.Square;
import pchess.core.enums.PieceColor;
import pchess.core.enums.PieceType;
import pchess.core.enums.PlayerType;

/**
 * This abstract class defines common behavior to killer games serie. Killer
 * game series is composed by "Killer Queen", "Killer Rock", and "Killer Knight".
 */
public abstract class AbstractKiller extends PreChessGame {

    protected static String[] whiteMap1 = {"Xa1", "Da4", "Db6", "Dc3", "Dd5",
        "De2", "De7", "Df4", "Dg6"};
    protected static String[] blackMap1 = {"Xh8", "Db3", "Dc5", "Dd2", "Dd7",
        "De4", "Df6", "Dg3", "Dh5"};
    protected static String[] whiteMap2 = {"Xa1", "Da4", "Db6", "Dc3", "Dd5",
        "De2", "De7", "Df4", "Dg6", "Da8", "Df3", "Dg1", "Dh3"};
    protected static String[] blackMap2 = {"Xh8", "Db3", "Dc5", "Dd2", "Dd7",
        "De4", "Df6", "Dg3", "Dh5", "Da6", "Db8", "Dc6", "Dh1"};
    protected static String[] whiteMap3 = {"Xa1", "Da4", "Db6", "Dc3", "Dd5",
        "De2", "De7", "Df4", "Dg6", "Da8", "Df3", "Dg1", "Dh3", "Db2", "Dc4",
        "Dd6", "De3"};
    protected static String[] blackMap3 = {"Xh8", "Db3", "Dc5", "Dd2", "Dd7",
        "De4", "Df6", "Dg3", "Dh5", "Da6", "Db8", "Dc6", "Dh1", "Dg7", "Df5",
        "Dd3", "De6"};

    protected static String[] whiteMap4 = {"Na1", "Da7", "Db2", "Dd1", "Dd4",
        "Dd7", "Dg4", "Dh1", "Dh7"};
    protected static String[] blackMap4 = {"Nh8", "Da2", "Da8", "Db5", "De2",
        "De5", "De8", "Dg7", "Dh2"};

    protected static String[] whiteMap5 = {"Na1", "Db1", "Dg1", "Dg2", "Dd3",
        "De3", "Da5", "Dc5", "Df5", "Dh5", "Dg7", "Dd8", "De8"};

    protected static String[] blackMap5 = {"Nh8", "Dd1", "De1", "Db2", "Da4",
        "Dc4", "Df4", "Dh4", "Dd6", "De6", "Db7", "Db8", "Dg8"};

    protected static String[] whiteMap6 = {"Na1", "Db1", "Dg1", "Da2", "De2",
        "Dh2", "Db3", "Df3", "Dg3", "De4", "Dg4", "Da5", "Db5", "Dd5", "Dh5",
        "Dc6", "Dd7"};

    protected static String[] blackMap6 = {"Nh8", "Dd2", "Dc3", "Da4", "Db4",
        "Dd4", "Dh4", "De5", "Dg5", "Db6", "Df6", "Dg6", "Da7", "De7", "Dh7",
        "Db8", "Dg8"};

    /**
     * White killer piece.
     */
    protected Piece whiteKiller;

    /**
     * Black killer piece.
     */
    protected Piece blackKiller;

    /**
     * Piece type for killer piece.
     */
    private final PieceType killer;

    /**
     * Number of diamonds.
     */
    private final int numberOfDiamonds;

    /**
     * Constructor. Makes a killer game.
     *
     * @param p1 white player type.
     * @param p2 black player type.
     * @param killer piece type for killer piece.
     * @param numberOfDiamonds number of diamonds (can be 8, 12, or 16).
     * @param name game name.
     */
    public AbstractKiller(PlayerType p1, PlayerType p2, PieceType killer,
            int numberOfDiamonds, String name) {
        super(p1, p2, name);
        this.killer = killer;
        this.numberOfDiamonds = numberOfDiamonds;
        chessBoard = new ChessBoard();

    }

    @Override
    protected void startPlayerPieces() {

        String[] whiteDiamonds;
        String[] blackDiamonds;

        if (killer == PieceType.KNIGHT) {
            switch (numberOfDiamonds) {
                case 8:
                    whiteDiamonds = whiteMap4;
                    blackDiamonds = blackMap4;
                    break;
                case 12:
                    whiteDiamonds = whiteMap5;
                    blackDiamonds = blackMap5;
                    break;
                case 16:
                    whiteDiamonds = whiteMap6;
                    blackDiamonds = blackMap6;
                    break;
                default:
                    throw new IllegalArgumentException("Number of diamonds not allowed!!!");
            }

        } else {
            switch (numberOfDiamonds) {
                case 8:
                    whiteDiamonds = whiteMap1;
                    whiteDiamonds[0] = killer.getCharId() + whiteDiamonds[0].substring(1);
                    blackDiamonds = blackMap1;
                    blackDiamonds[0] = killer.getCharId() + blackDiamonds[0].substring(1);
                    break;
                case 12:
                    whiteDiamonds = whiteMap2;
                    whiteDiamonds[0] = killer.getCharId() + whiteDiamonds[0].substring(1);
                    blackDiamonds = blackMap2;
                    blackDiamonds[0] = killer.getCharId() + blackDiamonds[0].substring(1);
                    break;
                case 16:
                    whiteDiamonds = whiteMap3;
                    whiteDiamonds[0] = killer.getCharId() + whiteDiamonds[0].substring(1);
                    blackDiamonds = blackMap3;
                    blackDiamonds[0] = killer.getCharId() + blackDiamonds[0].substring(1);
                    break;
                default:
                    throw new IllegalArgumentException("Number of diamonds not allowed!!!");
            }
        }

        chessBoard.addPieces(whiteDiamonds, blackDiamonds);

        whiteKiller = chessBoard.getPiece("a1");
        whitePlayer.setPieces(chessBoard.getWhitePieces());

        blackKiller = chessBoard.getPiece("h8");
        blackPlayer.setPieces(chessBoard.getBlackPieces());

    }

    @Override
    public boolean isGameOver(ChessBoard board) {
        Piece boardWhiteKiller = board.getWhitePieces()[0];
        if (!boardWhiteKiller.isLiving()) {
            board.setWinner(PieceColor.BLACK, "black " + killer.toString() + " captured white "
                    + killer.toString() + ".");
            return true;
        }

        Piece boardBlackKiller = board.getBlackPieces()[0];
        if (!boardBlackKiller.isLiving()) {
            board.setWinner(PieceColor.WHITE, "white " + killer.toString() + " captured black "
                    + killer.toString() + ".");
            return true;
        }

        int whiteDiamonds = numberOfDiamonds;
        int blackDiamonds = numberOfDiamonds;

        for (int i = 1; i <= numberOfDiamonds; i++) {
            if (!board.getWhitePieces()[i].isLiving()) {
                whiteDiamonds--;
            }
            if (!board.getBlackPieces()[i].isLiving()) {
                blackDiamonds--;
            }
        }

        if (whiteDiamonds == 0) {
            board.setWinner(PieceColor.BLACK, "black " + killer.toString() + " captured all white diamonds.");
            return true;
        }

        if (blackDiamonds == 0) {
            board.setWinner(PieceColor.WHITE, "white " + killer.toString() + " captured all black diamonds.");
            return true;
        }

        return false;
    }

    @Override
    public int evaluate(ChessBoard board) {

        Piece thisKiller, opponentKiller;
        Piece[] thisPieces, opponentPieces;
        if (board.getCurrentPlayer() == PieceColor.BLACK) {

            thisPieces = board.getBlackPieces();
            opponentPieces = board.getWhitePieces();
            thisKiller = thisPieces[0];
            opponentKiller = opponentPieces[0];

        } else {
            thisPieces = board.getWhitePieces();
            opponentPieces = board.getBlackPieces();
            thisKiller = thisPieces[0];
            opponentKiller = opponentPieces[0];
        }

        if (!thisKiller.isLiving()) {
            return -1000;
        }

        if (!opponentKiller.isLiving()) {
            return 1000;
        }
        
        int c = 0;
        int m = 0;
        int a = 0;
        int d = 0;

        thisKiller.defLegalMove();
        opponentKiller.defLegalMove();

        for (int i = 1; i <= numberOfDiamonds; i++) {
            if (!thisPieces[i].isLiving()) {
                m++;
            } else {
                Square sq = thisPieces[i].getSquare();
                if (opponentKiller.isLegalMove(sq)) {
                    d++;
                }
            }

            if (!opponentPieces[i].isLiving()) {
                c++;
            } else {
                Square sq = opponentPieces[i].getSquare();
                if (thisKiller.isLegalMove(sq)) {
                    a++;
                }
            }
        }

        if (c == numberOfDiamonds) {
            return 1000;
        }
        if (m == numberOfDiamonds) {
            return -1000;
        }


        return 100 * c + 20 * a - 100 * m - 20 * d;
    }

    /**
     * Returns number of diamonds for each player.
     * 
     * @return number of diamonds for each player.
     */
    public int getNumberOfDiamonds() {
        return numberOfDiamonds;
    }

}
