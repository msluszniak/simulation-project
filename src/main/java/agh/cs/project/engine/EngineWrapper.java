package agh.cs.project.engine;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;

import java.util.LinkedList;
import java.util.List;

public class EngineWrapper implements Runnable {
    private final Engine engine;
    private final Engine engine1;

    public EngineWrapper(int width, int height, int energyFromGrass, int startEnergy, double jungleRatio, int initialNumberOfAnimals, int energyLoss) {
        this.engine = new Engine(width, height, jungleRatio, startEnergy, initialNumberOfAnimals, energyFromGrass, energyLoss);
        this.engine1 = new Engine(width, height, jungleRatio, startEnergy, initialNumberOfAnimals, energyFromGrass, energyLoss);
        this.spawnInitialAnimals();
        }

    public void spawnInitialAnimals() {
        for (int i = 0; i < engine.getInitialNumberOfAnimals(); i++) {
            Vector2d initPosition = this.engine.getMap().initializeRandomPositionInBounds();
            Genotype genotype = new Genotype();
            Animal animal = new Animal(this.engine.getMap(), initPosition, genotype, this.engine.getMap().getInitialEnergy(), 0);
            Animal animal1 = new Animal(this.engine1.getMap(), initPosition, genotype, this.engine1.getMap().getInitialEnergy(), 0);
            engine1.getMap().addElement(animal);
            engine.getMap().addElement(animal1);
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
