package agh.cs.project.widget;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.engine.Engine;
import agh.cs.project.engine.EngineWrapper;
import agh.cs.project.engine.JsonParser;
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

import static agh.cs.project.engine.JsonParser.loadParametersFromFile;

public class asd extends Application {
    private final int width = 1100;
    private final int height = 650;
    private int rows;
    private int columns;
    private int squareSize;
    private volatile boolean isSynchronized = true;
    private EngineWrapper engineWrapper;
    private static boolean flag = true;

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
        Pair<HBox, Timeline> pair = createTimelineAndHBox(overlay, engineWrapper.getEngine());
        Pair<HBox, Timeline> pair1 = createTimelineAndHBox(overlay1, engineWrapper.getEngine1());
        pair.getValue().play();
        pair1.getValue().play();

        HBox twoMaps = new HBox(pair.getKey(), pair1.getKey());

        primaryStage.setScene(new Scene(twoMaps, width, height));

        primaryStage.show();

    }

    private Pair<HBox, Timeline> createTimelineAndHBox(GridPane overlay, Engine engine){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), e -> run(overlay, engine)));
        timeline.setCycleCount(Animation.INDEFINITE);
        Button startButton = new Button("start button");
        startButton.setOnAction(e -> timeline.play());
        Button stopButton = new Button("stop button");
        stopButton.setOnAction(e -> timeline.stop());
        HBox buttons = new HBox(startButton, stopButton, overlay);
        return new Pair<>(buttons, timeline);
    }



    private void run(GridPane overlay, Engine engine) {
        MapVisualizer mapVisualizer = new MapVisualizer(engine, squareSize, rows, columns);
        mapVisualizer.drawBackground(overlay);
        mapVisualizer.drawIMapElements(overlay);

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
