package pchess.core;

import pchess.core.enums.PieceColor;
import pchess.core.enums.SquareType;

/**
 * A chessboard used in prechess game.
 */
public class ChessBoard {

    /**
     * List of squares in this chessboard.
     */
    private final Square[] squares;

    /**
     * Number of rows.
     */
    private final int nRows;

    /**
     * Number of columns.
     */
    private final int nColumns;

    /**
     * White pieces in this chessboard.
     */
    private Piece[] whitePieces;

    /**
     * Black pieces in this chessboard.
     */
    private Piece[] blackPieces;

    /**
     * Defines current player to move.
     */
    private PieceColor currentPlayer;

    /**
     * Defines winner player after end game.
     */
    private PieceColor winnerPlayer;

    /**
     * Message to show rule used to define winner player.
     */
    private String winMessage;

    /**
     * Constructor. Makes a default chessboard with 8 rows and 8 columns.
     */
    public ChessBoard() {
        this(8, 8);
    }

    /**
     * Constructor. Makes a custom chessboard.
     *
     * @param nRows number of rows in this chessboard.
     * @param nColumns number of columns in this chessboard.
     */
    public ChessBoard(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;

        squares = new Square[nRows * nColumns];

        int n = 0;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                SquareType squareType = (((i + j) % 2 == 0) ? SquareType.LIGHT
                        : SquareType.DARK);
                squares[n] = new Square(i, j, squareType, this);
                n++;
            }
        }

        currentPlayer = PieceColor.WHITE;

    }

    /**
     * Returns a square in this chessboard.
     *
     * @param row internal square row number.
     * @param column internal square column number.
     * @return a square in this chessboard.
     */
    public Square getSquare(int row, int column) {
        int n = row * nColumns + column;
        return squares[n];
    }

    /**
     * Returns a square in this chessboard.
     *
     * @param column square column.
     * @param row square row.
     * @return a square in this chessboard.
     */
    public Square getSquare(char column, int row) {
        int r = nRows - row;
        int c = (int) column - 97;
        return getSquare(r, c);
    }

    /**
     * Returns a square in this chessboard.
     *
     * @param squareName square name.
     * @return a square in this chessboard.
     */
    public Square getSquare(String squareName) {
        char c = squareName.charAt(0);
        int r = Integer.parseInt(squareName.substring(1));
        return getSquare(c, r);
    }

    /**
     * Returns a piece in this chessboard.
     * 
     * @param squareName square where stays desired piece.
     * @return piece that stays in square whose name is {@code squareName}
     */
    public Piece getPiece(String squareName) {
        return getSquare(squareName).getPiece();
    }

    /**
     * Returns number of rows in this chessboard.
     *
     * @return numbero of rows in this chessboard.
     */
    public int getNRows() {
        return nRows;
    }

    /**
     * Returns number of columns in this chessboard.
     *
     * @return number of columns in this chessboard.
     */
    public int getNColumns() {
        return nColumns;
    }

    /**
     * Adds pieces in this chessboard.
     *
     * @param wPieces Vector containing white piece descriptions.
     * @param bPieces Vector containing black piece descriptions.
     */
    public void addPieces(String[] wPieces, String[] bPieces) {
        whitePieces = new Piece[wPieces.length];
        blackPieces = new Piece[bPieces.length];
        addPieces(whitePieces, wPieces, PieceColor.WHITE);
        addPieces(blackPieces, bPieces, PieceColor.BLACK);
    }

    private void addPieces(Piece[] pieces, String[] pieceDefinitions, PieceColor pieceColor) {
        for (int i = 0; i < pieces.length; i++) {
            char pType = pieceDefinitions[i].charAt(0);

            switch (pType) {
                case 'K':
                    pieces[i] = new King(pieceColor);
                    break;
                case 'Q':
                    pieces[i] = new Queen(pieceColor);
                    break;
                case 'R':
                    pieces[i] = new Rook(pieceColor);
                    break;
                case 'B':
                    pieces[i] = new Bishop(pieceColor);
                    break;
                case 'N':
                    pieces[i] = new Knight(pieceColor);
                    break;
                case 'P':
                    pieces[i] = new Pawn(pieceColor, true);
                    break;
                case 'V':
                    pieces[i] = new Pawn(pieceColor, false);
                    break;
                case 'D':
                    pieces[i] = new Diamond(pieceColor);
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Piece symbol not recognized: " + pType);
            }

            String squareName = pieceDefinitions[i].substring(1);
            if (!squareName.equals("X")) {
                Square square = getSquare(squareName);
                pieces[i].startSquare(square);
            }
        }
    }

    /**
     * Returns white pieces.
     * 
     * @return white pieces. 
     */
    public Piece[] getWhitePieces() {
        return whitePieces;
    }

    /**
     * Returns black pieces.
     * 
     * @return black pieces. 
     */
    public Piece[] getBlackPieces() {
        return blackPieces;
    }

    /**
     * Returns a new chessboard with a piece moved. This method is used to
     * AI, so that computer can play against human player.
     * 
     * @param move Movement to be done in new chessboard.
     * @return new chessboard with a piece moved.
     */
    public ChessBoard makeMove(Movement move) {
        ChessBoard cb = new ChessBoard(nRows, nColumns);
        String[] wp = new String[whitePieces.length];
        String[] bp = new String[blackPieces.length];
        for (int i = 0; i < wp.length; i++) {
            wp[i] = whitePieces[i].toString();
        }
        for (int i = 0; i < bp.length; i++) {
            bp[i] = blackPieces[i].toString();
        }
        cb.addPieces(wp, bp);
        String piecePosition = move.getPiece().getSquare().getName();
        String squarePosition = move.getSquare().getName();
        Piece p = cb.getPiece(piecePosition);
        Square s = cb.getSquare(squarePosition);
        p.doMove(s);
        if (currentPlayer == PieceColor.WHITE) {
            cb.passMove();
        }
        return cb;
    }

    /**
     * Changes player that has move.
     */
    public void passMove() {
        if (currentPlayer == PieceColor.WHITE) {
            currentPlayer = PieceColor.BLACK;
        } else {
            currentPlayer = PieceColor.WHITE;
        }
    }

    /**
     * Changes move to defined player.
     * 
     * @param player player that gains move. 
     */
    public void passMoveTo(Player player) {
        currentPlayer = player.getPieceColor();
    }

    /**
     * Returns player that has move.
     * 
     * @return player that has move. 
     */
    public PieceColor getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns a list with all possible movements for current player.
     * 
     * @return a list with all possible movements for current player. 
     */
    public Movement[] getMoves() {
        Piece[] pieces = (currentPlayer == PieceColor.WHITE ? whitePieces : blackPieces);

        int total = 0;
        for (Piece p : pieces) {
            p.defLegalMove();
            total += p.totalLegalMove();
        }

        Movement[] mov = new Movement[total];
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
     * Sets what player wins game.
     * 
     * @param pc player that wins game.
     * @param message message for win player.
     */
    public void setWinner(PieceColor pc, String message) {
        winnerPlayer = pc;
        winMessage = message;
    }

    /**
     * Returns winner player.
     * 
     * @return winner player.
     */
    public PieceColor getWinner() {
        return winnerPlayer;
    }

    /**
     * Returns winner message.
     * 
     * @return winner message. 
     */
    public String getWinnerMessage() {
        return winMessage;
    }

    /**
     * Returns true if a player has no legal move to do.
     * 
     * @param pc player to find if it has legal moves.
     * @return true if a player has no legal move to do. 
     */
    public boolean hasNoMoviments(PieceColor pc) {

        if (currentPlayer != pc) {
            return false;
        }

        Piece[] pieces = (pc == PieceColor.WHITE ? whitePieces : blackPieces);

        for (Piece piece : pieces) {
            piece.defLegalMove();
            int t = piece.totalLegalMove();
            if (t > 0) {
                return false;
            }
        }
        return true;
    }

}
