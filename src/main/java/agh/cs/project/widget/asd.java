package agh.cs.project.widget;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.engine.Engine;
import agh.cs.project.engine.EngineWrapper;
import agh.cs.project.engine.JsonParser;
import agh.cs.project.map.MapStatus;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Consumer;

import static agh.cs.project.engine.JsonParser.loadParametersFromFile;

public class asd extends Application {
    private final int width = 1100;
    private final int height = 650;
    private int rows;
    private int columns;
    private int squareSize;
    private volatile boolean isSynchronized = true;
    private EngineWrapper engineWrapper;
    private static boolean flag = false;
    private static boolean makeStatisticsToTxt = false;
    private static AnimalOnClick animalOnClick = new AnimalOnClick();


    //private GraphicsContext graphicsContext;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            JsonParser parameters = loadParametersFromFile();
            this.engineWrapper = new EngineWrapper(parameters.getWidth(), parameters.getHeight(), parameters.getPlantEnergy(), parameters.getStartEnergy(), parameters.getJungleRatio(), parameters.getInitialNumberOfAnimals(), parameters.getMoveEnergy());
        } catch (IllegalArgumentException | FileNotFoundException ex) {
            System.out.println(ex);
        }
        JsonParser optionsParser = loadParametersFromFile();
        this.rows = optionsParser.getWidth();
        this.columns = optionsParser.getHeight();
        //this.squareSize = width / rows;
        this.squareSize = 15;


        primaryStage.setTitle("World Simulation");
        //Group root = new Group();
        //Canvas canvas = new Canvas(width, height);
        //root.getChildren().add(canvas);
        //Scene scene = new Scene(root);
        //primaryStage.setScene(scene);
        //graphicsContext = canvas.getGraphicsContext2D();
        GridPane overlay = new GridPane();
        //MapVisualizer mapVisualizer = new MapVisualizer(engineWrapper, squareSize, rows, columns);
        GridPane overlay1 = new GridPane();
        //MapVisualizer mapVisualizer1 = new MapVisualizer(engineWrapper, squareSize, rows, columns);
        MapStatus mapStatus = new MapStatus(engineWrapper.getEngine());
        MapStatus mapStatus1 = new MapStatus(engineWrapper.getEngine1());
        Pair<VBox, Timeline> pair = createTimelineAndHBox(overlay, engineWrapper.getEngine(), mapStatus);
        Pair<VBox, Timeline> pair1 = createTimelineAndHBox(overlay1, engineWrapper.getEngine1(), mapStatus1);
        pair.getValue().play();
        pair1.getValue().play();
        createStatisticButtonAverageEnergy(pair.getKey(), mapStatus);
        createStatisticButtonAverageEnergy(pair1.getKey(), mapStatus1);
        createStatisticButtonNumberOfAliveAnimals(pair.getKey(), mapStatus);
        createStatisticButtonNumberOfAliveAnimals(pair1.getKey(), mapStatus1);
        createStatisticButtonNumberOfGrass(pair.getKey(), mapStatus);
        createStatisticButtonNumberOfGrass(pair1.getKey(), mapStatus1);
        createStatisticButtonAverageLifespan(pair.getKey(), mapStatus);
        createStatisticButtonAverageLifespan(pair1.getKey(), mapStatus1);
        createStatisticButtonAverageNumberOfBabies(pair.getKey(), mapStatus);
        createStatisticButtonAverageNumberOfBabies(pair1.getKey(), mapStatus1);
        createStatisticButtonDominantGenotype(pair.getKey(), mapStatus);
        createStatisticButtonDominantGenotype(pair1.getKey(), mapStatus1);
        TextField textField = createTextField(pair.getKey());
        TextField textField1 = createTextField(pair1.getKey());
        createButtonGetValueOfTextField(pair.getKey(), textField, mapStatus);
        createButtonGetValueOfTextField(pair1.getKey(), textField1, mapStatus1);
        createButtonShowAnimalsWithDominantGenotype(pair.getKey());
        createButtonShowAnimalsWithDominantGenotype(pair1.getKey());


        HBox twoMaps = new HBox(pair.getKey(), pair1.getKey());
        twoMaps.setSpacing(50);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(twoMaps);

        primaryStage.setScene(new Scene(scroll, width, height));
        //primaryStage.setScene(new Scene(twoMaps, width, height));

        primaryStage.show();

    }


    private Pair<VBox, Timeline> createTimelineAndHBox(GridPane overlay, Engine engine, MapStatus mapStatus) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), e -> run(overlay, engine, animalOnClick, mapStatus)));
        timeline.setCycleCount(Animation.INDEFINITE);
        Button startButton = new Button("start button");
        startButton.setOnAction(e -> timeline.play());
        Button stopButton = new Button("stop button");
        stopButton.setOnAction(e -> timeline.stop());
        VBox buttons = new VBox(startButton, stopButton, overlay);
        return new Pair<>(buttons, timeline);
    }


    private TextField createTextField(VBox mapbox) {
        TextField textField = new TextField("100");
        mapbox.getChildren().add(textField);
        return textField;
    }

    private void createButtonShowAnimalsWithDominantGenotype(VBox mapbox) {
        Button button = new Button("Zwierzeta z genotypem dominujacym");
        button.setOnAction(e -> {
            flag = !flag;
        });
        mapbox.getChildren().add(button);
    }

    private void createButtonGetValueOfTextField(VBox mapbox, TextField textField, MapStatus mapStatus) {
        Button button = new Button("Wartość n");
        button.setOnAction(e -> {
            animalOnClick.setTrackDuring(Integer.parseInt(textField.getText()));
            makeStatisticsToTxt = true;
            mapStatus.setMakeTxtSummaryAtDay(mapStatus.getEngine().getActualDate() + Integer.parseInt(textField.getText()));
        });
        mapbox.getChildren().add(button);
    }


    private void createStatisticButtonDominantGenotype(VBox mapBox, MapStatus mapStatus) {
        Button button = new Button("Dominujący genotyp");
        button.setOnAction(e -> {
            if (mapStatus.dominantGenotype() != null) {
                System.out.println(mapStatus.dominantGenotype(false).getKey());
            } else {
                System.out.println("Wszystkie zwierzeta sa martwe!");
            }
        });
        mapBox.getChildren().add(button);
    }

    private void createStatisticButtonNumberOfAliveAnimals(VBox mapBox, MapStatus mapStatus) {
        Button button = new Button("Liczba zwierząt");
        button.setOnAction(e -> System.out.println(mapStatus.numberOfAliveAnimals(false)));
        mapBox.getChildren().add(button);
    }

    private void createStatisticButtonNumberOfGrass(VBox mapBox, MapStatus mapStatus) {
        Button button = new Button("Liczba traw");
        button.setOnAction(e -> System.out.println(mapStatus.numberOfGrass(false)));
        mapBox.getChildren().add(button);
    }

    private void createStatisticButtonAverageEnergy(VBox mapBox, MapStatus mapStatus) {
        Button button = new Button("Średnia enegria");
        button.setOnAction(e -> System.out.println(mapStatus.averageEnergy(false)));
        mapBox.getChildren().add(button);
    }

    private void createStatisticButtonAverageLifespan(VBox mapBox, MapStatus mapStatus) {
        Button button = new Button("Średnia długość życia");
        button.setOnAction(e -> System.out.println(mapStatus.averageLifespan(false)));
        mapBox.getChildren().add(button);
    }

    private void createStatisticButtonAverageNumberOfBabies(VBox mapBox, MapStatus mapStatus) {
        Button button = new Button("Średnia liczba dzieci");
        button.setOnAction(e -> System.out.println(mapStatus.averageNumberOfBabies(false)));
        mapBox.getChildren().add(button);
    }

    private void run(GridPane overlay, Engine engine, AnimalOnClick trackedAnimal, MapStatus mapStatus) {
        MapVisualizer mapVisualizer = new MapVisualizer(engine, squareSize, rows, columns);
        mapVisualizer.drawBackground(overlay);
        mapVisualizer.drawIMapElements(overlay, trackedAnimal, flag, mapStatus);
        if(makeStatisticsToTxt){
            mapStatus.makeFullLoopOfStatistics();
            if(mapStatus.getEngine().getActualDate() == mapStatus.getMakeTxtSummaryAtDay()){
                mapStatus.makeTxt("test_stats.txt");
                makeStatisticsToTxt = false;
            }
        }
        //if (isSynchronized) {
        // isSynchronized = false;
        // drawBackground(overlay);
        // drawIMapElements(overlay);
        engine.step();
        //return overlay;
        // isSynchronized = true;
        //}
    }


    public static void main(String[] args) {
        launch(args);
    }
}
