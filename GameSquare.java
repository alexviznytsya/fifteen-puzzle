/* GameSquare.java
 *
 * Fifteen Puzzle
 *
 * Alex Viznytsya
 * Fall 2017
 *
 * Project# 2
 * 10/04/2017
 *
 * Info: This interface is used for creating any square in the game.
 *       Example: JButton squares or JLabel squares with image inside.
 *
 */
public interface GameSquare {
    public int getPosition();
    public void setPosition(int position);
}
