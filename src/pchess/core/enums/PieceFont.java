package pchess.core.enums;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Defines fonts to draw pieces. All fonts used in this application are
 * obtained from 
 */
public enum PieceFont {

    /**
     * Chess Condal font.
     */
    CONDFONT("CONDFONT.TTF"),
    
    /**
     * Chess Leipzig font.
     */
    LEIPFONT("LEIPFONT.TTF"),
    
    /**
     * Chess Lucena font.
     */
    LUCEFONT("LUCEFONT.TTF"),
    
    /**
     * Chess Magnetic font.
     */
    MAGNFONT("MAGNFONT.TTF"),
    
    /**
     * Chess Marroquin font.
     */
    MARRFONT("MARRFONT.TTF"),
    
    /**
     * Chess Maya font.
     */
    MAYAFONT("MAYAFONT.TTF"),
    
    /**
     * Chess Mediaeval font.
     */
    MVALFONT("MVALFONT.TTF"),
    
    /**
     * Diamonds font.
     */
    DIAMONDFONT("Diamonds.ttf");

    /**
     * Font used to draw piece images.
     */
    private Font font;

    /**
     * Constructor. Makes a PieceFont and derives font.
     * 
     * @param fontName font name. 
     */
    private PieceFont(String fontName) {
        try {
            InputStream fontOrigin = PieceFont.class.getClassLoader().getResourceAsStream("resources/fonts/" + fontName);
            font = Font.createFont(Font.TRUETYPE_FONT, fontOrigin);
            font = font.deriveFont(30F);
        } catch (FontFormatException | IOException e) {
            font = new Font("Serif", Font.PLAIN, 12);
        }
    }

    /**
     * Returns font defined in this enum element.
     *
     * @return font defined in this enum element.
     */
    public Font getFont() {
        return font;
    }

}
