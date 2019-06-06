package pchess.gui;

import pchess.games.PreChessGame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Main window of this application.
 */
public class GameWindow extends JFrame {

    /**
     * Game screen.
     */
    private final GameScreen gameScreen;

    /**
     * Constructor. Makes a main game window.
     */
    public GameWindow() {
        super("P-CHESS");
        gameScreen = new GameScreen(this);

        setContentPane(gameScreen);
        setJMenuBar(new ProgramMenu(this));

        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(getSize());
        setPreferredSize(getSize());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                confirmExit();

            }
        });

    }

    /**
     * Shows a dialog to confirm exiting.
     */
    public void confirmExit() {
        int resposta = JOptionPane.showConfirmDialog(this,
                "Really exit?", "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Starts a new game.
     * 
     * @param pcg new prechess game.
     */
    public void startGame(PreChessGame pcg) {
        gameScreen.setGame(pcg);
        if (pcg != null) {
            setTitle(pcg.getName());
        } else{
            setTitle("PRECHESS");
        }
    }

    /**
     * Returns current game.
     * 
     * @return current game. 
     */
    public PreChessGame getGame() {
        if (gameScreen == null) {
            return null;
        }

        return gameScreen.getGame();
    }

    /**
     * Returns game screen.
     * 
     * @return game screen. 
     */
    public GameScreen getScreen() {
        return gameScreen;
    }
    
}
