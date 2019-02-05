/* GameEngine.java
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
 *     This class is the logic of the game. From moves determination and
 *     maintaining move history to undo and auto solve features.
 *
 * Constructors:
 *
 *      GameEngine(Settings settings, GameWindow gameWindow);
 *
 * Getter functions:
 *
 *    public GameWindow getGameWindow();
 *
 *    public Settings getSettings();
 *
 *    public Controller getController();
 *
 *    public List<Integer> getSquarePositions();
 *
 *    public List<Integer> getDefaultSquarePositions();
 *
 *    public int getGameScore();
 *
 *    public int getGameComplexity();
 *
 *    public Stack<List<Integer>> getMoveHistory();
 *
 *     public boolean isGameInitialState();
 *
 * Setter functions:
 *
 *    public void setGameWindow(GameWindow gameWindow);
 *
 *    public void setSettings(Settings settings);
 *
 *    public void setController(Controller controller);
 *
 *    public void setSquarePositions(List<Integer> squarePositions);
 *
 *    public void setDefaultSquarePositions(List<Integer> defaultSquarePositions);
 *
 *    public void setGameScore(int gameScore);
 *
 *    public int getGameComplexity();
 *
 *    public Stack<List<Integer>> getMoveHistory();
 *
 *    public void setGameInitialState(boolean gameInitialState);
 *
 * Other functions:
 *
 *     public void startGame();
 *
 *     public void generateDebugLayout();
 *
 *     public void generateRandomLayout();
 *
 *     private void copyArrayList(List<Integer> from, List<Integer> to);
 *
 *     private List<Integer> orderedLayout();
 *
 *     private List<Integer> randomLayout();
 *
 *     public void updateWindow();
 *
 *     private boolean isSolvable();
 *
 *     private boolean isSolved(List<Integer> squarePositions);
 *
 *     public void moveSquare(Component button);
 *
 *     private void swapSquares(int fromPosition, int toPosition);
 *
 *     public void undo();
 *
 *     public void undoAll();
 *
 *     private String squaresToString(List<Integer> squarePositions);
 *
 *     public void autoSolve();
 *
 *     private void showSolution(List<List<Integer>> mp);
 *
 */

