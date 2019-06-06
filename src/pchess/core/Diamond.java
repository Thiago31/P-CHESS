package pchess.core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import pchess.core.enums.PieceColor;
import pchess.core.enums.PieceType;

/**
 * Diamond, a special piece that can't move, it can only be captured.
 */
public class Diamond extends Piece {

    /**
     * Constructor. Makes a new diamond.
     *
     * @param pc diamond color.
     */
    public Diamond(PieceColor pc){
        super(PieceType.DIAMOND, pc);
    }

    @Override
    /**
     * Since diamond don't move, this method is empty.
     */
    public void defLegalMove() {
    }
    
    @Override
    public int getPieceCode(){
        return (getPieceColor() == PieceColor.WHITE ? 12 : 13);
    }

}
