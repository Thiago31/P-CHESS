package pchess.gui;

import pchess.games.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import pchess.core.ComputerPlayer;
import pchess.core.enums.Difficulty;
import pchess.core.enums.PieceColor;
import pchess.core.enums.PlayerType;

/**
 * This dialog window is used to select a prechess game to play.
 */
public class SelectGame extends JDialog {

    /**
     * Panel to show options for a game. Each game has its own options.
     */
    private final JPanel gameOptions;
    
    /**
     * Card layout to change between game options.
     */
    private final CardLayout cl;

    /**
     * Selected prechess game.
     */
    private PreChessGame selectedGame = null;
    
    /**
     * Selected player type for white pieces.
     */
    private PlayerType wt;
    
    /**
     * Selected player type for black pieces.
     */
    private PlayerType bt;

    /**
     * Constructor. Makes a game selector window.
     * 
     * @param parentFrame parent frame.
     */
    private SelectGame(JFrame parentFrame) {
        super(parentFrame, "Select Game", true);
        GameSender[] games = {new KingDuelSender(),
            new KillerQueenSender(), new KillerRookSender(),
            new KillerKnightSender(), new BishopAgainstPawnSender(),
            new KnightAgainstPawnSender(), new PawnBattleSender()};

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel opcoes = new JPanel();
        opcoes.setLayout(new BoxLayout(opcoes, BoxLayout.PAGE_AXIS));
        ButtonGroup agrupar = new ButtonGroup();
        for (GameSender game : games) {
            agrupar.add(game.getRadioButton());
            opcoes.add(game.getRadioButton());
        }
        ((JRadioButton) opcoes.getComponent(0)).setSelected(true);

        JPanel playerOptions = new JPanel();
        playerOptions.setLayout(new BoxLayout(playerOptions, BoxLayout.PAGE_AXIS));
        JPanel whitePlayer = new JPanel();
        whitePlayer.setLayout(new GridLayout(1, 2));
        JRadioButton wHuman = new JRadioButton("Human");
        wHuman.setSelected(true);
        JRadioButton wRobot = new JRadioButton("Computer");
        ButtonGroup wGroup = new ButtonGroup();
        wGroup.add(wHuman);
        wGroup.add(wRobot);
        whitePlayer.add(wHuman);
        whitePlayer.add(wRobot);
        makeBorder(whitePlayer, "White Player", 6);

        JPanel blackPlayer = new JPanel();
        blackPlayer.setLayout(new GridLayout(1, 2));
        JRadioButton bHuman = new JRadioButton("Human");
        JRadioButton bRobot = new JRadioButton("Computer");
        bRobot.setSelected(true);
        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(bHuman);
        bGroup.add(bRobot);
        blackPlayer.add(bHuman);
        blackPlayer.add(bRobot);
        makeBorder(blackPlayer, "Black Player", 6);

        Hashtable<Integer, JLabel> difficultyLabels = new Hashtable<>();
        difficultyLabels.put(0, new JLabel("easy"));
        difficultyLabels.put(1, new JLabel("medium"));
        difficultyLabels.put(2, new JLabel("hard"));
        JSlider difficulty = new JSlider(0, 2, 0);

        makeBorder(difficulty, "Difficulty", 6);
        difficulty.setLabelTable(difficultyLabels);
        difficulty.setPaintLabels(true);

        gameOptions = new JPanel();
        cl = new CardLayout();
        gameOptions.setLayout(cl);
        gameOptions.validate();
        for (GameSender gs : games) {
            cl.addLayoutComponent(gs.getOptionPanel(), gs.getGameName());
            gameOptions.add(gs.getOptionPanel());
        }

        playerOptions.add(whitePlayer);
        playerOptions.add(blackPlayer);
        playerOptions.add(difficulty);
        playerOptions.add(gameOptions);
        int pw = 0;
        int ph = 0;
        for (Component c : playerOptions.getComponents()) {

            if (c.getPreferredSize().width > pw) {
                pw = c.getPreferredSize().width;
            }
            ph += c.getPreferredSize().height;
        }

        playerOptions.setPreferredSize(new Dimension(pw, ph));

        JScrollPane scroller = new JScrollPane(opcoes);

        makeBorder(scroller, "Select a game", 10);
        scroller.setPreferredSize(new Dimension(pw, ph));

        JPanel central = new JPanel();
        central.setLayout(new GridLayout(1, 2));
        central.add(scroller);
        central.add(playerOptions);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent evt) -> {
            wt = (wHuman.isSelected() ? PlayerType.HUMAN
                    : PlayerType.COMPUTER);
            bt = (bHuman.isSelected() ? PlayerType.HUMAN
                    : PlayerType.COMPUTER);

            for (int i = 0; i < games.length; i++) {
                JRadioButton sel = (JRadioButton) opcoes.getComponent(i);
                if (sel.isSelected()) {
                    selectedGame = games[i].getNewGame();
                }
            }

            ComputerPlayer.setDifficulty(
                    Difficulty.values()[difficulty.getValue()]);
            
            dispose();
        });

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                selectedGame = null;
                dispose();
            }
        });
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        panel.add(central, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        pack();
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentFrame);
    }

    /**
     * Shows a dialog window to select a game to play.
     * 
     * @param parentFrame parent frame.
     * @return selected game.
     */
    public static PreChessGame showDialog(JFrame parentFrame) {
        SelectGame selJogo = new SelectGame(parentFrame);
        selJogo.setVisible(true);
        return selJogo.getSelectedGame();
    }

    /**
     * Returns selected game.
     * 
     * @return selected game. 
     */
    private PreChessGame getSelectedGame() {
        return selectedGame;
    }

    /**
     * Makes a border in component.
     * 
     * @param c component to make border.
     * @param title border title.
     * @param s border spacing.
     */
    private void makeBorder(JComponent c, String title, int s) {
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(s, s, s, s),
                BorderFactory.createTitledBorder(title)
        ));
    }

    /**
     * Parent class to game senders. A game sender is used to create new game
     * from selected game and display game options.
     */
    private abstract class GameSender {

        /**
         * Panel to show game options. 
         */
        protected final JPanel options;
        
        /**
         * Radio button to show game name and select this game.
         */
        private final JRadioButton rbutton;
        
        /**
         * Game name.
         */
        private final String gameName;

        /**
         * Constructor. Makes a new GameSender
         * 
         * @param gameName game name. 
         */
        GameSender(String gameName) {
            this.gameName = gameName;
            rbutton = new JRadioButton(gameName);
            rbutton.setOpaque(false);
            rbutton.addActionListener((ActionEvent e) -> {
                cl.show(gameOptions, gameName);
            });
            options = new JPanel(new GridLayout(2, 1));
            makeBorder(options, "Options", 6);

        }

        /**
         * Returns radio button.
         * 
         * @return radio button. 
         */
        JRadioButton getRadioButton() {
            return rbutton;
        }

        /**
         * Returns option panel.
         * 
         * @return option panel.
         */
        JPanel getOptionPanel() {
            return options;
        }

        /**
         * Returns game name.
         * 
         * @return game name.
         */
        String getGameName() {
            return gameName;
        }

        /**
         * Returns new game.
         * 
         * @return new game.
         */
        abstract PreChessGame getNewGame();
    }

    /**
     * Game sender with numeric option.
     */
    private abstract class NumericOptionsGame extends GameSender {

        /**
         * Values available to choose.
         */
        protected final int[] values;
        
        /**
         * Panel with values to choose.
         */
        private final JPanel sel;

        /**
         * Constructor. Makes a NumericOptionsGame.
         * 
         * @param gameName game name.
         * @param values list with available values.
         * @param label label to define options values.
         */
        NumericOptionsGame(String gameName, int[] values, String label) {
            super(gameName);
            this.values = values;
            options.add(new JLabel(label));
            sel = new JPanel(new GridLayout(1, values.length));
            ButtonGroup bg = new ButtonGroup();
            for (int i = 0; i < values.length; i++) {
                JRadioButton rb = new JRadioButton(String.valueOf(values[i]));
                sel.add(rb);
                bg.add(rb);
            }
            ((JRadioButton) sel.getComponent(0)).setSelected(true);
            options.add(sel);
        }

        /**
         * Returns selected value.
         * 
         * @return selected value. 
         */
        int getSelectedValue() {
            for (int i = 0; i < values.length; i++) {
                JRadioButton rb = (JRadioButton) sel.getComponent(i);
                if (rb.isSelected()) {
                    return values[i];
                }
            }
            return 0;
        }
    }

    /**
     * Game sender for King Duel.
     */
    private class KingDuelSender extends NumericOptionsGame {

        /**
         * Constructor. Makes a KingDuelSender.
         */
        KingDuelSender() {
            super("King Duel", new int[]{10, 20, 30, 40},
                    "Maximum number of movements");
        }

        @Override
        PreChessGame getNewGame() {
            return new KingDuel(wt, bt, getSelectedValue());
        }
    }

    /**
     * Game sender for Killer Queen.
     */
    private class KillerQueenSender extends NumericOptionsGame {

        /**
         * Constructor. Makes a KillerQueenSender.
         */
        KillerQueenSender() {
            super("Killer Queen", new int[]{8, 12, 16},
                    "Number of diamonds");
        }

        @Override
        PreChessGame getNewGame() {
            return new KillerQueen(wt, bt, getSelectedValue());
        }
    }

    /**
     * Game sender for Killer Rook.
     */
    private class KillerRookSender extends NumericOptionsGame {

        /**
         * Constructor. Makes a KillerRookSender.
         */
        KillerRookSender() {
            super("Killer Rook", new int[]{8, 12, 16},
                    "Number of diamonds");
        }

        @Override
        PreChessGame getNewGame() {
            return new KillerRook(wt, bt, getSelectedValue());
        }
    }

    /**
     * Game sender for Killer Knight.
     */
    private class KillerKnightSender extends NumericOptionsGame {

        /**
         * Constructor. Makes a KillerKnightSender.
         */
        KillerKnightSender() {
            super("Killer Knight", new int[]{8, 12, 16},
                    "Number of diamonds");
        }

        @Override
        PreChessGame getNewGame() {
            return new KillerKnight(wt, bt, getSelectedValue());
        }
    }

    /**
     * Game sender for minor piece games.
     */
    private abstract class MinorPieceGame extends GameSender {

        /**
         * Radio button to define minor pieces as white.
         */
        protected final JRadioButton asWhite;
        
        /**
         * Radio button to define minor pieces as black.
         */
        protected final JRadioButton asBlack;

        /**
         * Constructor. Makes a MinorPieceGame.
         * @param gameName game name.
         * @param piece minor piece name.
         */
        MinorPieceGame(String gameName, String piece) {
            super(gameName);
            asWhite = new JRadioButton(piece + " as white");
            asBlack = new JRadioButton(piece + " as black");
            ButtonGroup bg = new ButtonGroup();
            bg.add(asWhite);
            bg.add(asBlack);
            options.add(asWhite);
            options.add(asBlack);
            asWhite.setSelected(true);
        }

        /**
         * Returns minor piece color.
         * 
         * @return minor piece color.
         */
        PieceColor getMinorPieceColor() {
            if (asWhite.isSelected()) {
                return PieceColor.WHITE;
            } else {
                return PieceColor.BLACK;
            }
        }
    }

    /**
     * Game sender for Bishops Against Pawns.
     */
    private class BishopAgainstPawnSender extends MinorPieceGame {

        /**
         * Constructor. Makes a BishopAgainstPawnSender.
         */
        BishopAgainstPawnSender() {
            super("Bishops Against Pawns", "Bishop");
        }

        @Override
        PreChessGame getNewGame() {
            return new BishopAgainstPawn(wt, bt, getMinorPieceColor());
        }
    }

    /**
     * Game sender for Knights Against Pawns.
     */
    private class KnightAgainstPawnSender extends MinorPieceGame {

        /**
         * Constructor. Makes a KnightAgainstPawnSender.
         */
        KnightAgainstPawnSender() {
            super("Knights Against Pawns", "Knight");
        }

        @Override
        PreChessGame getNewGame() {
            return new KnightAgainstPawn(wt, bt, getMinorPieceColor());
        }
    }

    /**
     * Game sender for Pawn Battle.
     */
    private class PawnBattleSender extends NumericOptionsGame {

        /**
         * Constructor. Makes a PawnBattleSender.
         */
        PawnBattleSender() {
            super("Pawn Battle", new int[]{4, 5, 6, 7, 8},
                    "Number of columns");
        }

        @Override
        PreChessGame getNewGame() {
            return new PawnBattle(wt, bt, getSelectedValue());
        }
    }
}
