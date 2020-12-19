package agh.cs.project.widget;

import agh.cs.project.elements.Animal;
import agh.cs.project.elements.IAnimalObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimalOnClick implements IAnimalObserver {
    private Animal animal;
    private int trackDuring;
    private int whenCaught;
    private int numberOfChildren;
    private int numberOfDescendants;
    private int dateOfDeath;

    public AnimalOnClick(){

    }

    public int getWhenCaught() {
        return whenCaught;
    }

    public int getDateOfDeath() {
        return dateOfDeath;
    }

    public AnimalOnClick(Animal animal){
        this.animal = animal;
        this.trackDuring = 0;
    }

    public void  changeAnimal(Animal newAnimal){
        this.animal = newAnimal;
    }

    public void setTrackDuring(int time){
        this.trackDuring = time;
    }

    public void setWhenCaught(int date){
        this.whenCaught = date;
    }

    public Animal getAnimal(){
        return this.animal;
    }

    public int getTrackDuring(){
        return this.trackDuring;
    }


    @Override
    public int getNumberOfChildren() {
        return this.numberOfChildren;
    }

    @Override
    public int getNumberOfDescendants() {
        return this.numberOfDescendants;
    }

    @Override
    public void update() {
        this.numberOfChildren = this.animal.getChildren().size();
        this.numberOfDescendants = getDescendants(animal.getChildren()).size();
    }

    private Set<Animal> getDescendants(List <Animal> children){
        Set<Animal> descendants = new HashSet<>(children);
        for(Animal child : children){
            descendants.addAll(getDescendants(child.getChildren()));
        }
        return descendants;
    }

    @Override
    public Animal getTrackedAnimal() {
        return this.animal;
    }

    @Override
    public void startObserving(Animal animal) {
        if(this.animal != null){
            stopObserving(animal);
        }
        animal.addTrackObserver(this);
        this.animal = animal;
    }

    @Override
    public void stopObserving(Animal animal) {
        animal.removeTrackObserver(this);
        this.animal = null;
    }
}
