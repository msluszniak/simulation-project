package agh.cs.project.engine;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.IMapElement;
import agh.cs.project.map.RectangularMap;

import java.util.List;

public class Engine {
    private RectangularMap map;
    private int initialEnergy;
    private int initialNumberOfAnimals;
    private int numberOfEpochs;
    private int actualDate = 0;
    private int energyFromGrass;

    public Engine(int width, int height, int jungleRation, int initialEnergy,
                  int initialNumberOfAnimals, int numberOfEpochs, int energyFromGrass, int energyLoss){
        this.map = new RectangularMap(width, height, jungleRation, energyLoss);
        this.initialEnergy = initialEnergy;
        this.initialNumberOfAnimals = initialNumberOfAnimals;
        this.numberOfEpochs = numberOfEpochs;
        this.energyFromGrass = energyFromGrass;
    }

    public void spawnInitialAnimals(){
        for(int i = 0; i < initialNumberOfAnimals ; i++){
            Vector2d initPosition = this.map.initializeRandomPositionInBounds();
            Genotype genotype = new Genotype();
            new Animal(this.map, initPosition, genotype, this.initialEnergy, 0);
        }
    }

    public void removeDeadAnimals(){
        List<Animal> animalList = this.map.animalCollection.animalsToList();
        for (Animal animal: animalList) {
            if(animal.isAlreadyDead(this.actualDate)){
                this.map.animalCollection.removeAnimal((IMapElement) animal);
            }
        }
    }

    public void moveAllAnimals(){
        List<Animal> animalList = this.map.animalCollection.animalsToList();
        for (Animal animal: animalList) {
            animal.move();
        }
    }







}
