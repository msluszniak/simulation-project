package agh.cs.project.map;

import agh.cs.project.elements.Animal;
import agh.cs.project.engine.Engine;
import javafx.util.Pair;

import java.util.*;

public class MapStatus {

    private final Engine engine;

    public MapStatus(Engine engine){
        this.engine = engine;
    }

    public int numberOfAliveAnimals(){
        return this.engine.getMap().getListOfAnimals().size();
    }

    public int numberOfGrass(){
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

    public Map.Entry<List<Integer>, Set<Animal>> dominantGenotype(){
        List<Animal> animals = this.engine.getMap().getListOfAnimals();
        Map<List<Integer>, Set<Animal>> resultMapping = new HashMap<>();
        for(Animal animal : animals){
            if(!resultMapping.containsKey(animal.getGenotype())){
                resultMapping.put(animal.getGenotype(), new HashSet<>(Set.of(animal)));
            }
            else {
                resultMapping.get(animal.getGenotype()).add(animal);
            }
        }
        int max_size = 0;
        Map.Entry<List<Integer>, Set<Animal>> dominantGenotype = null;
        for(Map.Entry<List<Integer>, Set<Animal>> pair : resultMapping.entrySet()){
            if(max_size < pair.getValue().size()){
                max_size = pair.getValue().size();
                dominantGenotype = pair;
            }
        }
        return dominantGenotype;
    }

    public int numberOfChildren(Animal animal){
        return animal.getChildren().size();
    }

    public Engine getEngine(){
        return engine;
    }
}
