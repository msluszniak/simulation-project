package agh.cs.project.widget;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.engine.Engine;
import agh.cs.project.map.MapStatus;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class MapVisualizer {
    private final Engine engine;
    private final int squareSize;
    private final int rows;
    private final int columns;

    public MapVisualizer(Engine engine, int squareSize, int rows, int columns) {
        this.engine = engine;
        this.squareSize = squareSize;
        this.rows = rows;
        this.columns = columns;
    }

    public void drawIMapElements(GridPane overlay, AnimalOnClick trackedAnimal, boolean flag, MapStatus mapStatus) {
        List<Animal> animalsToDraw = engine.getMap().getListOfStrongestAnimals();
        List<Grass> grassToDraw = engine.getMap().getListOfGrasses();
        if (trackedAnimal.getTrackedAnimal() != null) {
            trackedAnimal.update();
        }
        for (Grass g : grassToDraw) {
            int x = g.getPosition().x;
            int y = g.getPosition().y;
            Rectangle rectangle = new Rectangle(x * squareSize, y * squareSize, squareSize, squareSize);
            rectangle.setFill(Color.rgb(94, 116, 57));
            rectangle.setOnMouseClicked((MouseEvent e) -> {
                System.out.println("Just grass, nothing special");
            });
            overlay.add(rectangle, x * squareSize, y * squareSize);
        }
        for (Animal a : animalsToDraw) {
            int energyStatus = (int) ((a.getEnergy() / (float) engine.getMap().getInitialEnergy()) * 255);
            Color color;
            if (energyStatus > 255) {
                color = Color.rgb(255, 255, 255);
            } else if (energyStatus < 0) {
                color = Color.rgb(0, 0, 0);
            } else color = Color.rgb(energyStatus, energyStatus, energyStatus);
            if (flag) {
                if (mapStatus.dominantGenotype() != null) {
                    if (a.getGenotype().equals(mapStatus.dominantGenotype().getKey())) {
                        color = Color.rgb(255, 0, 0);
                    }
                }
            }
            if (trackedAnimal.getTrackedAnimal() != null) {
                if (trackedAnimal.getTrackedAnimal().equals(a)) {
                    color = Color.rgb(0, 0, 255);
                }
            }
            int x = a.getPosition().x;
            int y = a.getPosition().y;
            Rectangle rectangle = new Rectangle(x * squareSize, y * squareSize, squareSize, squareSize);
            rectangle.setFill(color);
            rectangle.setOnMouseClicked((MouseEvent e) -> {
                System.out.println(a.getGenotype());
                trackedAnimal.changeAnimal(a);
                if (trackedAnimal.getAnimal() != null && trackedAnimal.getTrackDuring() != 0) {
                    trackedAnimal.setWhenCaught(engine.getActualDate());
                }
            });
            overlay.add(rectangle, x * squareSize, y * squareSize);
        }
        if (engine.getActualDate() == trackedAnimal.getWhenCaught() + trackedAnimal.getTrackDuring() &&
                trackedAnimal.getTrackedAnimal() != null) {
            System.out.println(trackedAnimal.getNumberOfChildren());
            System.out.println(trackedAnimal.getNumberOfDescendants());
            System.out.println(trackedAnimal.getDateOfDeath());
            trackedAnimal.stopObserving(trackedAnimal.getAnimal());
        }
    }

    public void drawBackground(GridPane overlay) {
        overlay.getChildren().clear();
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                if (engine.getMap().isInJungle(new Vector2d(i, j))) {
                    Rectangle rectangle = new Rectangle(i * squareSize, j * squareSize, squareSize, squareSize);
                    rectangle.setFill(Color.web("#008000"));
                    overlay.add(rectangle, i * squareSize, j * squareSize);
                } else if ((i + j) % 2 == 0) {
                    Rectangle rectangle = new Rectangle(i * squareSize, j * squareSize, squareSize, squareSize);
                    rectangle.setFill(Color.web("AAD751"));
                    overlay.add(rectangle, i * squareSize, j * squareSize);
                } else {
                    Rectangle rectangle = new Rectangle(i * squareSize, j * squareSize, squareSize, squareSize);
                    rectangle.setFill(Color.web("A2D149"));
                    overlay.add(rectangle, i * squareSize, j * squareSize);

                }
            }
        }
    }


}
