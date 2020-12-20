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

    public boolean containsKey(Vector2d position){
        return animals.containsKey(position);
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
        if (animals.containsKey(actualPosition)) {
            animals.get(actualPosition).remove( (Animal) element);

            if (animals.get(actualPosition).isEmpty()) {
                animals.remove(actualPosition);
            }
        }
    }

    public void removeAnimalByPositionOld(Vector2d oldPosition, Animal animal){
        if (animals.containsKey(oldPosition)) {
            animals.get(oldPosition).remove(animal);

            if (animals.get(oldPosition).isEmpty()) {
                animals.remove(oldPosition);
            }
        }
    }

    public List<Animal> getParents(Vector2d position) {
        SortedSet<Animal> set =  animals.get(position);
        if (set.size() < 2) return null;
        Iterator<Animal> iterator = set.iterator();
        return List.of(iterator.next(), iterator.next());
    }

}
