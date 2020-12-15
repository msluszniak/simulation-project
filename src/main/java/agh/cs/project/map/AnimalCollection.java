package agh.cs.project.map;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.IMapElement;

import java.util.*;

public class AnimalCollection {
    private Map<Vector2d, SortedSet<Animal>> animalList = new LinkedHashMap<>();
    //private RectangularMap map;

    public AnimalCollection(){}
    public AnimalCollection(Map<Vector2d, SortedSet<Animal>> animalList) {
        this.animalList = animalList;
        //this.map = map;
    }

    public List<Animal> animalsToList() {
        List<Animal> list = new LinkedList<>();
        for (SortedSet<Animal> sortedSet : animalList.values()) {
            list.addAll(sortedSet);
        }
        return list;
    }

    public Map<Vector2d, SortedSet<Animal>> getAnimalMap(){
        return this.animalList;
    }

    public void addAnimal(IMapElement element){
        if (!animalList.containsKey(element.getPosition())) {
            SortedSet<Animal> animalsByEnergy = new TreeSet<>((animal1, animal2) -> animal1.getEnergy() - animal2.getEnergy());
            animalList.put(element.getPosition(), animalsByEnergy);
        }
        animalList.get(element.getPosition()).add( (Animal) element);
        //((Animal) element).addObserver(map);
    }

    public void removeAnimal(IMapElement element){
        Vector2d actualPosition = element.getPosition();
        animalList.get(actualPosition).remove(element);
        if (animalList.get(actualPosition).isEmpty()) {
            animalList.remove(actualPosition);
        }
    }

//    public void positionChangedElement(Vector2d oldPosition, Animal animal){
//        //IMapElement animal = elementsMap.get(oldPosition);
//        animalList.get(oldPosition).remove(animal);
//        //jeśli nie ma zwierzątek na oldposition to usuwamy set
//        if(animalList.get(oldPosition).isEmpty()){
//            animalList.remove(oldPosition);
//        }
//        //teraz dodajemy, jeśli nie ma seta na daną pozycję
//        //to trzeba go utworzyć
//        Vector2d newPosition = animal.getPosition();
//        if(!animalList.containsKey(newPosition)){
//            SortedSet<Animal> animalsByEnergy = new TreeSet<>((animal1, animal2) -> animal1.getEnergy() >= animal2.getEnergy() ? 1 : -1);
//            animalList.put(newPosition, animalsByEnergy);
//        }
//        animalList.get(newPosition).add(animal);
//    }

}
