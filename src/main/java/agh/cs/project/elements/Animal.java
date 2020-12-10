package agh.cs.project.elements;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.engine.IDeadAnimalOnPosition;
import agh.cs.project.engine.IPositionChangeObserver;
import agh.cs.project.map.MapDirection;
import agh.cs.project.map.RectangularMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement, IDeadAnimalOnPosition {
    private Vector2d position;
    private Genotype genotype;
    private int energy;
    private RectangularMap map;
    private int birthDate;
    private int deathDate;
    private final int initialEnergy;
    private List<Animal> children = new ArrayList<>();
    private final List<RectangularMap> observerList = new ArrayList<>();

    public Animal(RectangularMap map, Vector2d initPos, Genotype genotype, int energy, int birthDate){
        this.map = map;
        this.position = initPos;
        this.addObserver(map);
        this.genotype = genotype;
        this.energy = energy;
        this.birthDate = birthDate;
        this.deathDate = -1;
        this.initialEnergy = energy;

        map.addElement(this);
    }
    public void positionChanged(Vector2d oldPosition){
        for (RectangularMap observer: observerList) {
            observer.positionChanged(oldPosition, this);
        }
    }

    public boolean isAlreadyDead(int date){
        if(this.energy <= 0){
            this.deathDate = date;
            return true;
        }
        return false;
    }

    public int howLongAnimalLive(int date){
        if(this.deathDate == -1){
            return this.deathDate - this.birthDate;
        }
        return date - this.birthDate;
    }


    public void addObserver(RectangularMap observer){
        observerList.add(observer);
    }
    void removeObserver(IPositionChangeObserver observer){
        for (int i = 0; i < observerList.size(); i++){
            if(observerList.get(i).equals(observer)){
                observerList.remove(i);
                break;
            }
        }
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy(){ return this.energy; }

    public void move() {
        Random random = new Random(42);
        int randomGen = random.nextInt(32);
        int gen = genotype.getGenotype().get(randomGen);
        MapDirection direction = MapDirection.NORTH.toEnum(gen); //troche śmiesznie wygląda
        Vector2d oldPosition = position;
        position = position.add(direction.toUnitVector(), map.getUpperRight().x,map.getUpperRight().y);
        this.energy = this.energy - this.map.getEnergyLoss();
        this.positionChanged(oldPosition);
    }

    //jeśli nie mogą się rozmnożyć, to wzracam nulla, uważać na to
    public Animal reproduce(Animal parent, int date, Vector2d initPos){
        if(this.energy > this.initialEnergy/2 && parent.energy > parent.initialEnergy){
            int kidEnergy = this.energy/4 + parent.energy/4;
            this.energy = this.energy - this.energy/4;
            parent.energy = parent.energy - parent.energy/4;
            Genotype kidGenotype = this.genotype.mixGenotypes(parent.genotype);
            Animal kid =  new Animal(this.map, initPos, kidGenotype, kidEnergy, date);
            this.children.add(kid);
            parent.children.add(kid);
            return kid;
        }
        return null;
    }

}
