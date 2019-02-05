/* ImageSquare.java
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
 *     This class is container for graphics. User can use build in graphic
 *     design or select own picture and those cropped images will be stored
 *     in this container.
 *
 *
 * Constructors:
 *
 *    ImageSquare(boolean customImage);
 *
 * Getter functions:
 *
 *     public int getPosition();
 *
 * Setter functions:
 *
 *     public void setPosition(int position);
 *
 * Other functions:
 *
 *     **No other functions**
 *
 */

import javax.swing.*;
import java.awt.*;

public class ImageSquare extends JLabel implements GameSquare {
    private int position;

    // Default Constructor:
    ImageSquare(boolean customImage) {
        setBorder(BorderFactory.createRaisedBevelBorder());
        setCursor(new Cursor(12));
    }

    // Getter functions
    public int getPosition() {
        return this.position;
    }

    // Setter functions:
    public void setPosition(int position) {
        this.position = position;
    }
}
