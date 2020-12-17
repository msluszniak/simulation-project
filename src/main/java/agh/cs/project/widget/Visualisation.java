package agh.cs.project.widget;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.engine.Engine;
import agh.cs.project.engine.EngineWrapper;
import agh.cs.project.engine.JsonParser;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static agh.cs.project.engine.JsonParser.loadParametersFromFile;

public class Visualisation extends Application {
    private final int width = 600;
    private final int height = 600;
    private int rows;
    private int columns;
    private int squareSize;
    private volatile boolean isSynchronized = true;
    private EngineWrapper engineWrapper;
    private JsonParser optionsParser;

    private GraphicsContext graphicsContext;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            JsonParser parameters = loadParametersFromFile();
            this.engineWrapper = new EngineWrapper(parameters.getWidth(), parameters.getHeight(),parameters.getPlantEnergy(), parameters.getStartEnergy(), parameters.getJungleRatio(), parameters.getInitialNumberOfAnimals(), parameters.getMoveEnergy());
        }catch(IllegalArgumentException | FileNotFoundException ex){
            System.out.println(ex);
        }
        this.optionsParser = loadParametersFromFile();
        this.rows = optionsParser.getWidth();
        this.columns = optionsParser.getHeight();
        this.squareSize = width/rows;

        primaryStage.setTitle("Darwin World");
        Group root = new Group();
        Canvas canvas = new Canvas(width,height);
        root.getChildren().add(canvas);
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        graphicsContext = canvas.getGraphicsContext2D();
        primaryStage.show();



        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), e -> run(graphicsContext)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext graphicsContext){
        if(isSynchronized) {
            isSynchronized = false;
            drawBackground(graphicsContext);
            drawIMapElements(graphicsContext);
            engineWrapper.run();
            isSynchronized = true;
        }
    }

    private void drawIMapElements(GraphicsContext graphicsContext) {
        List<Animal> animalsToDraw = engineWrapper.getEngine().getMap().getListOfAnimals();
        List<Grass> grassToDraw = engineWrapper.getEngine().getMap().getListOfGrasses();

        for(Grass g : grassToDraw){
            graphicsContext.setFill(Color.web("#A52A2A"));
            int x = g.getPosition().x;
            int y = g.getPosition().y;
            graphicsContext.fillRect(x*squareSize,y*squareSize,squareSize,squareSize);
        }
        for(Animal a : animalsToDraw){
            graphicsContext.setFill(Color.web("4674E9"));
            int x = a.getPosition().x;
            int y = a.getPosition().y;
            graphicsContext.fillRect(x*squareSize,y*squareSize,squareSize,squareSize);
        }


    }

    private void drawBackground(GraphicsContext graphicsContext){
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                if(engineWrapper.getEngine().getMap().isInJungle(new Vector2d(i,j))){
                    graphicsContext.setFill(Color.web("#008000"));
                }
                else if((i+j)%2 == 0){
                    graphicsContext.setFill(Color.web("AAD751"));
                }
                else{
                    graphicsContext.setFill(Color.web("A2D149"));
                }
                graphicsContext.fillRect(i*squareSize,j*squareSize,squareSize,squareSize);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}