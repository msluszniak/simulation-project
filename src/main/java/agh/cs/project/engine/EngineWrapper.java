package agh.cs.project.engine;

import agh.cs.project.elements.Animal;

import java.util.LinkedList;
import java.util.List;

public class EngineWrapper implements Runnable {
    private final Engine engine;
    private final Engine engine1;

    public EngineWrapper(int width, int height, int energyFromGrass, int startEnergy, double jungleRatio, int initialNumberOfAnimals, int energyLoss) {
        this.engine = new Engine(width, height, jungleRatio, startEnergy, initialNumberOfAnimals, energyFromGrass, energyLoss);
        engine.spawnInitialAnimals();
        this.engine1 = new Engine(width, height, jungleRatio, startEnergy, initialNumberOfAnimals, energyFromGrass, energyLoss);
        List<Animal> startAnimalList = this.engine.getMap().getListOfAnimals();
        for (Animal animal : startAnimalList){
            this.engine1.getMap().addElement(animal);
        }
    }

    public Engine getEngine() {
        return engine;
    }

    public Engine getEngine1(){
        return engine1;
    }

    public void run(){
        engine.step();
    }
}
