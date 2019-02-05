/* GameWindow.java
 *
 * Fifteen Puzzle
 *
 * Alex Viznytsya
 * Fall 2017
 *
 * Project# 2
 * 10/04/2017
 *
 * Info:
 *     This class creates and updates main game window. This kind of GUI.
 *
 * Constructors:
 *
 *     GameWindow(Settings gSettings);
 *
 * Getter functions:
 *
 *     public Settings getSettings();
 *
 *     public Controller getController();
 *
 *     public GameEngine getGameEngine();
 *
 *     public Map<String, JPanel> getPanels();
 *
 *     public Map<String, JMenuItem> getMenuItems();
 *
 *     public Map<String, JLabel> getGameStats();
 *
 *     public List<Component> getBoardSquares()
 *
 * Setter functions:
 *
 *     public void setSettings(Settings settings);
 *
 *     public void setGameEngine(GameEngine gameEngine);
 *
 *     public void setController(Controller controller);
 *
 *     public void setMainWindow(JFrame mainWindow);
 *
 *     public void setPanels(Map<String, JPanel> panels);
 *
 *     public void setMenuItems(Map<String, JMenuItem> menuItems);
 *
 *     public void setBoardSquares(List<Component> boardSquares);
 *
 *     public void setGameStats(Map<String, JLabel> gameStats);
 *
 * Other functions:
 *
 *     public void initialize();
 *
 *     public void createSquares();
 *
 *     private JMenuBar createGameMenu();
 *
 *     private JPanel createGameStatsPanel()
 *
 *     private JPanel createGameBoardPanel();
 *
 *     public void updateWindow(List<Integer> squarePositions, int score, int complexity);
 *
 *     private void drawGameBoard(List<Integer> squarePositions);
 *
 *     private void drawGameInfo(int score, int complexity)
 *
 */

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWindow {

    private Settings settings;
    private Controller controller;
    private GameEngine gameEngine;
    private JFrame mainWindow;
    private Map<String, JPanel> panels;
    private Map<String, JMenuItem> menuItems;
    private Map<String, JLabel> gameStats;
    private List<Component> boardSquares;

    // Default constructor:
    GameWindow(Settings settings) {
        this.settings = settings;
        this.mainWindow = new JFrame();
        this.panels = new HashMap<String, JPanel>();
        this.menuItems = new HashMap<String, JMenuItem>();
        this.gameStats = new HashMap<String, JLabel>();
        this.boardSquares = new ArrayList<Component>(17);
    }

    // Getter functions:


    public Settings getSettings() {
        return this.settings;
    }

    public Controller getController() {
        return this.controller;
    }

    public GameEngine getGameEngine() {
        return this.gameEngine;
    }

    public JFrame getMainWindow() {
        return this.mainWindow;
    }

    public Map<String, JPanel> getPanels() {
        return this.panels;
    }

    public Map<String, JMenuItem> getMenuItems() {
        return this.menuItems;
    }

    public Map<String, JLabel> getGameStats() {
        return this.gameStats;
    }

    public List<Component> getBoardSquares() {
        return this.boardSquares;
    }

    // Setter functions:
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setMainWindow(JFrame mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setPanels(Map<String, JPanel> panels) {
        this.panels = panels;
    }

    public void setMenuItems(Map<String, JMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setBoardSquares(List<Component> boardSquares) {
        this.boardSquares = boardSquares;
    }

    public void setGameStats(Map<String, JLabel> gameStats) {
        this.gameStats = gameStats;
    }

    // Initialize window:
    public void initialize() {
        createSquares();
        this.mainWindow.setTitle(this.settings.getGameName());
        this.mainWindow.setResizable(this.settings.isResizable());
        this.mainWindow.setLocationRelativeTo(null);
        this.mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainWindow.setJMenuBar(createGameMenu());
        this.mainWindow.getContentPane().setLayout(new BoxLayout(this.mainWindow.getContentPane(), BoxLayout.Y_AXIS));
        this.mainWindow.getContentPane().add(Box.createRigidArea(new Dimension(0,20)));
        this.panels.put("gameStats", createGameStatsPanel());
        this.panels.put("gameBoard", createGameBoardPanel());
        this.mainWindow.getContentPane().add(this.panels.get("gameStats"));
        this.mainWindow.getContentPane().add(Box.createRigidArea(new Dimension(0,20)));
        this.mainWindow.getContentPane().add(this.panels.get("gameBoard"));
        this.mainWindow.pack();
        this.mainWindow.setVisible(true);
    }

    // Create game squares. Array of 17 objects are created.
    // In case of non graphical mode 16 buttons are created. Otherwise,
    // array of 17 cropped images (16 images and 1 empty):
    public void createSquares() {
        boardSquares.clear();
        int squareSize = settings.getSquareSize();
        if(settings.isImageMode()) {
            String imagePath = "";
            if(settings.isCustomImageBoard()) {
                imagePath = settings.getCustomImageBoardPath();
            } else {
                imagePath = settings.getImageBoardPath();
            }

            BufferedImage originalImage = null;
            try {
                originalImage = ImageIO.read(new File(imagePath));
            }catch (IOException e) {
                System.out.println("Cannot open default game board image: " + settings.getImageBoardPath());
                System.exit(1);
            }
            BufferedImage scaledImage = new BufferedImage(squareSize * 4, squareSize * 4, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaledImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.drawImage(originalImage, 0, 0, squareSize * 4, squareSize * 4,  null);
            g.dispose();

            int sx1 = 0;
            int sy1 = 0;
            int sx2 = squareSize;
            int sy2 = squareSize;
            int counter = 0;
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    BufferedImage croppedImage = new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_RGB);
                    g = croppedImage.createGraphics();
                    g.setComposite(AlphaComposite.Src);
                    g.drawImage(scaledImage, 0,0, settings.getSquareSize(), settings.getSquareSize(),
                                             sx1, sy1, sx2,sy2, null);
                    g.dispose();
                    ImageSquare square = new ImageSquare(settings.isCustomImageBoard());
                    ImageIcon squareIcon = new ImageIcon(croppedImage);
                    square.setIcon(squareIcon);
                    square.setPosition(counter++);
                    this.boardSquares.add(square);
                    sx1 += squareSize;
                    sx2 += squareSize;
                }
                sx1 = 0;
                sy1 += squareSize;
                sx2 = squareSize;
                sy2 += squareSize;
            }

            // check if board image is custom:
            if(settings.isCustomImageBoard()) {
                this.boardSquares.add(this.boardSquares.get(15));
                BufferedImage emptySquare = new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_RGB);
                Graphics2D es = emptySquare.createGraphics();
                es.setColor(Color.WHITE);
                es.fill(new Rectangle(0, 0, squareSize, squareSize));
                es.dispose();
                ImageSquare square = new ImageSquare(settings.isCustomImageBoard());
                ImageIcon squareIcon = new ImageIcon(emptySquare);
                square.setIcon(squareIcon);
                square.setPosition(15);
                this.boardSquares.set(15, square);
            }

        } else {
            for(int i = 0; i < 15; i++) {
                ButtonSquare square = new ButtonSquare((i + 1) + "");
                square.setPosition(i);
                square.setAlignmentX(Component.CENTER_ALIGNMENT);
                square.setPreferredSize(new Dimension(settings.getSquareSize(), settings.getSquareSize()));
                this.boardSquares.add(square);
            }
            boardSquares.add(Box.createRigidArea(new Dimension(settings.getSquareSize(), settings.getSquareSize())));
        }
    }


    // Create menu for game as:
    //  --------------------------------------------------------------
    //     File         |   Actions:     |    Help        |  Debug
    //  --------------------------------------------------------------
    //     New Game     |   Undo         |    Game Rules  |  New Ordered Game
    //     Open Image   |   Undo All     |    About       |  Turn Images Off
    //     Exit         |   Auto Solve   |                |  Remove Custom Image
    //  ---------------------------------------------------------------
    //
    private JMenuBar createGameMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileNewGameItem = new JMenuItem("New Game");
        fileNewGameItem.setMnemonic('n');
        this.menuItems.put("newGame", fileNewGameItem);
        JMenuItem fileOpenImageItem = new JMenuItem("Open Image");
        fileOpenImageItem.setMnemonic('o');
        this.menuItems.put("openImage", fileOpenImageItem);
        JMenuItem fileExitItem = new JMenuItem("Exit");
        fileExitItem.setMnemonic('x');
        this.menuItems.put("exit", fileExitItem);
        fileMenu.add(fileNewGameItem);
        fileMenu.add(fileOpenImageItem);
        fileMenu.add(fileExitItem);
        JMenu actionsMenu = new JMenu("Actions");
        JMenuItem actionsUndoItem = new JMenuItem("Undo");
        actionsUndoItem.setMnemonic('u');
        this.menuItems.put("undo", actionsUndoItem);
        JMenuItem actionsUndoAllItem = new JMenuItem("Undo All");
        actionsUndoAllItem.setMnemonic('a');
        this.menuItems.put("undoAll", actionsUndoAllItem);
        JMenuItem actionsAutoSolveItem = new JMenuItem("Auto Solve");
        actionsAutoSolveItem.setMnemonic('s');
        this.menuItems.put("autoSolve", actionsAutoSolveItem);
        actionsMenu.add(actionsUndoItem);
        actionsMenu.add(actionsUndoAllItem);
        actionsMenu.add(actionsAutoSolveItem);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpGameManualItem = new JMenuItem("Game Manual");
        helpGameManualItem.setMnemonic('m');
        this.menuItems.put("gameManual", helpGameManualItem);
        JMenuItem helpAboutItem = new JMenuItem("About");
        helpAboutItem.setMnemonic('a');
        this.menuItems.put("about", helpAboutItem);
        helpMenu.add(helpGameManualItem);
        helpMenu.add(helpAboutItem);
        JMenu debugMenu = new JMenu("Debug");
        JMenuItem debugNewGameItem = new JMenuItem("New Game (ordered)");
        debugNewGameItem.setMnemonic('n');
        this.menuItems.put("debugNewGame", debugNewGameItem);
        JMenuItem debugTurnImageModeTrigerItem = new JMenuItem("Turn Image Mode Off");
        debugTurnImageModeTrigerItem.setMnemonic('t');
        this.menuItems.put("debugImageModeTriger", debugTurnImageModeTrigerItem);
        JMenuItem debugDeleteCustomImageItem = new JMenuItem("Remove Custom Image");
        debugDeleteCustomImageItem.setMnemonic('r');
        this.menuItems.put("debugRemoveCustomImage", debugDeleteCustomImageItem);
        debugMenu.add(debugNewGameItem);
        debugMenu.add(debugTurnImageModeTrigerItem);
        debugMenu.add(debugDeleteCustomImageItem);
        menuBar.add(fileMenu);
        menuBar.add(actionsMenu);
        menuBar.add(helpMenu);
        menuBar.add(debugMenu);
        return menuBar;
    }

    // Create game score and complexity counters above the game board:
    private JPanel createGameStatsPanel() {
        JPanel gameInfoBar = new JPanel();
        gameInfoBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel gameScore = new JLabel("Game Score:");
        JLabel gameScoreValue = new JLabel();
        this.gameStats.put("score", gameScoreValue);
        JLabel gameComplexity = new JLabel("Game Complexity:");
        JLabel gameComplexityValue = new JLabel();
        this.gameStats.put("complexity", gameComplexityValue);
        gameInfoBar.add(gameScore);
        gameInfoBar.add(gameScoreValue);
        gameInfoBar.add(Box.createRigidArea(new Dimension(200,0)));
        gameInfoBar.add(gameComplexity);
        gameInfoBar.add(gameComplexityValue);
        return gameInfoBar;
    }

    // Create 4x4 game board:
    private JPanel createGameBoardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,4));
        panel.setPreferredSize(new Dimension(settings.getSquareSize() * 4, settings.getSquareSize() * 4));
        return panel;
    }

    // Update graphics or buttons on game board:
    public void updateWindow(List<Integer> squarePositions, int score, int complexity) {
        drawGameBoard(squarePositions);
        drawGameInfo(score, complexity);
        this.panels.get("gameStats").revalidate();
        this.panels.get("gameStats").repaint();
        this.panels.get("gameBoard").revalidate();
        this.panels.get("gameBoard").repaint();
    }

    // Redraw 4x4 game board:
    private void drawGameBoard(List<Integer> squarePositions) {
        this.panels.get("gameBoard").removeAll();
        for(int i = 0; i < 16; i++) {
            int square = squarePositions.get(i);
            if(square == 0) {
                panels.get("gameBoard").add(boardSquares.get(15));
            } else {
                GameSquare tSquare = (GameSquare)this.boardSquares.get(square - 1);
                tSquare.setPosition(i);
                this.panels.get("gameBoard").add(this.boardSquares.get(square - 1));
            }
        }
    }

    // Redraw game statistic data:
    private void drawGameInfo(int score, int complexity) {
        this.gameStats.get("score").setText(score + "");
        this.gameStats.get("complexity").setText(complexity + "");
    }

}