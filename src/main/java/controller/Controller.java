package controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Animal;
import model.LotkaVolterra;
import model.Wolf;

public class Controller extends Application {
    private final int size = 100;
    private final int maxAnimals = size * size;
    private final int pixelsPerAnimal = 5;
    private final Color black = new Color(0.0, 0.0, 0.0, 1.0);
    private final Color white = new Color(1.0, 1.0, 1.0, 1.0);
    private final Color wolfColor = new Color(1.0, 0.0, 0.0, 1.0);
    private final Color rabbitColor = new Color(0.0, 1.0, 0.0, 1.0);

    private Stage primaryStage;
    private Parent splitPane;
    private AnimationTimer animationTimer = null;

    private LotkaVolterra lotkaVolterra = new LotkaVolterra(size);
    private Animal[][] board;

    @FXML private Button startButton;
    @FXML private Canvas boardCanvas;
    @FXML private Canvas graphCanvas;
    @FXML private TextField initialWolfInput;
    @FXML private TextField initialRabbitInput;
    @FXML private TextField wolfDeathInput;
    @FXML private TextField wolfBirthInput;
    @FXML private TextField rabbitBirthInput;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/view.fxml"));
        splitPane = loader.load();

        Scene scene = new Scene(splitPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void onStart() {
        int initialWolf = Integer.valueOf(initialWolfInput.getCharacters().toString());
        int initialRabbit = Integer.valueOf(initialRabbitInput.getCharacters().toString());
        int wolfDeath = Integer.valueOf(wolfDeathInput.getCharacters().toString());
        int wolfBirth = Integer.valueOf(wolfBirthInput.getCharacters().toString());
        int rabbitBirth = Integer.valueOf(rabbitBirthInput.getCharacters().toString());

        lotkaVolterra.setInitialWolfPercentage(initialWolf);
        lotkaVolterra.setInitialRabbitPercentage(initialRabbit);
        lotkaVolterra.setWolfDeathProbability(wolfDeath);
        lotkaVolterra.setWolfBirthProbability(wolfBirth);
        lotkaVolterra.setRabbitBirthProbability(rabbitBirth);

        lotkaVolterra.refreshAnimals();
        board = lotkaVolterra.getBoard();

        if (animationTimer != null)
            animationTimer.stop();

        animationTimer = new AnimationTimer() {
            private PixelWriter boardPixelWriter = boardCanvas.getGraphicsContext2D().getPixelWriter();
            private PixelWriter graphPixelWriter = graphCanvas.getGraphicsContext2D().getPixelWriter();
            private Color currentColor;

            private long before = 0;

            @Override
            public void handle(long now) {
                if (now - before >= 10_000_000) {
                    before = now;

                    drawBoard();
                    drawGraph();

                    lotkaVolterra.iterate();
                }
            }

            private void drawBoard() {
                for (int x = 0; x < board.length; ++x) {
                    for (int y = 0; y < board[x].length; ++y) {
                        Animal animal = board[x][y];

                        if (animal == null) {
                            currentColor = white;
                        } else if (animal instanceof Wolf) {
                            currentColor = wolfColor;
                        } else {
                            currentColor = rabbitColor;
                        }

                        int firstX = x * pixelsPerAnimal;
                        int firstY = y * pixelsPerAnimal;
                        int lastX = firstX + pixelsPerAnimal;
                        int lastY = firstY + pixelsPerAnimal;

                        for (int i = firstX; i < lastX; i++) {
                            for (int j = firstY; j < lastY; j++) {
                                boardPixelWriter.setColor(i, j, currentColor);
                            }
                        }
                    }
                }
            }

            private void drawGraph() {
                PixelReader pixelReader = graphCanvas.snapshot(null, null).getPixelReader();
                for (int x = 1; x < 500; x++) {
                    for (int y = 0; y < 100; y++) {
                        Color color = pixelReader.getColor(x, y);
                        graphPixelWriter.setColor(x - 1, y, color);
                    }
                }

                for (int y = 0; y < 100; y++) {
                    graphPixelWriter.setColor(499, y, white);
                }

                int wolvesPercentage = 100 - (lotkaVolterra.getWolvesNumber() * 100) / maxAnimals;
                int rabbitsPercentage = 100 - (lotkaVolterra.getRabbitsNumber() * 100) / maxAnimals;
                graphPixelWriter.setColor(499, wolvesPercentage, wolfColor);
                graphPixelWriter.setColor(499, rabbitsPercentage, rabbitColor);
            }
        };

        animationTimer.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
