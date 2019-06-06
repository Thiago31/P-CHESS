package pchess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import pchess.core.enums.PieceFont;

/**
 * Dialog window to configure game appearance.
 */
public class ConfigDialog extends JDialog {

    /**
     * Singleton to use this window.
     */
    private static ConfigDialog config;

    /**
     * Color chooser for dark squares.
     */
    private final SimpleColorChooser darkSquare;
    
    /**
     * Color chooser for light squares.
     */
    private final SimpleColorChooser lightSquare;
    
    /**
     * Color chooser for background.
     */
    private final SimpleColorChooser background;
    
    /**
     * Color chooser for chessboard border.
     */
    private final SimpleColorChooser border;
    
    /**
     * Check box to show/hide chessboard coordinates.
     */
    private final JCheckBox showCoordinates;
    
    /**
     * Piece font chooser.
     */
    private final FontChooser pieceFont;

    /**
     * Controller variable to reset {@code showCoordinates} state.
     */
    private boolean resetCoordinates;

    /**
     * Font used to draw this dialog.
     */
    private final Font font = new Font("Dialog", Font.PLAIN, 12);

    /**
     * Shows configuration dialog.
     * 
     * @param frame parent frame.
     * @param gameScreen game screen.
     */
    public static void showDialog(JFrame frame, GameScreen gameScreen) {
        if (config == null) {
            config = new ConfigDialog(frame, gameScreen);
        }
        config.setVisible(true);
    }

