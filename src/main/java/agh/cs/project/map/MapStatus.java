package agh.cs.project.map;

import agh.cs.project.elements.Animal;
import agh.cs.project.engine.Engine;

import java.util.List;

public class MapStatus {

    private Engine engine;

    public MapStatus(Engine engine){
        this.engine = engine;
    }

    public int numberOfAliveAnimals(){
        return this.engine.getMap().getListOfAnimals().size();
    }

    public int numberOfGrasses(){
        return this.engine.getMap().getListOfGrasses().size();
    }

    public double averageLifespan(){
        List<Animal> animalList = this.engine.getMap().getListOfAnimals();
        int cumulativeEnergy = 0;
        for(Animal animal : animalList){
            cumulativeEnergy += animal.getEnergy();
        }
        return cumulativeEnergy/ ((double) this.numberOfAliveAnimals());
    }

    public double averageNumberOfBabies(){
        List<Animal> animalList = this.engine.getMap().getListOfAnimals();
        int cumulativeNumberOfBabies = 0;
        for(Animal animal : animalList){
            cumulativeNumberOfBabies += animal.getChildren().size();
        }
        return cumulativeNumberOfBabies/ ((double) this.numberOfAliveAnimals());
    }





}
