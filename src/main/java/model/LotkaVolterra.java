package model;

import java.util.*;

public class LotkaVolterra {
    private int size;
    private Animal[][] board;
    private List<Animal> animals;

    private Random random = new Random();

    private int initialWolfPercentage = 2;
    private int initialRabbitPercentage = 20;
    private int wolfDeathProbability = 2;
    private int wolfBirthProbability = 10;
    private int rabbitBirthProbability = 4;

    public LotkaVolterra(int size) {
        this.size = size;
    }

    public void refreshAnimals() {
        board = new Animal[size][size];
        animals = new LinkedList<>();

        int wolfAmount = (initialWolfPercentage * size * size) / 100;
        int rabbitAmount = (initialRabbitPercentage * size * size) / 100;

        for (int i = 0; i < wolfAmount; i++) {
            int x = random.nextInt(size), y = random.nextInt(size);
            if (board[x][y] == null) {
                board[x][y] = new Wolf(x, y, board, this);
                animals.add(board[x][y]);
            } else {
                i--;
            }
        }

        for (int i = 0; i < rabbitAmount; i++) {
            int x = random.nextInt(size), y = random.nextInt(size);
            if (board[x][y] == null) {
                board[x][y] = new Rabbit(x, y, board, this);
                animals.add(board[x][y]);
            } else {
                i--;
            }
        }

        Collections.shuffle(animals);
    }

    public void iterate() {
        List<Animal> animalsCopy = new ArrayList<>(animals);

        for (Animal animal : animalsCopy) {
            animal.iterate();
        }
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void removeAnimal(int x, int y) {
        if (board[x][y] instanceof Rabbit) {
            ((Rabbit) board[x][y]).kill();
        }
        animals.remove(board[x][y]);
        board[x][y] = null;
    }

    public void addAnimal(int x, int y, Animal animal) {
        animals.add(animal);
        board[x][y] = animal;
    }

    public void moveAnimal(int fromX, int fromY, int toX, int toY) {
        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;

        board[toX][toY].setX(toX);
        board[toX][toY].setY(toY);
    }

    public Animal[][] getBoard() {
        return board;
    }

    public int getRabbitsNumber() {
        return (int) animals.stream()
                .filter(animal -> animal instanceof Rabbit)
                .count();
    }

    public int getWolvesNumber() {
        return (int) animals.stream()
                .filter(animal -> animal instanceof Wolf)
                .count();
    }

    public int getInitialRabbitPercentage() {
        return initialRabbitPercentage;
    }

    public int getInitialWolfPercentage() {
        return initialWolfPercentage;
    }

    public int getRabbitBirthProbability() {
        return rabbitBirthProbability;
    }

    public int getWolfBirthProbability() {
        return wolfBirthProbability;
    }

    public int getWolfDeathProbability() {
        return wolfDeathProbability;
    }

    public void setInitialRabbitPercentage(int initialRabbitPercentage) {
        this.initialRabbitPercentage = initialRabbitPercentage;
    }

    public void setInitialWolfPercentage(int initialWolfPercentage) {
        this.initialWolfPercentage = initialWolfPercentage;
    }

    public void setRabbitBirthProbability(int rabbitBirthProbability) {
        this.rabbitBirthProbability = rabbitBirthProbability;
    }

    public void setWolfBirthProbability(int wolfBirthProbability) {
        this.wolfBirthProbability = wolfBirthProbability;
    }

    public void setWolfDeathProbability(int wolfDeathProbability) {
        this.wolfDeathProbability = wolfDeathProbability;
    }
}
