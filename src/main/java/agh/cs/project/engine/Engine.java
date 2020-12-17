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
    private int actualDate = 0;
    private int energyFromGrass;
    private int energyLoss;
    private Random random = new Random();

    public Engine(int width, int height, double jungleRation, int initialEnergy,
                  int initialNumberOfAnimals, int energyFromGrass, int energyLoss) {
        this.map = new RectangularMap(width, height, jungleRation, initialEnergy);
        //this.initialEnergy = initialEnergy;
        this.initialNumberOfAnimals = initialNumberOfAnimals;
        this.energyFromGrass = energyFromGrass;
        this.energyLoss = energyLoss;
    }

    public void spawnInitialAnimals() {
        for (int i = 0; i < initialNumberOfAnimals; i++) {
            Vector2d initPosition = this.map.initializeRandomPositionInBounds();
            Genotype genotype = new Genotype();
            Animal animal = new Animal(this.map, initPosition, genotype, this.map.getInitialEnergy(), 0);
            map.addElement(animal);
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
            animal.move(energyLoss);
        }
    }

    //tutaj trzeba usuwać silne zwierzęta przy wyciąganiu z setu, a następnie je dodać spowrotem z nową energią
    public void grassEating() {
        //List<Animal> animalList = this.map.getListOfAnimals();
        //Map<Vector2d, SortedSet<Animal>> animalList = this.map.getAnimalMap();
        //Map<Vector2d, Set<Grass>> grassesList = this.map.getGrassesMap();
        GrassCollection grassCollection = this.map.getGrassCollection();
        AnimalCollection animalCollection = this.map.getAnimalCollection();

        for (SortedSet<Animal> set : animalCollection.getAnimalMap().values()) {
            List<Animal> strongestAnimals = new LinkedList<>();
            int strongestAnimalEnergy = ((Animal) set.first()).getEnergy();
            for (Object strongAnimal : set) {
                if (((Animal) strongAnimal).getEnergy() == strongestAnimalEnergy) {
                    strongestAnimals.add((Animal) strongAnimal);
                } else break;
            }
            if (strongestAnimals.isEmpty()) continue;

            Vector2d position = strongestAnimals.get(0).getPosition();
            if (grassCollection.getGrassMap().containsKey(position)) {
                //System.out.println("haha");
                for (Animal strongAnimal : strongestAnimals) {
                    //minus bo w changeEnergy odejmujemy
                    strongAnimal.changeEnergy(-energyFromGrass / strongestAnimals.size());
                }

                for (Animal strongAnimal : strongestAnimals) {
                    set.add(strongAnimal);
                }
                map.removeElement(grassCollection.getGrassMap().get(position));
            }
        }
    }


    public void reproduceAnimal() {
        List<Animal> animalList = new LinkedList<>();
        for (Vector2d position : this.map.getAnimalCollection().getAnimalMap().keySet()) {
            List<Animal> parents = this.map.getAnimalCollection().getParents(position);
            if (parents != null) {
                Animal kid = parents.get(0).reproduce(parents.get(1), this.actualDate);
                if (kid != null) animalList.add(kid);
            }
        }
        for (Animal animal : animalList) {
            this.map.addElement(animal);
        }
    }

    public void placeNewGrasses() {
        LinkedList<Vector2d> emptyPlacesJungle = new LinkedList<>(map.getEmptyPlacesJungle());
        LinkedList<Vector2d> emptyPlacesOutsideJungle = new LinkedList<>(map.getEmptyPlacesOutsideJungle());
        if (emptyPlacesJungle.size() > 0) {
            this.map.addElement(new Grass(map, emptyPlacesJungle.get(random.nextInt(emptyPlacesJungle.size()))));
        }
        if (emptyPlacesOutsideJungle.size() > 0) {
            this.map.addElement(new Grass(map, emptyPlacesOutsideJungle.get(random.nextInt(emptyPlacesOutsideJungle.size()))));
        }
    }

    public void step() {
        this.removeDeadAnimals();
//        System.out.println("removeDeadAnimals");
        this.moveAllAnimals();
//        System.out.println("moveAllAnimals");
        this.grassEating();
//        System.out.println("grassEating");
        this.reproduceAnimal();
//        System.out.println("reproduceAnimal");
        this.placeNewGrasses();
        //System.out.println("placeNewGrasses");
        this.changeDate();
        //System.out.println("changeDate");

    }

    public void changeDate() {
        this.actualDate++;
    }

    public RectangularMap getMap() {
        return this.map;
    }


}
