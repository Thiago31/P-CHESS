package pchess.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import pchess.core.enums.PieceFont;

/**
 * This class provides image manipulation to format piece images showed in game
 * screen. A piece font set can be used to draw standard chess pieces. Special
 * font is used to draw diamonds.
 */
public class PieceImage {

    /**
     * List of piece images. Piece images are stored as BufferedImage.
     */
    private final BufferedImage[] images;

    /**
     * String code to draw each piece image, accordingly piece font.
     */
    private final String[] symbolLetters = {"p", "n", "b", "r", "q", "k",
        "o", "m", "v", "t", "w", "l", "n", "r"};

    /**
     * Current font to draw piece images.
     */
    private Font currentFont = PieceFont.CONDFONT.getFont();

    /**
     * Constructor. Starts piece image list, initialy empty.
     */
    public PieceImage() {
        images = new BufferedImage[14];
    }

    /**
     * Returns piece image at index i. Each piece has a getPieceCode() method to
     * just receive correct image.
     *
     * @param i piece code.
     * @return Image to a piece.
     * @see Piece#getPieceCode
     */
    public BufferedImage getPieceImage(int i) {
        return images[i];
    }

    /**
     * Derive piece images based on current piece font.
     *
     * @param size square size where piece image will be stay.
     */
    public void deriveImages(int size) {
        deriveImages(size, currentFont);
    }

    /**
     * Derive piece images based on specified piece font.
     *
     * @param size square size where piece image will be stay.
     * @param pieceFont piece font to use in drawing piece images. This font is
     * set to {@code currentFont}.
     */
    public void deriveImages(int size, Font pieceFont) {
        deriveImages(size, pieceFont, 0, 12);
        deriveImages(size, PieceFont.DIAMONDFONT.getFont(), 12, 14);
        currentFont = pieceFont;
    }

    /**
     * Derive piece images based on specified piece font.
     *
     * @param size square size where piece image will be stay.
     * @param pFont PieceFont enum type whose piece font to use in drawing piece
     * images. This font is set to {@code currentFont}.
     */
    public void deriveImages(int size, PieceFont pFont) {
        deriveImages(size, pFont.getFont());
    }

    /**
     * Derive piece images based on specified piece font.
     * @param size square size where piece image will be stay.
     * @param pieceFont piece font to use in drawing piece images. This font is
     * set to {@code currentFont}.
     * @param first first image index in {@code images} to be drawn.
     * @param last last image index in {@code images} to be drawn.
     */
    private void deriveImages(int size, Font pieceFont, int first, int last) {

        if (size == 0) {
            return;
        }
        float fontSize = ((float) size) * 0.85f;
        Font pFont = pieceFont.deriveFont(fontSize);
        FontMetrics fm;
        for (int i = first; i < last; i++) {
            images[i] = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = images[i].createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(pFont);
            g.setColor(Color.BLACK);
            fm = g.getFontMetrics();
            int hf = fm.getAscent();
            int wf = fm.stringWidth(symbolLetters[i]);
            int x = (size - wf) / 2;
            int y = (size + hf) / 2;
            g.drawString(symbolLetters[i], x, y);
            g.dispose();
            boolean[][] paintPixels = innerPixels(images[i]);

            int white = Color.WHITE.getRGB();
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (paintPixels[j][k]) {
                        images[i].setRGB(j, k, white);
                    }
                }
            }

            makeImageBorder(images[i]);

        }

    }

    /**
     * Define pixels in image that must be colored in white color. Pixels 
     * inside picture must be colored.
     * @param image image to be colored
     * @return boolean double array that defines those pixels to be colored.
     */
    private boolean[][] innerPixels(BufferedImage image) {

        boolean[][] pixels = new boolean[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                int alpha = (color >> 24) & 0xFF;
                pixels[i][j] = (alpha == 0);
            }
        }

        LinkedList<Point> queue = new LinkedList<>();
        queue.add(new Point());
        pixels[0][0] = false;

        while (!queue.isEmpty()) {

            Point p = queue.poll();

            if (p.x > 0 && pixels[p.x - 1][p.y]) {
                queue.add(new Point(p.x - 1, p.y));
                pixels[p.x - 1][p.y] = false;
            }

            if (p.x < image.getWidth() - 1 && pixels[p.x + 1][p.y]) {
                queue.add(new Point(p.x + 1, p.y));
                pixels[p.x + 1][p.y] = false;
            }

            if (p.y > 0 && pixels[p.x][p.y - 1]) {
                queue.add(new Point(p.x, p.y - 1));
                pixels[p.x][p.y - 1] = false;
            }

            if (p.y < image.getHeight() - 1 && pixels[p.x][p.y + 1]) {
                queue.add(new Point(p.x, p.y + 1));
                pixels[p.x][p.y + 1] = false;
            }
        }

        return pixels;
    }

    /**
     * Draws a white border around picture limits.
     * @param image image to draw border in.
     */
    private void makeImageBorder(BufferedImage image) {
        int[] x = {1, -1, 0, 0, 1, 1, -1, -1};
        int[] y = {0, 0, 1, -1, 1, -1, 1, -1};
        int white = Color.WHITE.getRGB();

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                int color = image.getRGB(w, h);
                int alpha = (color >> 24) & 0xFF;
                if (alpha > 0 && color != white) {
                    for (int c = 0; c < x.length; c++) {
                        int nColor = image.getRGB(w + x[c], h + y[c]);
                        int nAlpha = (nColor >> 24) & 0xFF;
                        if (nAlpha == 0) {
                            image.setRGB(w + x[c], h + y[c], white);
                        }
                    }
                }
            }
        }
    }

}
