package agh.cs.project.engine;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.map.AnimalCollection;
import agh.cs.project.map.GrassCollection;
import agh.cs.project.map.RectangularMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;

public class Engine {
    private final RectangularMap map;
    private final int initialNumberOfAnimals;
    private int actualDate = 0;
    private final int energyFromGrass;
    private final int energyLoss;
    private final Random random = new Random();
    private int cumulativeDeadAnimalsDays = 0;
    private int numberOfDeadAnimals = 0;

    public Engine(int width, int height, double jungleRation, int initialEnergy,
                  int initialNumberOfAnimals, int energyFromGrass, int energyLoss) {
        this.map = new RectangularMap(width, height, jungleRation, initialEnergy);
        this.initialNumberOfAnimals = initialNumberOfAnimals;
        this.energyFromGrass = energyFromGrass;
        this.energyLoss = energyLoss;
    }


    public void removeDeadAnimals() {
        List<Animal> animalList = this.map.getListOfAnimals();
        for (Animal animal : animalList) {
            if (animal.isAlreadyDead(this.actualDate)) {
                this.numberOfDeadAnimals++;
                this.cumulativeDeadAnimalsDays += this.actualDate - animal.getBirthDate();
                this.map.removeElement(animal);
            }

        }
    }

    public void moveAllAnimals() {
        List<Animal> animalList = this.map.getListOfAnimals();
        for (Animal animal : animalList) {
            animal.move(energyLoss);
        }
    }

    public void grassEating() {
        GrassCollection grassCollection = this.map.getGrassCollection();
        AnimalCollection animalCollection = this.map.getAnimalCollection();

        for (SortedSet<Animal> set : animalCollection.getAnimalMap().values()) {
            List<Animal> strongestAnimals = new LinkedList<>();
            int strongestAnimalEnergy = set.first().getEnergy();
            if(strongestAnimalEnergy <= 0) continue;
            for (Object strongAnimal : set) {
                if (((Animal) strongAnimal).getEnergy() == strongestAnimalEnergy) {
                    strongestAnimals.add((Animal) strongAnimal);
                } else break;
            }
            if (strongestAnimals.isEmpty()) continue;

            Vector2d position = strongestAnimals.get(0).getPosition();
            if (grassCollection.getGrassMap().containsKey(position)) {
                for (Animal strongAnimal : strongestAnimals) {
                    //minus bo w changeEnergy odejmujemy
                    strongAnimal.changeEnergy(-energyFromGrass / strongestAnimals.size());
                }

                set.addAll(strongestAnimals);
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
            this.map.addElement(new Grass(emptyPlacesJungle.get(random.nextInt(emptyPlacesJungle.size()))));
        }
        if (emptyPlacesOutsideJungle.size() > 0) {
            this.map.addElement(new Grass(emptyPlacesOutsideJungle.get(random.nextInt(emptyPlacesOutsideJungle.size()))));
        }
    }

    public void step() {
        this.removeDeadAnimals();
        this.moveAllAnimals();
        this.grassEating();
        this.reproduceAnimal();
        this.placeNewGrasses();
        this.changeDate();
    }

    public void changeDate() {
        this.actualDate++;
    }

    public int getActualDate() {
        return this.actualDate;
    }

    public RectangularMap getMap() {
        return this.map;
    }

    public int getInitialNumberOfAnimals() {
        return initialNumberOfAnimals;
    }

    public int getNumberOfDeadAnimals() {
        return numberOfDeadAnimals;
    }

    public int getCumulativeDeadAnimalsDays() {
        return cumulativeDeadAnimalsDays;
    }


}
