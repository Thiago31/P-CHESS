package pchess.core.enums;

/**
 * This enum type defines pieces in prechess games. Standard and non standard
 * pieces are defined. Each piece has an id used in selecting font to draw it.
 */
public enum PieceType {
    /**
     * Standard king.
     */
    KING(5, 'K'),
    /**
     * Standard queen.
     */
    QUEEN(4, 'Q'),
    /**
     * Standard rock.
     */
    ROCK(3, 'R'),
    /**
     * Standard bishop.
     */
    BISHOP(2, 'B'),
    /**
     * Standard knight.
     */
    KNIGHT(1, 'N'),
    /**
     * Standard pawn.
     */
    PAWN(0, 'P'),
    /**
     * Pawn that can move only one square in its first move.
     */
    PAWN_VAR(0, 'V'),
    /**
     * A diamond is a special piece that can't move.
     */
    DIAMOND(-1, 'D');

    /**
     * Piece type id.
     */
    private final int id;
    
    /**
     * Piece type character id.
     */
    private final char charId;

    /**
     * Constructor. Makes a PieceType.
     * 
     * @param id piece type id.
     * @param charId piece type character id.
     */
    private PieceType(int id, char charId) {
        this.id = id;
        this.charId = charId;
    }

    /**
     * Returns piece type id.
     * 
     * @return piece type id.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns piece type character id.
     * 
     * @return piece type character id.
     */
    public char getCharId(){
        return charId;
    }
    
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
