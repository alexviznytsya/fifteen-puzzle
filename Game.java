/* Game.java
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
 *     The puzzle consists of a 4x4 grid with the numbers from 1 to 15 on
 *     the grid leaving one grid position empty. The numbers are mixed up the
 *     puzzle is solved when the number are put into the following order:
 *
 *     |-----|-----|-----|-----|
 *     |  1  |  2  |  3  |  4  |
 *     |-----|-----|-----|-----|
 *     |  5  |  6  |  7  |  8  |
 *     |-----|-----|-----|-----|
 *     |  9  | 10  | 11  | 12  |
 *     |-----|-----|-----|-----|
 *     | 13  | 14  | 15  | 16  |
 *     |-----|-----|-----|-----|
 *
 *     To solve the puzzle, a number that is next to the empty position is
 *     moved into the empty position. By "next to", the number can be above,
 *     below, to the left or to the right of the empty position. The empty
 *     position will now occupy the grid position were the number had been.
 *
 * Constructors:
 *
 *     public Game();
 *
 * Getter functions:
 *
 *     **No getter functions**
 *
 * Setter functions:
 *
 *     **No setter functions**
 *
 * Other functions:
 *
 *     **No other functions**
 *
 */

public class Game {

    public static void main(String[] args) {
        Settings settings = new Settings();
        GameWindow gameWindow = new GameWindow(settings);
        gameWindow.initialize();
        GameEngine gameEngine = new GameEngine(settings, gameWindow);
        Controller controller = new Controller(settings, gameEngine, gameWindow);
        controller.initialize();
        gameEngine.setController(controller);
        gameEngine.startGame();

    }
}