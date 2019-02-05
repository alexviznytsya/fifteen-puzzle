/* Settings.java
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
 *     This class is all possible settings that can be changed in this game.
 *
 * Constructors:
 *
 *     public Settings();
 *
 * Getter functions:
 *
 *    public String getGameName();
 *
 *    public boolean isDebugMode();
 *
 *    public int getSquareSize();
 *
 *    public boolean isResizable();
 *
 *    public boolean isImageMode()
 *
 *    public String getImageBoardPath();
 *
 *    public boolean isCustomImageBoard();
 *
 *    public String getCustomImageBoardPath();
 *
 *
 * Setter functions:
 *
 *    public void setGameName(String gName);
 *
 *    public void setGameName(String gName);
 *
 *    public void setSquareSize(int slSize);
 *
 *    public void setResizable(boolean resizable);
 *
 *    public void setImageMode(boolean imageMode);
 *
 *    public void setImageBoardPath(String imageBoardPath);
 *
 *    public void setCustomImageBoard(boolean customImageBoard);
 *
 *    public void setCustomImageBoardPath(String customImageBoardPath)
 *
 *
 * Other functions:
 *
 *    **No other functions**
 *
 */

public  class Settings {

    private boolean debugMode;
    private String gameName;
    private int squareSize;
    private boolean resizable;
    private boolean imageMode;
    private String imageBoardPath;
    private boolean customImageBoard;
    private String customImageBoardPath;

    // Default constructor:
    Settings() {
        this.debugMode = false;
        this.gameName = "Project 2 - Fifteen Puzzle";
        this.squareSize = 200;
        this.resizable = false;
        this.imageMode = true;
        this.imageBoardPath = "gameboard.jpg";
        this.customImageBoard = false;
        this.customImageBoardPath = "";
    }

    // Getter functions:
    public boolean isDebugMode() {
        return this.debugMode;
    }

    public String getGameName() {
        return this.gameName;
    }

    public int getSquareSize() {
        return this.squareSize;
    }

    public boolean isResizable() {
        return this.resizable;
    }

    public boolean isImageMode() {
        return this.imageMode;
    }

    public String getImageBoardPath() {
        return this.imageBoardPath;
    }

    public boolean isCustomImageBoard() {
        return this.customImageBoard;
    }

    public String getCustomImageBoardPath() {
        return this.customImageBoardPath;
    }

    // Setter functions:
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setGameName(String gName) {
        this.gameName = gName;
    }

    public void setSquareSize(int slSize) {
        this.squareSize = slSize;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public void setImageMode(boolean imageMode) {
        this.imageMode = imageMode;
    }

    public void setImageBoardPath(String imageBoardPath) {
        this.imageBoardPath = imageBoardPath;
    }

    public void setCustomImageBoard(boolean customImageBoard) {
        this.customImageBoard = customImageBoard;
    }

    public void setCustomImageBoardPath(String customImageBoardPath) {
        this.customImageBoardPath = customImageBoardPath;
    }
}
