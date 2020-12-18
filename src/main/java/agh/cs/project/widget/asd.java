package agh.cs.project.widget;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
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

import java.io.FileNotFoundException;
import java.util.List;

import static agh.cs.project.engine.JsonParser.loadParametersFromFile;

public class asd extends Application {
    private final int width = 600;
    private final int height = 600;
    private int rows;
    private int columns;
    private int squareSize;
    private volatile boolean isSynchronized = true;
    private EngineWrapper engineWrapper;
    private static boolean flag = true;

    private GraphicsContext graphicsContext;

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
        this.squareSize = width / rows;


        primaryStage.setTitle("World Simulation");
        //Group root = new Group();
        //Canvas canvas = new Canvas(width, height);
        //root.getChildren().add(canvas);
        //Scene scene = new Scene(root);
        //primaryStage.setScene(scene);
        //graphicsContext = canvas.getGraphicsContext2D();
        GridPane overlay = new GridPane();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> run(overlay)));
        timeline.setCycleCount(Animation.INDEFINITE);
        Button startButton = new Button("start_button");
        startButton.setOnAction(e -> timeline.play());
        Button stopButton = new Button("stop_button");
        stopButton.setOnAction(e -> timeline.stop());
        HBox buttons = new HBox(startButton, stopButton, overlay);

        primaryStage.setScene(new Scene(buttons, width, height));

        primaryStage.show();

    }

    private void run(GridPane overlay) {
        if (isSynchronized) {
            isSynchronized = false;
            drawBackground(overlay);
            drawIMapElements(overlay);
            engineWrapper.run();
            isSynchronized = true;
        }
    }

    private void drawIMapElements(GridPane overlay) {
        List<Animal> animalsToDraw = engineWrapper.getEngine().getMap().getListOfAnimals();
        List<Grass> grassToDraw = engineWrapper.getEngine().getMap().getListOfGrasses();

        for (Grass g : grassToDraw) {
            //graphicsContext.setFill(Color.rgb(94, 116, 57));
            int x = g.getPosition().x;
            int y = g.getPosition().y;
            //graphicsContext.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
            Rectangle rectangle = new Rectangle(x * squareSize, y * squareSize, squareSize, squareSize);
            rectangle.setFill(Color.rgb(94, 116, 57));
            rectangle.setOnMouseClicked((MouseEvent e) -> {
                System.out.println("Clicked!"); // change functionality
            });
            overlay.add(rectangle ,x * squareSize, y * squareSize);
        }
        for (Animal a : animalsToDraw) {
            int color = (int)((a.getEnergy()/(float)engineWrapper.getEngine().getMap().getInitialEnergy())*255);
            if(color > 255) color = 255;
            if(color < 0) color = 0;
            //graphicsContext.setFill(Color.rgb(color, color, color));
            int x = a.getPosition().x;
            int y = a.getPosition().y;
            //graphicsContext.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
            Rectangle rectangle = new Rectangle(x * squareSize, y * squareSize, squareSize, squareSize);
            rectangle.setFill(Color.rgb(color, color, color));
            rectangle.setOnMouseClicked((MouseEvent e) -> {
                System.out.println(a.getGenotype());
                //timeline.stop();// change functionality
            });
            overlay.add(rectangle, x * squareSize, y * squareSize);
        }
    }

    private void drawBackground(GridPane overlay) {
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                if (engineWrapper.getEngine().getMap().isInJungle(new Vector2d(i, j))) {
                    //graphicsContext.setFill(Color.web("#008000"));
                    Rectangle rectangle = new Rectangle(i * squareSize, j * squareSize, squareSize, squareSize);
                    rectangle.setFill(Color.web("#008000"));
                    overlay.add(rectangle, i * squareSize, j * squareSize);
                } else if ((i + j) % 2 == 0) {
                    //graphicsContext.setFill(Color.web("AAD751"));
                    //graphicsContext.setFill(Color.web("AAD751"));
                    Rectangle rectangle = new Rectangle(i * squareSize, j * squareSize, squareSize, squareSize);
                    rectangle.setFill(Color.web("AAD751"));
                    overlay.add(rectangle, i * squareSize, j * squareSize);
                } else {
                    //graphicsContext.setFill(Color.web("A2D149"));
                    Rectangle rectangle = new Rectangle(i * squareSize, j * squareSize, squareSize, squareSize);
                    rectangle.setFill(Color.web("A2D149"));
                    overlay.add(rectangle, i * squareSize, j * squareSize);

                }
                //graphicsContext.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
