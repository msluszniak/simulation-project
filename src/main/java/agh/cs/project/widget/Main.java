package agh.cs.project.widget;

import agh.cs.project.engine.Engine;
import agh.cs.project.engine.EngineWrapper;
import agh.cs.project.engine.JsonParser;
import agh.cs.project.map.MapStatus;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.FileNotFoundException;

import static agh.cs.project.engine.JsonParser.loadParametersFromFile;

public class Main extends Application {
    private int rows;
    private int columns;
    private int squareSize;
    private EngineWrapper engineWrapper;
    private static boolean makeStatisticsToTxt = false;
    private static final AnimalOnClick animalOnClick = new AnimalOnClick();
    private static final AnimalOnClick animalOnClick1 = new AnimalOnClick();


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
        this.squareSize = 15;


        primaryStage.setTitle("World Simulation");
        GridPane overlay = new GridPane();
        GridPane overlay1 = new GridPane();
        MapStatus mapStatus = new MapStatus(engineWrapper.getEngine());
        MapStatus mapStatus1 = new MapStatus(engineWrapper.getEngine1());
        String fileName = "stats1.txt";
        String fileName1 = "stats2.txt";
        Pair<VBox, Timeline> pair = createTimelineAndHBox(overlay, engineWrapper.getEngine(), mapStatus, animalOnClick, fileName);
        Pair<VBox, Timeline> pair1 = createTimelineAndHBox(overlay1, engineWrapper.getEngine1(), mapStatus1, animalOnClick1, fileName1);
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
        createButtonGetValueOfTextField(pair.getKey(), textField, mapStatus, animalOnClick);
        createButtonGetValueOfTextField(pair1.getKey(), textField1, mapStatus1, animalOnClick1);
        createButtonShowAnimalsWithDominantGenotype(pair.getKey(), engineWrapper.getEngine(), overlay, animalOnClick, mapStatus);
        createButtonShowAnimalsWithDominantGenotype(pair1.getKey(), engineWrapper.getEngine1(), overlay1, animalOnClick1, mapStatus1);

        HBox twoMaps = new HBox(pair.getKey(), pair1.getKey());
        twoMaps.setSpacing(50);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(twoMaps);

        int width = 1100;
        int height = 650;
        primaryStage.setScene(new Scene(scroll, width, height));

        primaryStage.show();

    }


    private Pair<VBox, Timeline> createTimelineAndHBox(GridPane overlay, Engine engine, MapStatus mapStatus, AnimalOnClick animalOnClick, String fileName) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(125), e -> run(overlay, engine, animalOnClick, mapStatus, fileName)));
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

    private void createButtonShowAnimalsWithDominantGenotype(VBox mapbox, Engine engine, GridPane overlay, AnimalOnClick trackedAnimal, MapStatus mapStatus) {
        Button button = new Button("Zwierzeta z genotypem dominujacym");
        button.setOnAction(e -> {
            MapVisualizer mapVisualizer = new MapVisualizer(engine, squareSize, rows, columns);
            mapVisualizer.drawBackground(overlay);
            mapVisualizer.drawIMapElements(overlay, trackedAnimal, true, mapStatus);
        });
        mapbox.getChildren().add(button);
    }


    private void createButtonGetValueOfTextField(VBox mapbox, TextField textField, MapStatus mapStatus, AnimalOnClick animalOnClick) {
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

    private void run(GridPane overlay, Engine engine, AnimalOnClick trackedAnimal, MapStatus mapStatus, String fileName) {
        engine.step();
        MapVisualizer mapVisualizer = new MapVisualizer(engine, squareSize, rows, columns);
        mapVisualizer.drawBackground(overlay);
        mapVisualizer.drawIMapElements(overlay, trackedAnimal, false, mapStatus);
        if (makeStatisticsToTxt) {
            mapStatus.makeFullLoopOfStatistics();
            if (mapStatus.getEngine().getActualDate() == mapStatus.getMakeTxtSummaryAtDay()) {
                mapStatus.makeTxt(fileName);
                makeStatisticsToTxt = false;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
