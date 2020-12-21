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
    private boolean isSynchronized = true;


    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            JsonParser parameters = loadParametersFromFile("src\\main\\resources\\input.json");
            parameters.checkIfJsonIsValid();
            this.engineWrapper = new EngineWrapper(parameters.getWidth(), parameters.getHeight(), parameters.getPlantEnergy(), parameters.getStartEnergy(), parameters.getJungleRatio(), parameters.getInitialNumberOfAnimals(), parameters.getMoveEnergy());
            this.rows = parameters.getWidth();
            this.columns = parameters.getHeight();
        } catch (IllegalArgumentException | FileNotFoundException ex) {
            System.out.println(ex);
        }

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
        MapBuilder mapBuilder = new MapBuilder(animalOnClick, mapStatus, engineWrapper.getEngine(), fileName);
        VBox map = mapBuilder.makeMap(pair.getKey());
        MapBuilder mapBuilder1 = new MapBuilder(animalOnClick1, mapStatus1, engineWrapper.getEngine1(), fileName1);
        VBox map1 = mapBuilder1.makeMap(pair1.getKey());
        TextField textField = createTextField(map);
        TextField textField1 = createTextField(map1);
        createButtonGetValueOfTextField(map, textField, mapStatus, animalOnClick);
        createButtonGetValueOfTextField(map1, textField1, mapStatus1, animalOnClick1);
        createButtonShowAnimalsWithDominantGenotype(pair.getKey(), engineWrapper.getEngine(), overlay, animalOnClick, mapStatus);
        createButtonShowAnimalsWithDominantGenotype(pair1.getKey(), engineWrapper.getEngine1(), overlay1, animalOnClick1, mapStatus1);
        HBox twoMaps = new HBox(map, map1);
        twoMaps.setSpacing(50);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(twoMaps);
        int width = 1100;
        int height = 650;
        primaryStage.setScene(new Scene(scroll, width, height));

        primaryStage.show();

    }


    private Pair<VBox, Timeline> createTimelineAndHBox(GridPane overlay, Engine engine, MapStatus mapStatus, AnimalOnClick animalOnClick, String fileName) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> run(overlay, engine, animalOnClick, mapStatus, fileName)));
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
        Button button = new Button("Zwierzęta z genotypem dominującym");
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

    private void run(GridPane overlay, Engine engine, AnimalOnClick trackedAnimal, MapStatus mapStatus, String fileName) {

        if(isSynchronized) {
            isSynchronized = false;
            engine.step();
            MapVisualizer mapVisualizer = new MapVisualizer(engine, squareSize, rows, columns);
            mapVisualizer.drawBackground(overlay);
            mapVisualizer.drawIMapElements(overlay, trackedAnimal, false, mapStatus);
            isSynchronized = true;
        }

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
