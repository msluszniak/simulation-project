package agh.cs.project.map;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.IMapElement;

import java.util.*;

public class RectangularMap {

    private final AnimalCollection animalCollection = new AnimalCollection();
    private final GrassCollection grassCollection = new GrassCollection();
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d lowerLeftJungle;
    private final Vector2d upperRightJungle;
    private final int initialEnergy;
    private final Set<Vector2d> emptyPlacesJungle = new HashSet<>();
    private final Set<Vector2d> emptyPlacesOutsideJungle = new HashSet<>();


    public RectangularMap(int width, int height, double jungleRatio, int initialEnergy) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
        this.initialEnergy = initialEnergy;
        int jungleX = (int) (jungleRatio * width);
        int jungleY = (int) (jungleRatio * height);
        this.lowerLeftJungle = new Vector2d(width / 2 - jungleX / 2, height / 2 - jungleY / 2);
        this.upperRightJungle = new Vector2d(width / 2 + jungleX / 2, height / 2 + jungleY / 2);
        for (int i = lowerLeft.x; i < upperRight.x; i++) {
            for (int j = lowerLeft.y; j < upperRight.y; j++) {
                Vector2d position = new Vector2d(i, j);
                if (this.isInJungle(position)) {
                    emptyPlacesJungle.add(position);
                }
                emptyPlacesOutsideJungle.add(position);
            }
        }
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getLowerLeftJungle() {
        return lowerLeftJungle;
    }

    public Vector2d getUpperRightJungle() {
        return upperRightJungle;
    }


    // generujemy z przedziału <min, max)
    public Vector2d initializeRandomPositionInBounds() {
        int x = (int) (Math.random() * (this.upperRight.x - this.lowerLeft.x + 1) + this.lowerLeft.x);
        int y = (int) (Math.random() * (this.upperRight.y - this.lowerLeft.y + 1) + this.lowerLeft.y);
        return new Vector2d(x, y);
    }

    public void energyChanged(Animal oldAnimal, Animal animal) {
        this.animalCollection.getAnimalMap().get(animal.getPosition()).remove(oldAnimal);
        this.animalCollection.getAnimalMap().get(animal.getPosition()).add(animal);
    }


    private Set<Vector2d> getProperArea(Vector2d position) {
        return position.isInArea(lowerLeftJungle, upperRightJungle) ? emptyPlacesJungle : emptyPlacesOutsideJungle;
    }

    public void positionChanged(Vector2d oldPosition, Animal animal) {
        this.animalCollection.removeAnimalByPositionOld(oldPosition, animal);
        if (!this.animalCollection.containsKey(oldPosition))
            getProperArea(oldPosition).add(oldPosition);

        animalCollection.addAnimal(animal);
        getProperArea(animal.getPosition()).remove(animal.getPosition());
    }

    public void addElement(IMapElement element) {
        if (element instanceof Grass) {
            grassCollection.addGrass(element);
        } else {
            animalCollection.addAnimal(element);
            ((Animal) element).addObserver(this);
        }
        this.emptyPlacesJungle.remove(element.getPosition());
        this.emptyPlacesOutsideJungle.remove(element.getPosition());
    }

    public void removeElement(IMapElement element) {
        Vector2d actualPosition = element.getPosition();
        if (element instanceof Grass) {
            grassCollection.removeGrass(element);
        } else {
            animalCollection.removeAnimal(element);
        }
        if (!animalCollection.getAnimalMap().containsKey(actualPosition)
                && !grassCollection.getGrassMap().containsKey(actualPosition)) {
            if (actualPosition.isInArea(lowerLeftJungle, upperRightJungle)) {
                emptyPlacesJungle.add(actualPosition);
            } else {
                emptyPlacesOutsideJungle.add(actualPosition);
            }
        }
    }

    public Vector2d getProperPositionForBabyAnimal(Vector2d parentPosition) {
        Random random = new Random(42);
        List<Vector2d> potentialPositions = new LinkedList<>();
        List<Vector2d> potentialOccupiedPositions = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            if (grassCollection.getGrassMap().containsKey(parentPosition.add(MapDirection.values()[i].toUnitVector()))) {
                continue;
            }
            if (animalCollection.getAnimalMap().containsKey(parentPosition.add(MapDirection.values()[i].toUnitVector()))) {
                potentialOccupiedPositions.add(parentPosition.add(MapDirection.values()[i].toUnitVector()));
            }
            potentialPositions.add(parentPosition.add(MapDirection.values()[i].toUnitVector()));
        }
        if (!potentialPositions.isEmpty()) {
            return potentialPositions.get(random.nextInt(potentialPositions.size()));
        }
        return potentialOccupiedPositions.get(random.nextInt(potentialOccupiedPositions.size()));
    }

    public Set<Vector2d> getEmptyPlacesJungle() {
        return this.emptyPlacesJungle;
    }

    public Set<Vector2d> getEmptyPlacesOutsideJungle() {
        return this.emptyPlacesOutsideJungle;
    }

    public List<Animal> getListOfAnimals() {
        return animalCollection.animalsToList();
    }

    public List<Grass> getListOfGrasses() {
        return grassCollection.grassesToList();
    }

    public Map<Vector2d, Grass> getGrassesMap() {
        return this.grassCollection.getGrassMap();
    }

    public GrassCollection getGrassCollection() {
        return this.grassCollection;
    }

    public AnimalCollection getAnimalCollection() {
        return this.animalCollection;
    }

    public boolean isInJungle(Vector2d position) {
        return position.x >= this.lowerLeftJungle.x && position.x <= this.upperRightJungle.x &&
                position.y >= this.lowerLeftJungle.y && position.y <= this.upperRightJungle.y;
    }

}
