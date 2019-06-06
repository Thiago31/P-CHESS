package pchess;

import pchess.gui.GameWindow;

/**
 * Main class to start P-CHESS.
 */
public class Main {

    /**
     * Main function to start P-CHESS.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameWindow game = new GameWindow();
        game.setVisible(true);
    }

}
