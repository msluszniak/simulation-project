package agh.cs.project.engine;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.IMapElement;
import agh.cs.project.map.AnimalCollection;
import agh.cs.project.map.GrassCollection;
import agh.cs.project.map.RectangularMap;

import java.util.*;

public class Engine {
    private RectangularMap map;
    private int initialEnergy;
    private int initialNumberOfAnimals;
    private int numberOfEpochs;
    private int actualDate = 0;
    private int energyFromGrass;
    private Random random = new Random(42);

    public Engine(int width, int height, int jungleRation, int initialEnergy,
                  int initialNumberOfAnimals, int numberOfEpochs, int energyFromGrass, int energyLoss) {
        this.map = new RectangularMap(width, height, jungleRation, energyLoss);
        this.initialEnergy = initialEnergy;
        this.initialNumberOfAnimals = initialNumberOfAnimals;
        this.numberOfEpochs = numberOfEpochs;
        this.energyFromGrass = energyFromGrass;
    }

    public void spawnInitialAnimals() {
        for (int i = 0; i < initialNumberOfAnimals; i++) {
            Vector2d initPosition = this.map.initializeRandomPositionInBounds();
            Genotype genotype = new Genotype();
            new Animal(this.map, initPosition, genotype, this.initialEnergy, 0);
        }
    }

    public void removeDeadAnimals() {
        List<Animal> animalList = this.map.getListOfAnimals();
        for (Animal animal : animalList) {
            if (animal.isAlreadyDead(this.actualDate)) {
                this.map.removeElement((IMapElement) animal);
            }
        }
    }

    public void moveAllAnimals() {
        List<Animal> animalList = this.map.getListOfAnimals();
        for (Animal animal : animalList) {
            animal.move();
        }
    }

    public void grassEating(){
        //List<Animal> animalList = this.map.getListOfAnimals();
        //Map<Vector2d, SortedSet<Animal>> animalList = this.map.getAnimalMap();
        //Map<Vector2d, Set<Grass>> grassesList = this.map.getGrassesMap();
        GrassCollection grassCollection = this.map.getGrassCollection();
        AnimalCollection animalCollection = this.map.getAnimalCollection();

        for (SortedSet<Animal> set : animalCollection.getAnimalMap().values()) {
            List<Animal> strongestAnimals = new LinkedList<>();
            int strongestAnimalEnergy = ((Animal) set.first()).getEnergy();
            for(Object strongAnimal : set) {
                if (((Animal) strongAnimal).getEnergy() == strongestAnimalEnergy) {
                    strongestAnimals.add((Animal) strongAnimal);
                } else break;
            }
            if(strongestAnimals.isEmpty()) continue;

            Vector2d position = strongestAnimals.get(0).getPosition();
            if (!grassCollection.getGrassMap().containsKey(position)) {
                for(Animal strongAnimal : strongestAnimals){
                    //minus bo w changeEnergy odejmujemy
                    strongAnimal.changeEnergy(-energyFromGrass/strongestAnimals.size());
                }
            }
            for(Animal strongAnimal : strongestAnimals){
                set.add(strongAnimal);
            }
            grassCollection.removeGrass( grassCollection.getGrassMap().get(position));
        }
    }

    public void reproduceAnimal(){
        AnimalCollection animalCollection = this.map.getAnimalCollection();
        for (SortedSet<Animal> set : animalCollection.getAnimalMap().values()){
            if(set.size() < 2) continue;
            int counter = 0;
            List<Animal> animals = new LinkedList();
            for(Animal animal : set){
                animals.add(animal);
                counter += 1;
                if(counter == 2) break;
            }
            Animal animal = animals.get(0);
            Vector2d babyPosition = map.getProperPositionForBabyAnimal(animal.getPosition());
            Animal kid = animal.reproduce(animals.get(1), this.actualDate, babyPosition);
            if((kid != null)){
                this.map.addElement((IMapElement) kid);
            }
        }
    }

    public void placeNewGrasses(){
        LinkedList<Vector2d> emptyPlacesJungle = new LinkedList<Vector2d>(map.getEmptyPlacesJungle());
        LinkedList<Vector2d> emptyPlacesOutsideJungle = new LinkedList<Vector2d>(map.getEmptyPlacesOutsideJungle());
        if(emptyPlacesJungle.size() > 0){
            this.map.addElement(new Grass(map, emptyPlacesJungle.get(random.nextInt(emptyPlacesJungle.size()))));
        }
        if(emptyPlacesOutsideJungle.size() > 0) {
            this.map.addElement(new Grass(map, emptyPlacesOutsideJungle.get(random.nextInt(emptyPlacesOutsideJungle.size()))));
        }
    }

    public void changeDate(){
        this.actualDate++;
    }

    public RectangularMap getMap(){
        return this.map;
    }


}
