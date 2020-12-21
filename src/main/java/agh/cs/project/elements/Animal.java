package agh.cs.project.elements;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.engine.IDeadAnimalOnPosition;
import agh.cs.project.engine.IPositionChangeObserver;
import agh.cs.project.map.MapDirection;
import agh.cs.project.map.RectangularMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement, IDeadAnimalOnPosition {
    private Vector2d position;
    private final Genotype genotype;
    private int energy;
    private final RectangularMap map;
    private final int birthDate;
    private int deathDate;
    private final List<Animal> children = new LinkedList<>();
    private final List<RectangularMap> observerList = new ArrayList<>();
    private final List<RectangularMap> energyObserverList = new ArrayList<>();
    private final List<IAnimalObserver> trackObserver = new ArrayList<>();
    private static int generalId = 1;
    private final int id;

    public Animal(RectangularMap map, Vector2d initPos, Genotype genotype, int energy, int birthDate) {
        this.map = map;
        this.position = initPos;
        this.genotype = genotype;
        this.energy = energy;
        this.birthDate = birthDate;
        this.deathDate = -1;
        generalId++;
        this.id = generalId;
    }

    public Animal(Animal copy) {
        this.map = copy.map;
        this.position = copy.position;
        this.genotype = copy.genotype;
        this.energy = copy.energy;
        this.birthDate = copy.birthDate;
        this.deathDate = copy.birthDate;
        this.id = copy.id;
    }

    public void addTrackObserver(IAnimalObserver observer) {
        trackObserver.add(observer);
    }

    public void removeTrackObserver(IAnimalObserver observer) {
        trackObserver.remove(observer);
    }


    public void positionChanged(Vector2d oldPosition) {
        for (RectangularMap observer : observerList) {
            observer.positionChanged(oldPosition, this);
        }
    }

    public void energyChanged(Animal oldAnimal) {
        //for(RectangularMap energyObserver: energyObserverList)
            map.energyChanged(oldAnimal, this);
    }


    public boolean isAlreadyDead(int date) {
        if (this.energy <= 0) {
            this.deathDate = date;
            return true;
        }
        return false;
    }

    public int howLongAnimalLive(int date) {
        if (this.deathDate == -1) {
            return date - this.birthDate;
        }
        return this.deathDate - this.birthDate;
    }


    public void addObserver(RectangularMap observer) {
        observerList.add(observer);
    }

    public void addEnergyObserver(RectangularMap energyObserver) {
        energyObserverList.add(energyObserver);
    }

    void removeObserver(IPositionChangeObserver observer) {
        for (int i = 0; i < observerList.size(); i++) {
            if (observerList.get(i).equals(observer)) {
                observerList.remove(i);
                break;
            }
        }
    }

    public ArrayList<Integer> getGenotype() {
        return this.genotype.getGenotype();
    }

    public int getBirthDate() {
        return this.birthDate;
    }

    public RectangularMap getMap() {
        return this.map;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getDeathDate() {
        return this.deathDate;
    }

    public int getEnergy() {
        return this.energy;
    }

    public List<Animal> getChildren() {
        return this.children;
    }

    public int getId() {
        return this.id;
    }

    public void changeEnergy(int change) {
        Animal oldAnimal = new Animal(this);
        this.energy -= change;
        this.energyChanged(oldAnimal);
    }


    public void move(int energyLoss) {
        Random random = new Random();
        int randomGen = random.nextInt(32);
        int gen = genotype.getGenotype().get(randomGen);
        MapDirection direction = MapDirection.values()[gen];
        Vector2d oldPosition = position;
        position = position.add(direction.toUnitVector()).getCorrectPosition(map.getLowerLeft(), map.getUpperRight());
        this.positionChanged(oldPosition);
        this.changeEnergy(energyLoss);
    }

    //jeśli nie mogą się rozmnożyć, to wzracam nulla, uważać na to
    public Animal reproduce(Animal parent, int date) {
        if (this.energy > this.map.getInitialEnergy() / 2 && parent.energy > parent.map.getInitialEnergy() / 2) {
            int kidEnergy = this.energy / 4 + parent.energy / 4;
            this.energy = this.energy - this.energy / 4;
            parent.energy = parent.energy - parent.energy / 4;
            Vector2d babyPosition = map.getProperPositionForBabyAnimal(this.getPosition()).getCorrectPosition(map.getLowerLeft(), map.getUpperRight());
            Genotype kidGenotype = this.genotype.mixGenotypes(parent.genotype);
            Animal kid = new Animal(this.map, babyPosition, kidGenotype, kidEnergy, date);
            this.children.add(kid);
            parent.children.add(kid);
            return kid;
        }
        return null;
    }

}
