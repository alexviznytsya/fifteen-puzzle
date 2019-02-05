/* Controller.java
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
 *     This class is all possible actions. All game squares, buttons, menus
 *     actions are defined here.
 *
 * Constructors:
 *
 *     Controller(Settings settings, GameEngine gameEngine, GameWindow gameWindow);
 *
 * Getter functions:
 *
 *     public Settings getSettings();
 *
 *     public GameEngine getGameEngine();
 *
 *     public GameWindow getGameWindow()
 *
 * Setter functions:
 *
 *     public void setSettings(Settings settings);
 *
 *     public void setGameEngine(GameEngine gameEngine);
 *
 *     public void setGameWindow(GameWindow gameWindow)
 *
 * Other functions:
 *
 *     public void initialize();
 *
 *     private void createMenuItemsListeners();
 *
 *     private void createMenuItemsListeners();
 *
 *     private void createSquareListeners();
 *
 *     public void showCongratulationMessage();
 *
 */


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

public class Controller {

    private Settings settings;
    private GameEngine gameEngine;
    private GameWindow gameWindow;

    // Default constructor;
    Controller(Settings settings, GameEngine gameEngine, GameWindow gameWindow) {
        this.settings = settings;
        this.gameEngine = gameEngine;
        this.gameWindow = gameWindow;
    }

    // Getter functions:
    public Settings getSettings() {
        return this.settings;
    }

    public GameEngine getGameEngine() {
        return this.gameEngine;
    }

    public GameWindow getGameWindow() {
        return this.gameWindow;
    }


    // Setter functions:
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    // Other functions:
    public void initialize() {
        createMenuItemsListeners();
        createSquareListeners();
    }

    private void createMenuItemsListeners() {

        Map<String, JMenuItem> menuItems = this.gameWindow.getMenuItems();

        menuItems.get("newGame").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setDebugMode(false);
                gameEngine.startGame();
            }
        });

        menuItems.get("openImage").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpeg", "jpg", "png", "gif", "bmp"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                int result = fileChooser.showOpenDialog(gameWindow.getMainWindow());
                if (result == JFileChooser.APPROVE_OPTION) {
                    settings.setImageMode(true);
                    settings.setCustomImageBoard(true);
                    settings.setCustomImageBoardPath(fileChooser.getSelectedFile().getAbsolutePath());
                    gameWindow.createSquares();
                    createSquareListeners();
                    gameWindow.updateWindow(gameEngine.getSquarePositions(), gameEngine.getGameScore(), gameEngine.getGameComplexity());
                }
            }
        });

        menuItems.get("exit").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuItems.get("undo").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameEngine.undo();
            }
        });

        menuItems.get("undoAll").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameEngine.undoAll();
            }
        });

        menuItems.get("autoSolve").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameEngine.autoSolve();
            }
        });

        menuItems.get("gameManual").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameWindow.getMainWindow(),
                        "Available menu options and usage:\n\n" +
                                "File -> New Game: Creates new game with random game squares (Solvable)\n" +
                                "File -> Open Image: User can replace standard squares with own picture.\n" +
                                "*Note: User's image will be split into 16 squares and current squares \n" +
                                "will be replaced by new one.\n" +
                                "File -> Exit: Close the game\n\n" +
                                "Actions -> Undo: Remove last move.\n" +
                                "Actions -> Undo All: Undo all moves with anomation.\n" +
                                "Actions -> Auto Solve: Solve current puzzle and animate the solution.\n\n" +
                                "Help -> Game Manual: This manual :)\n" +
                                "Help -> About: Student who wrote this game.\n\n" +
                                "Debug -> New Game: Create new puzzle with ordered game squares from 1 to 15.\n" +
                                "Debug -> Turn Image Mode On/Off: Switch between image and button implementation\n" +
                                "of game squares.\n" +
                                "Debug -> Remove Custom Image: Remove user selected squares image and restore game\n" +
                                "default squares image\n" +
                        "",
                        "Game Manual", JOptionPane.PLAIN_MESSAGE);
            }
        });


        menuItems.get("about").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameWindow.getMainWindow(),
                        "Fifteen Puzzle\n" +
                                "\n" +
                                "Alex Viznytsya\n" +
                                "Fall 2017\n" +
                                "\n",
                        "About", JOptionPane.PLAIN_MESSAGE);
            }
        });

        menuItems.get("debugNewGame").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setDebugMode(true);
                gameEngine.startGame();
            }
        });

        menuItems.get("debugImageModeTriger").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(settings.isImageMode()) {
                    settings.setImageMode(false);
                    menuItems.get("debugImageModeTriger").setText("Turn Image Mode On");
                } else {
                    settings.setImageMode(true);
                    menuItems.get("debugImageModeTriger").setText("Turn Image Mode Off");

                }
                gameWindow.getMainWindow().getJMenuBar().validate();
                gameWindow.getMainWindow().getJMenuBar().updateUI();
                System.out.println("Image mode: " + settings.isImageMode());
                gameWindow.createSquares();
                createSquareListeners();
                gameWindow.updateWindow(gameEngine.getSquarePositions(), gameEngine.getGameScore(), gameEngine.getGameComplexity());
            }
        });

        menuItems.get("debugRemoveCustomImage").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setCustomImageBoard(false);
                settings.setCustomImageBoardPath("");
                gameWindow.createSquares();
                createSquareListeners();
                gameWindow.updateWindow(gameEngine.getSquarePositions(), gameEngine.getGameScore(), gameEngine.getGameComplexity());
            }
        });
    }

    private void createSquareListeners() {
        List<Component> squares = this.gameWindow.getBoardSquares();
        for (int i = 0; i < 15; i++) {
            squares.get(i).addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    gameEngine.moveSquare(e.getComponent());
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

    public void showCongratulationMessage() {
        int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (gameWindow.getMainWindow(), "Congratulation you won!\nDo you want play new game?","Congratulation!!!",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            settings.setDebugMode(false);
            gameEngine.startGame();
        }
        if(dialogResult == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        if(dialogResult == JOptionPane.CANCEL_OPTION) {
            this.gameEngine.getSquarePositions().set(15, 0);
            this.gameEngine.setGameInitialState(true);
            this.gameWindow.updateWindow(this.gameEngine.getSquarePositions(), this.gameEngine.getGameScore(), this.gameEngine.getGameComplexity());
        }
    }

}


