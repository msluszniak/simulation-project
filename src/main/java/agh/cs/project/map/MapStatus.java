package agh.cs.project.map;

import agh.cs.project.elements.Animal;
import agh.cs.project.engine.Engine;

import java.util.List;

public class MapStatus {

    private final Engine engine;

    public MapStatus(Engine engine){
        this.engine = engine;
    }

    public int numberOfAliveAnimals(){
        return this.engine.getMap().getListOfAnimals().size();
    }

    public int numberOfGrasses(){
        return this.engine.getMap().getListOfGrasses().size();
    }

    public double averageEnergy(){
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

    public double averageLifespan(){
        return this.engine.getCumulativeDeadAnimalsDays()/((double) this.engine.getNumberOfDeadAnimals());
    }

    public int numberOfChildren(Animal animal){
        return animal.getChildren().size();
    }









}
