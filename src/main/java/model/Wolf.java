package model;


import java.util.Random;

public class Wolf extends Animal {
    private LotkaVolterra lv;
    private Random random = new Random();

    public Wolf(int x, int y, Animal[][] board, LotkaVolterra lv) {
        super(x, y, board);
        this.lv = lv;
    }

    @Override
    public void iterate() {
        boolean eaten = false;
        for (Direction d : getDirections()) {
            int x = d.getX(getX(), getBoard().length), y = d.getY(getY(),  getBoard().length);

            if (getBoard()[x][y] instanceof Rabbit) {
                lv.removeAnimal(x, y);
                eaten = true;

                if (random.nextInt(100) < lv.getWolfBirthProbability()) {
                    lv.addAnimal(x, y, new Wolf(x, y, getBoard(), lv));
                }

                break;
            }
        }

        if (!eaten) {
            if (random.nextInt(100) < lv.getWolfDeathProbability()) {
                lv.removeAnimal(getX(), getY());
            } else {
                for (Direction d : getDirections()) {
                    int x = d.getX(getX(), getBoard().length), y = d.getY(getY(), getBoard().length);

                    if (getBoard()[x][y] == null) {
                        lv.moveAnimal(getX(), getY(), x, y);
                        break;
                    }
                }
            }
        }
    }
}