import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameEngine {

    private GameWindow gameWindow;
    private Settings settings;
    private Controller controller;
    private List<Integer> squarePositions;
    private List<Integer> defaultSquarePositions;
    private int gameScore;
    private int gameComplexity;
    private Stack<List<Integer>> moveHistory;
    private boolean gameInitialState;

    GameEngine(Settings settings, GameWindow gameWindow){
        this.settings = settings;
        this.gameWindow = gameWindow;
        this.controller = null;
        this.squarePositions = new ArrayList<Integer>(16);
        this.defaultSquarePositions = new ArrayList<Integer>(16);
        this.gameScore = 0;
        this.gameComplexity = 0;
        this.moveHistory = new Stack<List<Integer>>();
        this.gameInitialState = true;
    }


    // Getter functions:
    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public Controller getController() {
        return this.controller;
    }

    public List<Integer> getSquarePositions() {
        return this.squarePositions;
    }

    public List<Integer> getDefaultSquarePositions() {
        return this.defaultSquarePositions;
    }

    public int getGameScore() {
        return this.gameScore;
    }

    public int getGameComplexity() {
        return this.gameComplexity;
    }

    public Stack<List<Integer>> getMoveHistory() {
        return this.moveHistory;
    }

    public boolean isGameInitialState() {
        return this.gameInitialState;
    }

    // Setter Functions:
    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setSquarePositions(List<Integer> squarePositions) {
        this.squarePositions = squarePositions;
    }

    public void setDefaultSquarePositions(List<Integer> defaultSquarePositions) {
        this.defaultSquarePositions = defaultSquarePositions;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public void setGameComplexity(int gameComplexity) {
        this.gameComplexity = gameComplexity;
    }

    public void setMoveHistory(Stack<List<Integer>> moveHistory) {
        this.moveHistory = moveHistory;
    }

    public void setGameInitialState(boolean gameInitialState) {
        this.gameInitialState = gameInitialState;
    }

    // Other functions:
    public void startGame() {
        this.gameInitialState = true;
        if(this.settings.isDebugMode()) {
           generateDebugLayout();
           this.gameComplexity = 0;
        } else {
            generateRandomLayout();
            while(!isSolvable()) {
                generateRandomLayout();
            }
        }
        this.gameScore = 0;
        copyArrayList(this.squarePositions, this.defaultSquarePositions);
        updateWindow();
    }

    public void generateDebugLayout() {
        copyArrayList(orderedLayout(), this.squarePositions);
    }

    public void generateRandomLayout() {
        copyArrayList(randomLayout(), this.squarePositions);
    }

    private void copyArrayList(List<Integer> from, List<Integer> to) {
        to.clear();
        for(int i = 0; i < from.size(); i++) {
            to.add(from.get(i));
        }
    }

    private List<Integer> orderedLayout() {
        List<Integer> newList = new ArrayList<Integer>(16);
        for(int i = 1; i < 16; i++) {
            newList.add(i);
        }
        newList.add(0);
        return newList;
    }

    private List<Integer> randomLayout() {
        List<Integer> random = orderedLayout();
        random.remove(15);
        Collections.shuffle(random);
        random.add(0);
        return random;
    }

    public void updateWindow() {
        this.gameWindow.updateWindow(this.squarePositions, gameScore, gameComplexity);
        if(isSolved(this.squarePositions)) {
            System.out.println("Puzzle is solved!!!");
            if(settings.isCustomImageBoard()){
                this.squarePositions.set(15, 17);
                this.gameWindow.updateWindow(this.squarePositions, gameScore, gameComplexity);
            }
            controller.showCongratulationMessage();
        }
    }

    private boolean isSolvable() {
        int numOfInversions = 0;
        for(int i = 0; i < 15; i++) {
            int squareValue = this.squarePositions.get(i);
            for(int j = i + 1; j < 15; j++) {
                if(squareValue > this.squarePositions.get(j)) {
                    numOfInversions++;
                }
            }
        }
        System.out.println("Num  of inversions: " + numOfInversions);
        this.gameComplexity = numOfInversions;
        if((numOfInversions % 2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSolved(List<Integer> squarePositions) {
        for(int i = 0; i < 14; i++) {
            if(squarePositions.get(i+1) - squarePositions.get(i) == 1) {
                continue;
            } else {
                return false;
            }
        }
        if(this.gameInitialState) {
            return false;
        } else {
            return true;
        }
    }

    public void moveSquare(Component button) {
        this.gameInitialState = false;
        if(this.gameScore != 0) {
            List<Integer> newMove = new ArrayList<Integer>(16);
            copyArrayList(squarePositions, newMove);
            this.moveHistory.push(newMove);
        }
        GameSquare square = (GameSquare)button;
        int curPos = square.getPosition();
        int moveLeftPos = curPos - 1;
        int moveRightPos = curPos + 1;
        int moveUpPos = curPos - 4;
        int moveDownPos = curPos + 4;
        if(moveLeftPos >= 0 && this.squarePositions.get(moveLeftPos) == 0) {
            swapSquares(curPos, moveLeftPos);
        } else if (moveUpPos >=0 && this.squarePositions.get(moveUpPos) == 0) {
            swapSquares(curPos, moveUpPos);
        } else if (moveRightPos < 16 && this.squarePositions.get(moveRightPos) == 0){
            swapSquares(curPos, moveRightPos);
        } else if (moveDownPos < 16 && this.squarePositions.get(moveDownPos) == 0) {
            swapSquares(curPos, moveDownPos);
        }
        updateWindow();
    }

    private void swapSquares(int fromPosition, int toPosition) {
        System.out.println("Swap from: " + (fromPosition) + " to: " + (toPosition));
        this.squarePositions.set(toPosition, this.squarePositions.get(fromPosition));
        this.squarePositions.set(fromPosition, 0);
        this.gameScore++;
    }

    public void undo() {
        if(this.gameScore != 0) {
            this.gameScore--;
            if( !this.moveHistory.isEmpty()) {
                copyArrayList(this.moveHistory.pop(), this.squarePositions);
            } else {
                copyArrayList(this.defaultSquarePositions, this.squarePositions);
            }
            updateWindow();
        }
    }

    public void undoAll() {
        System.out.println("Clicked Undo all");
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(!moveHistory.isEmpty()) {
                    undo();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
                undo();
            }});
        t.start();

    }

    private String squaresToString(List<Integer> squarePositions) {
        String text = new String();
        for(int i = 0; i < 16; i++) {
            text += new String(squarePositions.get(i) + "");
        }
        return text;
    }

    public void autoSolve() {
        List<List<Integer>> movementStack = new ArrayList<List<Integer>>();
        List<Integer> initialSquarePositions = new ArrayList<Integer>(16);
        copyArrayList(this.squarePositions, initialSquarePositions);

        Pair<List<Integer>, List<List<Integer>>> tuple = new Pair<List<Integer>, List<List<Integer>>>(initialSquarePositions, movementStack);
        Queue<Pair<List<Integer>, List<List<Integer>>>> gsQueue = new LinkedList<Pair<List<Integer>, List<List<Integer>>>>();
        gsQueue.add(tuple);

        Set<String> gsSet = new LinkedHashSet<String>();
        gsSet.add(squaresToString(this.squarePositions));

        while(!gsQueue.isEmpty()) {
            Pair<List<Integer>, List<List<Integer>>> currentTuple = gsQueue.remove();
            List<Integer> sp = currentTuple.getKey();
            List<List<Integer>> mp = currentTuple.getValue();

            if(isSolved(sp)) {
                System.out.println("Solution Found!!!");
                showSolution(mp);
                return;
            } else {
                List<List<Integer>> possibleMoves = new ArrayList<List<Integer>>();
                int emptyPos = 0;
                for(int i = 0; i < 16; i++) {
                    if(sp.get(i) == 0) {
                        emptyPos = i;
                        break;
                    }
                }
                if((emptyPos - 1) >= 0 && (emptyPos % 4 != 0)) {
                    List<Integer> moveLeft = new ArrayList<Integer>(16);
                    copyArrayList(sp, moveLeft);
                    moveLeft.set(emptyPos, moveLeft.get(emptyPos - 1));
                    moveLeft.set((emptyPos - 1), 0);
                    possibleMoves.add(moveLeft);
                }
                if((emptyPos - 4) >=0) {
                    List<Integer> moveUp = new ArrayList<Integer>(16);
                    copyArrayList(sp, moveUp);
                    moveUp.set(emptyPos, moveUp.get(emptyPos - 4));
                    moveUp.set((emptyPos - 4), 0);
                    possibleMoves.add(moveUp);
                }
                if((emptyPos + 1) < 16 && ((emptyPos + 1) % 4 != 0)) {
                    List<Integer> moveRight = new ArrayList<Integer>(16);
                    copyArrayList(sp, moveRight);
                    moveRight.set(emptyPos, moveRight.get(emptyPos + 1));
                    moveRight.set((emptyPos + 1), 0);
                    possibleMoves.add(moveRight);
                }
                if((emptyPos + 4) < 16) {
                    List<Integer> moveDown = new ArrayList<Integer>(16);
                    copyArrayList(sp, moveDown);
                    moveDown.set(emptyPos, moveDown.get(emptyPos + 4));
                    moveDown.set((emptyPos + 4), 0);
                    possibleMoves.add(moveDown);
                }
                for(int i = 0; i < possibleMoves.size(); i++) {
                    String t = squaresToString(possibleMoves.get(i));
                    if(!gsSet.contains(t)) {
                        gsSet.add(t);
                        List<List<Integer>> ms = new ArrayList<List<Integer>>();
                        for(int j = 0; j < mp.size(); j++) {
                            ms.add(mp.get(j));
                        }
                        ms.add(possibleMoves.get(i));
                        Pair<List<Integer>, List<List<Integer>>> p = new Pair<List<Integer>, List<List<Integer>>>(possibleMoves.get(i), ms);
                        gsQueue.add(p);
                    }
                }
            }
        }
        if(!gsQueue.isEmpty()) {
            System.out.println("This puzzle is unsolvable");
        }
    }

    private void showSolution(List<List<Integer>> mp) {
        gameScore = 0;
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(!mp.isEmpty()) {
                    List<Integer> step = mp.get(0);
                    mp.remove(0);
                    copyArrayList(step, squarePositions);
                    gameScore++;
                    updateWindow();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
            }
        }});
        t.start();

    }

}
