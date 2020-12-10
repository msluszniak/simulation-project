package agh.cs.project.map;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.IMapElement;

import java.util.*;

public class RectangularMap {
    //tutaj chyba lepiej zrobić dwie mapy, jedna dla grassów, druga dla animali
    //wtedy animale sortować po energi i wówczas łatwo mieć najsilniejsze animale
    //do rozmnażania i do karmienia

    //Map<Vector2d, Set<IMapElement>> grassList = new LinkedHashMap<>();
    //Map<Vector2d, SortedSet<Animal>> animalList = new LinkedHashMap<>();

    //jak to powinno być napisane
    public AnimalCollection animalCollection = new AnimalCollection();
    public GrassCollection grassCollection = new GrassCollection();
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private Vector2d lowerLeftJungle;
    private Vector2d upperRightJungle;
    int energyLoss;

    public RectangularMap(int width, int height, double jungleRatio, int energyLoss){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width,height);
        int jungleX = (int) jungleRatio * width;
        int jungleY = (int) jungleRatio * height;
        this.lowerLeftJungle = new Vector2d( width/2 - jungleX/2,  height/2 - jungleY/2);
        this.upperRightJungle = new Vector2d( width/2 + jungleX/2,  height/2 + jungleY/2);
        this.energyLoss = energyLoss;
    }
    public Vector2d getUpperRight(){
        return upperRight;
    }

//    public boolean isInBounds(Vector2d position) {
//        return lowerLeft.precedes(position) && upperRight.follows(position);
//    }

    // generujemy z przedziału <min, max)
    public Vector2d initializeRandomPositionInBounds(){
        int x = (int) (Math.random() * (this.upperRight.x - this.lowerLeft.x + 1) + this.lowerLeft.x);
        int y = (int) (Math.random() * (this.upperRight.y - this.lowerLeft.y + 1) + this.lowerLeft.y);
        return new Vector2d(x, y);
    }

    public int getEnergyLoss(){
        return this.energyLoss;
    }

    //tutaj trzeba będzie doprecyzować te funkcje aby można było z nich korzystać
    //public Object objectAt(Vector2d position){
    //    return elementsMap.get(position);
    //}
    //public boolean isOccupied(Vector2d position){
    //    return elementsMap.containsKey(position);
    //}
    public void positionChanged(Vector2d oldPosition, Animal animal){
        animalCollection.positionChangedElement(oldPosition, animal);
    }

    public void addElement(IMapElement element){
        if(element instanceof Grass){
            grassCollection.addGrass(element);
        }
        else {
            animalCollection.addAnimal(element);
            ((Animal) element).addObserver(this);
        }
    }

    public void removeElement(IMapElement element){
        Vector2d actualPosition = element.getPosition();
        if(element instanceof Grass) {
            grassCollection.removeGrass(element);
        }
        else{
            animalCollection.removeAnimal(element);
        }
    }

    public List<Animal> getListOfAnimals(){
        return animalCollection.animalsToList();
    }

    public List<Grass> getListOfGrasses(){
        return grassCollection.grassesToList();
    }

}
