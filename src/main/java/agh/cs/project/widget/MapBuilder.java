package agh.cs.project.widget;

import agh.cs.project.engine.Engine;
import agh.cs.project.map.MapStatus;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MapBuilder {
    private final MapStatus mapStatus;
    Engine engine;
    String fileName;
    AnimalOnClick animalOnClick;


    public MapBuilder(AnimalOnClick animalOnClick, MapStatus mapStatus, Engine engine, String fileName){
        this.animalOnClick = animalOnClick;
        this.engine = engine;
        this.fileName = fileName;
        this.mapStatus = mapStatus;
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

    public VBox makeMap(VBox map){
        createStatisticButtonAverageEnergy(map, mapStatus);
        createStatisticButtonNumberOfAliveAnimals(map, mapStatus);
        createStatisticButtonNumberOfGrass(map, mapStatus);
        createStatisticButtonAverageLifespan(map, mapStatus);
        createStatisticButtonAverageNumberOfBabies(map, mapStatus);
        createStatisticButtonDominantGenotype(map, mapStatus);
        return map;
    }

}
