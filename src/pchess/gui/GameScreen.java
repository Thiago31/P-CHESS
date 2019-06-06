package pchess.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import pchess.core.Piece;
import pchess.core.Player;
import pchess.core.Square;
import pchess.core.enums.SquareType;
import pchess.games.PreChessGame;

/**
 * This class implements a screen to show game graphics.
 */
public class GameScreen extends JPanel {

    /**
     * Prechess game that this screen shows.
     */
    private PreChessGame game;

    /**
     * Listener to handle mouse events for this game.
     */
    private final GameMouseListener listener;

    /**
     * Image to display while none game is selected. This image shows some
     * informations about this application.
     */
    private BufferedImage startingImage;

    /**
     * ChessBoard border color.
     */
    private Color cbColor;

    /**
     * Light square color.
     */
    private Color lsColor;

    /**
     * Dark square color.
     */
    private Color dsColor;

    /**
     * Defines if chessboard coordinates is drawn.
     */
    private boolean isCoordinateDrawn;

    /**
     * Row's names list.
     */
    private String[] rows;

    /**
     * Column's names list.
     */
    private String[] columns;

    /**
     * Controller variable to wait human player do move.
     */
    private boolean waitingPlayerToMove;

    /**
     * Enum that defines game states.
     */
    private enum GameState {
        FINISHED, COMPUTER_TO_MOVE, HUMAN_TO_MOVE;
    }

    /**
     * Current game state.
     */
    private GameState gameState;

    /**
     * Stroke to draw a 'X' over attacked pieces.
     */
    private final BasicStroke stroke;

    // Some attributes to help in drawing //
    private int sW; // screen width
    private int sH; // screen height
    private int sqS; // square size
    private int cbW; // chessboard width
    private int cbH; // chessboard height

    private int xp; // x position to start drawing
    private int yp; // y position to start drawing

    private int nR; // number of rows in chessboard
    private int nC; // number of columns in chessboard

    private int xs, ys, xsf, ysf; // chessboard squares limit

    /**
     * List of players in the game.
     */
    private final Player[] players;

    /**
     * Piece that is selected.
     */
    private Piece selectedPiece;

    /**
     * Object to draw piece images.
     */
    private final PieceImage pieceImage;

    /**
     * Frame that contains this screen.
     */
    private final GameWindow gameWindow;

    /**
     * Thread to run game motor.
     */
    private Thread thread;

    /**
     * Constructos. Makes this screen.
     *
     * @param gameWindow frame that contains this screen.
     */
    public GameScreen(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBackground(Color.WHITE);
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL imageURL = cl.getResource("resources/images/startingScreen.png");
            startingImage = ImageIO.read(imageURL);
        } catch (Exception e) {
            startingImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        }
        Dimension imageSize = new Dimension(startingImage.getWidth(), startingImage.getHeight());
        setPreferredSize(imageSize);
        setSize(imageSize);

        pieceImage = new PieceImage();

        cbColor = Color.RED;
        lsColor = Color.WHITE;
        dsColor = Color.BLACK;

        isCoordinateDrawn = true;

        players = new Player[2];

        selectedPiece = null;

        stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        listener = new GameMouseListener();

