package model;

import java.util.Random;

public class Rabbit extends Animal {
    private LotkaVolterra lv;
    private Random random = new Random();
    private boolean alive = true;

    public Rabbit(int x, int y, Animal[][] board, LotkaVolterra lv) {
        super(x, y, board);
        this.lv = lv;
    }

    @Override
    public void iterate() {
        if (alive) {
            for (Direction d : getDirections()) {
                int x = d.getX(getX(), getBoard().length), y = d.getY(getY(),  getBoard().length);

                if (getBoard()[x][y] == null) {
                    if (random.nextInt(100) < lv.getRabbitBirthProbability()) {
                        lv.addAnimal(x, y, new Rabbit(x, y, getBoard(), lv));
                    } else {
                        lv.moveAnimal(getX(), getY(), x, y);
                    }

                    break;
                }
            }
        }
    }

    public void kill() {
        alive = false;
    }
}
