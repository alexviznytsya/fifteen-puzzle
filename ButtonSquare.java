/* ButtonSquare.java
 *
 * Fifteen Puzzle
 *
 * Alex Viznytsya
 * Fall 2017
 *
 * Info:
 *     This class is for playing without any graphics. So user can,
 *     turn off graphical interface in "Debug" menu and play this game
 *     switching buttons instead of picture squares.
 *
 * Constructors:
 *
 *     ButtonSquare(String name);
 *
 * Getter functions:
 *
 *     public int getPosition();
 *
 * Setter functions:
 *
 *     public void setPosition(int position)
 *
 * Other functions:
 *
 *     **No other functions**
 *
 */

import javafx.scene.Cursor;

import javax.swing.JButton;

class ButtonSquare extends JButton implements GameSquare{

    private int position;

    // Default constructor:
    ButtonSquare(String name) {
        super(name);
        setCursor(new java.awt.Cursor(12));
    }

    // Getter functions:
    public int getPosition() {
        return this.position;
    }

    // Setter functions:
    public void setPosition(int position) {
        this.position = position;
    }
}