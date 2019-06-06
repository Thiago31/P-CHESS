package pchess.gui;

import pchess.games.PreChessGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Application menubar.
 */
class ProgramMenu extends JMenuBar {

    /**
     * Menu "Game".
     */
    private final JMenu game;
    
    /**
     * Menu item "Start" in "Game" menu.
     */
    private final JMenuItem start;
    
    /**
     * Menu item "Configure" in "Game" menu.
     */
    private final JMenuItem configure;
    
    /**
     * Menu item "Exit" in "Game" menu.
     */
    private final JMenuItem exit;

    /**
     * Menu "Help".
     */
    private final JMenu help;
    /**
     * Menu item "About" in "Help" menu.
     */
    private final JMenuItem about;
    /**
     * Menu item "Manual" in "Help" menu.
     */
    private final JMenuItem manual;

    /**
     * parent frame.
     */
    GameWindow parentFrame;

    /**
     * Constructor. Makes a menu bar.
     * 
     * @param parent parent frame.
     */
    public ProgramMenu(GameWindow parent) {

        parentFrame = parent;

        game = new JMenu("Game");
        start = new JMenuItem("Start");
        start.addActionListener(new StartListener());

        configure = new JMenuItem("Configure");
        configure.addActionListener(new ConfigureListener());

        exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());

        game.add(start);
        game.add(configure);
        game.add(exit);

        help = new JMenu("Help");
        about = new JMenuItem("About");
        about.addActionListener(new AboutListener());
        manual = new JMenuItem("Manual");
        manual.addActionListener(new ManualListener());

        help.add(about);
        help.add(manual);

        add(game);
        add(help);

    }

    /**
     * Listener to start a new game.
     */
    private class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            PreChessGame selGame = SelectGame.showDialog(parentFrame);
            if (selGame != null) {
                parentFrame.startGame(selGame);
            }
        }
    }

    /**
     * Listener to exit.
     */
    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.confirmExit();
        }
    }

    /**
     * Listener to configure game appearence.
     */
    private class ConfigureListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ConfigDialog.showDialog(parentFrame, parentFrame.getScreen());
        }
    }

    /**
     * Listener to show credits.
     */
    private class AboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "P-CHESS is developed by\n"
                    + "Thiago Castilho Elias\n\n"
                    + "Any bug, comment or suggestion\n"
                    + "please send an e-mail to\n"
                    + "tcastilho31@yahoo.com.br";

            JOptionPane.showMessageDialog(parentFrame, message,
                    "About", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Listener to show game manual. 
     */
    private class ManualListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ManualWindow.showDialog(parentFrame);
        }
    }
}
