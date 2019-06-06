package pchess.core.enums;

/**
 * Enum type that defines piece colors.
 */
public enum PieceColor {

    /**
     * White color.
     */
    WHITE(0),
    /**
     * Black color.
     */
    BLACK(6);

    /**
     * Piece color id, used as parameter to define piece code.
     */
    private final int id;

    /**
     * Constructor. Makes a PieceColor.
     * 
     * @param id piece color id. 
     */
    private PieceColor(int id) {
        this.id = id;
    }

    /**
     * Returns piece color id.
     * 
     * @return piece color id. 
     */
    public int getId() {
        return id;
    }

}
