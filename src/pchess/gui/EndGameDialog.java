package pchess.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pchess.core.enums.PieceColor;
import pchess.games.PreChessGame;

/**
 * Dialog that is show after endgame.
 */
public class EndGameDialog extends JDialog {

    /**
     * Constructor. Makes an end game dialog. This dialog is show after endgame.
     * Winner player and winner message is displayed. User can restart the 
     * same game, choose another game or exit.
     * @param gameWindow parent frame.
     * @param pieceColor piece color for winner player.
     */
    public EndGameDialog(GameWindow gameWindow, PieceColor pieceColor) {
        super(gameWindow, "End Game", true);
        PreChessGame game = gameWindow.getGame();

        String imageName;
        if (pieceColor == PieceColor.WHITE) {
            imageName = "resources/images/whiteWin.png";
        } else if (pieceColor == PieceColor.BLACK) {
            imageName = "resources/images/blackWin.png";
        } else {
            imageName = "resources/images/draw.png";
        }

        URL imageURL = getClass().getClassLoader().getResource(imageName);
        ImageIcon icon;
        try {
            icon = new ImageIcon(ImageIO.read(imageURL));
        } catch (IOException ioe) {
            icon = new ImageIcon();
        }

        setResizable(false);

        JLabel messageLabel = new JLabel(game.getWinnerMessage());
        messageLabel.setHorizontalTextPosition(JLabel.CENTER);
        messageLabel.setVerticalTextPosition(JLabel.BOTTOM);
        messageLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        messageLabel.setIcon(icon);
        messageLabel.setIconTextGap(12);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JButton restart = new JButton("Restart");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startGame(game.newGame());
                dispose();
            }
        });

        JButton another = new JButton("Another game");
        another.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                PreChessGame selGame = SelectGame.showDialog(gameWindow);
                gameWindow.startGame(selGame);
            }
        });

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.confirmExit();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        buttonPanel.add(restart);
        buttonPanel.add(another);
        buttonPanel.add(exit);

        JPanel content = new JPanel(new BorderLayout());
        content.add(messageLabel, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(content);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        pack();

        setLocationRelativeTo(gameWindow);
    }
}