    /**
     * Constructor. Makes configuration dialog.
     * 
     * @param frame parent frame.
     * @param gameScreen game screen.
     */
    private ConfigDialog(JFrame frame, GameScreen gameScreen) {
        super(frame, "Configurations", true);

        darkSquare = new SimpleColorChooser(
                new Color[]{new Color(0, 0, 0), new Color(40, 40, 60),
                    new Color(0, 0, 60), new Color(0, 60, 0), new Color(0, 60, 60),
                    new Color(22, 49, 119), new Color(100, 23, 18),
                    new Color(55, 55, 0), new Color(90, 90, 90)});
        lightSquare = new SimpleColorChooser(
                new Color[]{Color.WHITE, new Color(255, 255, 204),
                    new Color(255, 204, 204), new Color(255, 225, 174),
                    new Color(255, 204, 102), new Color(204, 255, 255),
                    new Color(218, 218, 218), new Color(255, 202, 169),
                    new Color(255, 223, 236)});
        background = new SimpleColorChooser(
                new Color[]{Color.WHITE, Color.BLACK, Color.DARK_GRAY,
                    new Color(255, 204, 51), new Color(0, 102, 255),
                    new Color(51, 255, 0)});
        border = new SimpleColorChooser(
                new Color[]{Color.RED, Color.MAGENTA, Color.PINK, Color.ORANGE, Color.BLUE,
                    Color.YELLOW, Color.CYAN, new Color(189, 37, 141),
                    new Color(74, 204, 47)});

        showCoordinates = new JCheckBox("Show coordinates");
        showCoordinates.setSelected(true);
        showCoordinates.setFont(font);
        resetCoordinates = true;
        JPanel coord = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coord.add(showCoordinates);

        pieceFont = new FontChooser();
        JPanel choosePieces = new JPanel();
        choosePieces.add(pieceFont);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gameScreen.setBackground(background.getSelectedColor());
                gameScreen.setChessboardColor(border.getSelectedColor());
                gameScreen.setLightSquareColor(lightSquare.getSelectedColor());
                gameScreen.setDarkSquareColor(darkSquare.getSelectedColor());
                gameScreen.setCoordinateDrawing(showCoordinates.isSelected());
                gameScreen.setPieceFont(pieceFont.getPieceFont());
                gameScreen.repaint();
                resetCoordinates = showCoordinates.isSelected();
                setVisible(false);
            }
        });

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                setVisible(false);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(coord, BorderLayout.SOUTH);
        borderPanel.add(border, BorderLayout.CENTER);

        JTabbedPane tabbed = new JTabbedPane();
        tabbed.setTabPlacement(JTabbedPane.LEFT);
        tabbed.setFont(font);
        tabbed.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        tabbed.addTab("Light Square Color", lightSquare);
        tabbed.addTab("Dark Square Color", darkSquare);
        tabbed.addTab("Border Color", borderPanel);
        tabbed.addTab("Background Color", background);
        tabbed.addTab("Piece Style", pieceFont);

        JPanel content = new JPanel(new BorderLayout());
        content.add(buttonPanel, BorderLayout.SOUTH);
        content.add(tabbed, BorderLayout.CENTER);
        setContentPane(content);
        pack();
        setResizable(false);
        int xp = frame.getX() + (frame.getWidth() - getWidth()) / 2;
        int yp = frame.getY() + (frame.getHeight() - getHeight()) / 2;
        setLocation(xp, yp);
    }

    /**
     * Resets configuration state if user clicks cancel button.
     */
    private void reset() {
        darkSquare.reset();
        lightSquare.reset();
        background.reset();
        border.reset();
        showCoordinates.setSelected(resetCoordinates);
        pieceFont.reset();
    }

    /**
     * Inner class to implement simple color chooser.
     */
    private class SimpleColorChooser extends JPanel {

        /**
         * Selected color index.
         */
        private int selected = 0;

        /**
         * Constructor. Makes a SimpleColorChooser.
         * 
         * @param colors colors available in this color chooser. 
         */
        SimpleColorChooser(Color[] colors) {
            double sqr = Math.sqrt((double) colors.length);
            int rows = (int) Math.ceil(sqr);
            int columns = (int) Math.rint(sqr);
            setLayout(new GridLayout(rows, columns, 2, 2));
            Font font2 = new Font(Font.SERIF, Font.PLAIN, 26);
            ButtonGroup bg = new ButtonGroup();
            char boxChar = (char) 9608;
            String boxString = new String(new char[]{boxChar, boxChar});
            for (Color c : colors) {
                JRadioButton rb = new JRadioButton(boxString);
                rb.setFont(font2);
                rb.setForeground(c);
                bg.add(rb);
                add(rb);
            }
            ((JRadioButton) getComponent(selected)).setSelected(true);
        }

        /**
         * Returns selected color.
         * 
         * @return selected color.
         */
        public Color getSelectedColor() {
            int n = 0;
            for (Component c : getComponents()) {
                if (((JRadioButton) c).isSelected()) {
                    selected = n;
                    return c.getForeground();
                }
                n++;
            }
            return null;
        }

        /**
         * Resets color chooser state.
         */
        public void reset() {
            JRadioButton resetButton = (JRadioButton) getComponent(selected);
            resetButton.setSelected(true);
        }
    }

    /**
     * Inner class to implement chess font chooser for standard pieces.
     */
    private class FontChooser extends JPanel {

        /**
         * Selected font index.
         */
        private int selected = 0;

        /**
         * Constructor. Makes a FontChooser. Available fontes are defined in
         * {@code PieceFont} enum.
         */
        FontChooser() {
            int rows = PieceFont.values().length - 1;
            setLayout(new GridLayout(rows, 1, 2, 2));
            ButtonGroup bg = new ButtonGroup();
            for (int i = 0; i < rows; i++) {
                JRadioButton rb = new JRadioButton("kqrbnp");
                rb.setFont(PieceFont.values()[i].getFont());
                bg.add(rb);
                add(rb);
            }
            ((JRadioButton) getComponent(selected)).setSelected(true);
        }

        /**
         * Returns selected font.
         * 
         * @return selected font.
         */
        public Font getPieceFont() {
            int n = 0;
            for (Component c : getComponents()) {
                if (((JRadioButton) c).isSelected()) {
                    selected = n;
                    return c.getFont();
                }
                n++;
            }
            return null;
        }

        /**
         * Resets font choose state.
         */
        public void reset() {
            JRadioButton resetButton = (JRadioButton) getComponent(selected);
            resetButton.setSelected(true);
        }

    }
}
