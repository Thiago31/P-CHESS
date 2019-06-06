package pchess.core;

import pchess.core.enums.SquareType;

/**
 * A square in chessboard.
 */
public class Square {

    /**
     * Square row.
     */
    private final int row;

    /**
     * Square column.
     */
    private final int column;

    /**
     * Square type, can be LIGHT or DARK.
     */
    private final SquareType sType;

    /**
     * Chessboard that owns this square.
     */
    private final ChessBoard chessboard;

    /**
     * Piece that stays in this square. If square is empty, {@code atualPiece}
     * is set to {@code null}.
     */
    Piece atualPiece;

    /**
     * Square's name.
     */
    private final String name;

    /**
     * Constructor. Makes a new square.
     *
     * @param row square row (internal number).
     * @param column square column (interntal number).
     * @param sType square type (LIGHT or DARK).
     * @param chessboard chessboard where stays this square.
     */
    public Square(int row, int column, SquareType sType, ChessBoard chessboard) {
        this.row = row;
        this.column = column;
        this.sType = sType;
        this.chessboard = chessboard;

        StringBuilder sb = new StringBuilder();
        char letter = (char) (column + 97);
        int newRow = chessboard.getNRows() - row;
        name = sb.append(letter).append(newRow).toString();
    }

    /**
     * Returns row internal number.
     *
     * @return row internal number.
     */
    public int getInternalRow() {
        return row;
    }

    /**
     * Returns column internal number.
     *
     * @return column internal number.
     */
    public int getInternalColumn() {
        return column;
    }
    
    /**
     * Returns row number.
     * 
     * @return row number.
     */
    public int getRow(){
        return chessboard.getNRows() - row;
    }
    
    /**
     * Returns character column.
     * 
     * @return character column.
     */
    public char getColumn(){
        return (char)(column + 97);
    }

    /**
     * Returns square type (LIGHT or DARK).
     *
     * @return square type.
     */
    public SquareType getSquareType() {
        return sType;
    }

    /**
     * Puts a piece in this square.
     *
     * @param newPiece piece to put in this square.
     */
    public void addPiece(Piece newPiece) {
        if (isOccupied()) {

            atualPiece.removeFromGame();
        }
        atualPiece = newPiece;
    }

    /**
     * Removes piece that stays in this square.
     */
    public void removePiece() {
        atualPiece = null;
    }

    /**
     * Returns the piece that stays in this square.
     *
     * @return Piece that stays in this square. If square is empty, return null.
     */
    public Piece getPiece() {
        return atualPiece;
    }

    /**
     * Returns true if this square is occupied, that is, a piece stays on it,
     * returns false otherwise.
     *
     * @return true if this square is occupied.
     */
    public boolean isOccupied() {
        return atualPiece != null;
    }

    /**
     * Returns square chessboard.
     *
     * @return square chessboard.
     */
    public ChessBoard getChessBoard() {
        return chessboard;
    }

    /**
     * Returns square name.
     *
     * @return square name.
     */
    public String getName() {
        return name;
    }

}
