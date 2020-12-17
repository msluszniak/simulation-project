package agh.cs.project.map;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.IMapElement;

import java.util.*;

public class AnimalCollection {
    private Map<Vector2d, SortedSet<Animal>> animals = new HashMap<>();

    public AnimalCollection() {
    }

    public AnimalCollection(Map<Vector2d, SortedSet<Animal>> animals) {
        this.animals = animals;
    }

    public List<Animal> animalsToList() {
        List<Animal> list = new LinkedList<>();
        for (SortedSet<Animal> sortedSet : animals.values()) {
            list.addAll(sortedSet);
        }
        return list;
    }

    public Map<Vector2d, SortedSet<Animal>> getAnimalMap() {
        return this.animals;
    }

    public void addAnimal(IMapElement element) {
        if (!animals.containsKey(element.getPosition())) {
            SortedSet<Animal> animalsByEnergy = new TreeSet<>((animal1, animal2) -> {
                int energy1 = animal1.getEnergy();
                int energy2 = animal2.getEnergy();
                if (energy1 > energy2) return 1;
                if (energy2 > energy1) return -1;
                if (animal1.getId() < animal2.getId()) return 1;
                else if (animal1.getId() > animal2.getId()) return -1;
                return 0;
            });
            animals.put(element.getPosition(), animalsByEnergy);
        }
        animals.get(element.getPosition()).add((Animal) element);
    }

    public void removeAnimal(IMapElement element) {
        Vector2d actualPosition = element.getPosition();
        System.out.println(animals.get(actualPosition).remove(element));
        if (animals.get(actualPosition).isEmpty()) {
            animals.remove(actualPosition);
        }
    }

    public List<Animal> getParents(Vector2d position) {
        SortedSet<Animal> set =  animals.get(position);
        if (set.size() < 2) return null;
        Iterator<Animal> iterator = set.iterator();
        return List.of(iterator.next(), iterator.next());
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