        addMouseListener(listener);
        addMouseMotionListener(listener);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                pieceImage.deriveImages(sqS);
                repaint();
            }
        });

        waitingPlayerToMove = false;

        gameState = GameState.FINISHED;

    }

    /**
     * Initiates variable coordinates values to draw this GameScreen.
     */
    private void initScreen() {
        sW = getWidth();
        sH = getHeight();

        int sqH = sH / (nR + 2);
        int sqW = sW / (nC + 2);

        sqS = Math.min(sqH, sqW);

        cbW = sqS * (nC + 1);
        cbH = sqS * (nR + 1);
        xp = (sW - cbW) / 2;
        yp = (sH - cbH) / 2;

        xs = xp + sqS / 2;
        ys = yp + sqS / 2;
        xsf = xs + nC * sqS;
        ysf = ys + nR * sqS;
    }

    /**
     * Sets game displayed in this screen.
     *
     * @param game new game.
     */
    public void setGame(PreChessGame game) {

        this.game = game;

        if (game == null) {
            xs = 0;
            xsf = 0;
            ys = 0;
            ysf = 0;
            repaint();
            return;
        }

        game.startGame();

        nR = game.getChessBoard().getNRows();
        nC = game.getChessBoard().getNColumns();

        rows = new String[nR];
        for (int i = 0; i < nR; i++) {
            rows[i] = String.valueOf((nR - i));
        }

        columns = new String[nC];
        for (int i = 0; i < nC; i++) {
            char c = (char) (i + 97);
            columns[i] = String.valueOf(c);
        }

        players[0] = game.getWhitePlayer();
        players[1] = game.getBlackPlayer();

        initScreen();
        pieceImage.deriveImages(sqS);
        repaint();

        thread = new Thread(new GameMotor());
        thread.start();
    }

    /**
     * Sets chessboard border color.
     *
     * @param cbColor new chessboard border color.
     */
    public void setChessboardColor(Color cbColor) {
        this.cbColor = cbColor;
    }

    /**
     * Sets light square color.
     *
     * @param lsColor new light square color.
     */
    public void setLightSquareColor(Color lsColor) {
        this.lsColor = lsColor;
    }

    /**
     * Sets dark square color.
     *
     * @param dsColor new dark square color.
     */
    public void setDarkSquareColor(Color dsColor) {
        this.dsColor = dsColor;
    }

    /**
     * Sets to draw or not chessboard coordinates.
     *
     * @param isCoordinateDrawn, if true, chessboard coordinates must be drawn.
     */
    public void setCoordinateDrawing(boolean isCoordinateDrawn) {
        this.isCoordinateDrawn = isCoordinateDrawn;
    }

    /**
     * Sets piece font.
     *
     * @param pieceFont new piece font.
     */
    public void setPieceFont(Font pieceFont) {
        pieceImage.deriveImages(sqS, pieceFont);
    }

    /**
     * Returns PreChess Game in this screen.
     *
     * @return prechess game in this screen.
     */
    public PreChessGame getGame() {
        return game;
    }

    /**
     * Shows end game dialog.
     */
    public void endGame() {
        EndGameDialog endDialog = new EndGameDialog(gameWindow, game.getChessBoard().getWinner());
        endDialog.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (game == null) {
            int xi = (getWidth() - startingImage.getWidth()) / 2;
            int yi = (getHeight() - startingImage.getHeight()) / 2;
            g.drawImage(startingImage, xi, yi, this);
            return;
        }

        initScreen();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawSquares(g2);
        drawCoordinates(g2);
        drawPieces(g2);
        drawMovements(g2);
    }

    /**
     * Draws chessboard border and squares.
     *
     * @param g graphics object.
     */
    private void drawSquares(Graphics2D g) {

        g.setColor(cbColor);
        g.fillRect(xp, yp, cbW, cbH);

        for (int i = 0; i < nR; i++) {
            for (int j = 0; j < nC; j++) {
                Square square = game.getChessBoard().getSquare(i, j);
                SquareType st = square.getSquareType();
                Color color = (st == SquareType.LIGHT) ? lsColor : dsColor;
                g.setColor(color);
                g.fillRect(xs + sqS * j, ys + sqS * i, sqS, sqS);
            }
        }

    }

    /**
     * Draws chessboard coordinates.
     *
     * @param g graphics object.
     */
    private void drawCoordinates(Graphics2D g) {

        if (isCoordinateDrawn == false) {
            return;
        }

        g.setColor(Color.BLACK);
        Font coFont = new Font("Serif", Font.PLAIN, sqS / 3);
        g.setFont(coFont);
        FontMetrics fm = g.getFontMetrics();

        int xfo, yfo;

        for (int i = 0; i < nR; i++) {
            xfo = xp + (sqS / 2 - fm.stringWidth(rows[i])) / 2;
            yfo = yp + sqS / 2 + i * sqS + (sqS + fm.getAscent()) / 2;
            g.drawString(rows[i], xfo, yfo);
        }

        for (int i = 0; i < nC; i++) {
            xfo = xp + sqS / 2 + i * sqS + (sqS - fm.stringWidth(columns[i])) / 2;
            yfo = yp + sqS / 2 + sqS * nR + (sqS / 2 + fm.getAscent()) / 2;
            g.drawString(columns[i], xfo, yfo);
        }

    }

    /**
     * Draws pieces.
     *
     * @param g graphics object.
     */
    private void drawPieces(Graphics2D g) {

        for (Player player : players) {
            for (Piece piece : player.getPieces()) {

                if (!piece.isLiving()) {
                    continue;
                }

                Square square = piece.getSquare();
                int is = square.getInternalRow();
                int js = square.getInternalColumn();

                int xr = xs + sqS * js;
                int yr = ys + sqS * is;
                g.drawImage(pieceImage.getPieceImage(piece.getPieceCode()), xr, yr, this);

            }
        }

    }

    /**
     * Draws movement markers.
     * 
     * @param g graphics object.
     */
    private void drawMovements(Graphics2D g) {

        if (selectedPiece == null) {
            return;
        }

        int d = (int) (((double) sqS) * 0.45 * (1 - Math.cos(Math.toRadians(45))));

        g.setColor(Color.RED);
        Stroke old = g.getStroke();
        g.setStroke(stroke);
        selectedPiece.getLegalMoves().stream().forEach((square) -> {
            int is = square.getInternalRow();
            int js = square.getInternalColumn();

            int xc = xs + sqS * js + sqS / 20;
            int yc = ys + sqS * is + sqS / 20;

            if (square.isOccupied()) {
                int x1 = xc + d;
                int y1 = yc + d;
                int x2 = xc + sqS * 9 / 10 - d;
                int y2 = yc + sqS * 9 / 10 - d;
                g.drawLine(x1, y1, x2, y2);
                g.drawLine(x1, y2, x2, y1);

            } else {
                int xa = xs + sqS * js + sqS / 3;
                int ya = ys + sqS * is + sqS / 3;
                g.fillOval(xa, ya, sqS / 3, sqS / 3);

            }
        });
        g.setStroke(old);
    }

    /**
     * Changes controller variable to wait human player to move.
     * 
     * @param boo if true, wait human player do move. 
     */
    synchronized private void changeWaiting(boolean boo) {
        waitingPlayerToMove = boo;
    }

    /**
     * Inner class to handle mouse events.
     */
    private class GameMouseListener extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {

            Square square = getSquare(e.getX(), e.getY());
            if (square == null) {
                setCursor(Cursor.getDefaultCursor());
                return;
            }

            if (gameState == GameState.COMPUTER_TO_MOVE) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                return;
            }

            if (selectedPiece != null && selectedPiece.isLegalMove(square)) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return;
            }

            if (square.isOccupied()) {
                Piece piece = square.getPiece();
                Player player = game.getPlayer(piece.getPieceColor());
                if (player.isHuman() && player.hasMove()) {
                    if ((selectedPiece == null && piece.isBlocked() == false) || selectedPiece == piece) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        return;
                    }
                }

            }
            setCursor(Cursor.getDefaultCursor());

        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if (gameState != GameState.HUMAN_TO_MOVE) {
                return;
            }

            Square square = getSquare(e.getX(), e.getY());
            if (square == null) {
                return;
            }
            if (square.isOccupied()) {
                Piece piece = square.getPiece();
                Player player = game.getPlayer(piece.getPieceColor());
                if (player.isHuman() && player.hasMove()) {
                    if (selectedPiece == null && piece.isBlocked() == false) {
                        selectedPiece = piece;

                        repaint();
                    } else if (selectedPiece == piece) {

                        selectedPiece = null;

                        repaint();
                    }
                } else if (selectedPiece != null && selectedPiece.isLegalMove(square)) {
                    selectedPiece.doMove(square);
                    changeWaiting(false);
                    selectedPiece = null;
                    repaint();
                }
            } else if (selectedPiece != null && selectedPiece.isLegalMove(square)) {
                selectedPiece.doMove(square);
                changeWaiting(false);
                selectedPiece = null;
                repaint();
            }
        }

        /**
         * Returns square pointed by mouse.
         *
         * @param x mouse x-position.
         * @param y mouse y-position.
         * @return square pointed by mouse.
         */
        private Square getSquare(int x, int y) {

            if (x < xs || x > xsf - 1 || y < ys || y > ysf - 1) {
                return null;
            } else {
                int ci = (x - xs) / sqS;
                int ri = (y - ys) / sqS;

                Square square = game.getChessBoard().getSquare(ri, ci);
                return square;
            }
        }
    }

    /**
     * Inner class to run game motor.
     */
    private class GameMotor implements Runnable {

        @Override
        public void run() {

            while (true) {

                Player player = game.getCurrentPlayer();
                if (player.isComputer()) {
                    gameState = GameState.COMPUTER_TO_MOVE;
                    player.doMove();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                } else {
                    gameState = GameState.HUMAN_TO_MOVE;
                    changeWaiting(true);

                    while (waitingPlayerToMove) {
                        if (!waitingPlayerToMove) {
                            System.out.println("A problem here");
                        }
                    }

                }
                repaint();

                game.passMove();

                if (game.isGameOver()) {
                    gameState = GameState.FINISHED;
                    selectedPiece = null;
                    break;
                }

            }

            endGame();

        }

    }

}
