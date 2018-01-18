package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Animal {
    private int x;
    private int y;
    private Animal[][] board;
    private List<Direction> directions;


    public Animal(int x, int y, Animal[][] board) {
        this.x = x;
        this.y = y;
        this.board = board;
        directions = Arrays.asList(Direction.values());
    }


    public abstract void iterate();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Animal[][] getBoard() {
        return board;
    }

    public List<Direction> getDirections() {
        Collections.shuffle(directions);
        return directions;
    }
}
