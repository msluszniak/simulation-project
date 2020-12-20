package agh.cs.project.widget;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.engine.Engine;
import agh.cs.project.engine.EngineWrapper;
import agh.cs.project.map.MapStatus;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class MapVisualizer {
    private final Engine engine;
    private int squareSize;
    private int rows;
    private int columns;

    public MapVisualizer(Engine engine, int squareSize, int rows, int columns){
        this.engine = engine;
        this.squareSize = squareSize;
        this.rows = rows;
        this.columns = columns;
    }

    public void drawIMapElements(GridPane overlay, AnimalOnClick trackedAnimal, boolean flag, MapStatus mapStatus) {
        List<Animal> animalsToDraw = engine.getMap().getListOfAnimals();
        List<Grass> grassToDraw = engine.getMap().getListOfGrasses();
        if(trackedAnimal.getTrackedAnimal() != null)
            trackedAnimal.update();
        //System.out.println(overlay.getChildren().size());
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
            int energyStatus = (int)((a.getEnergy()/(float)engine.getMap().getInitialEnergy())*255);
            Color color;
            if(energyStatus > 255){color = Color.rgb(255,255,255);}
            else if(energyStatus < 0){color = Color.rgb(0,0,0);}
            else color = Color.rgb(energyStatus,energyStatus,energyStatus);
            if(flag){
                if(mapStatus.dominantGenotype() != null){
                    if(a.getGenotype().equals(mapStatus.dominantGenotype().getKey())){
                        color = Color.rgb(255,0,0);
                    }
                }
            }
            //graphicsContext.setFill(Color.rgb(color, color, color));
            int x = a.getPosition().x;
            int y = a.getPosition().y;
            //graphicsContext.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
            Rectangle rectangle = new Rectangle(x * squareSize, y * squareSize, squareSize, squareSize);
            rectangle.setFill(color);
            rectangle.setOnMouseClicked((MouseEvent e) -> {
                System.out.println(a.getGenotype());
                trackedAnimal.changeAnimal(a);
                if(trackedAnimal.getAnimal() != null && trackedAnimal.getTrackDuring() != 0){
                    trackedAnimal.setWhenCaught(engine.getActualDate());
                }
                //timeline.stop();// change functionality
            });
            overlay.add(rectangle, x * squareSize, y * squareSize);
        }
        if(engine.getActualDate() == trackedAnimal.getWhenCaught()+trackedAnimal.getTrackDuring() &&
                trackedAnimal.getTrackedAnimal() != null) {
            System.out.println(trackedAnimal.getNumberOfChildren());
            System.out.println(trackedAnimal.getNumberOfDescendants());
            System.out.println(trackedAnimal.getDateOfDeath());
        }
    }

    public void drawBackground(GridPane overlay) {
        overlay.getChildren().clear();
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                if (engine.getMap().isInJungle(new Vector2d(i, j))) {
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



}
