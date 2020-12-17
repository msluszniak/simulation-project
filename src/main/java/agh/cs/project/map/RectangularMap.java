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
    private final AnimalCollection animalCollection = new AnimalCollection();
    private final GrassCollection grassCollection = new GrassCollection();
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d lowerLeftJungle;
    private final Vector2d upperRightJungle;
    private final int initialEnergy;
    private final Set<Vector2d> emptyPlacesJungle = new HashSet<>();
    private final Set<Vector2d> emptyPlacesOutsideJungle = new HashSet<>();

    //private final int energyLoss;


    public RectangularMap(int width, int height, double jungleRatio, int initialEnergy){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width,height);
        this.initialEnergy = initialEnergy;
        int jungleX = (int) (jungleRatio * width);
        int jungleY = (int) (jungleRatio * height);
        this.lowerLeftJungle = new Vector2d( width/2 - jungleX/2,  height/2 - jungleY/2);
        this.upperRightJungle = new Vector2d( width/2 + jungleX/2,  height/2 + jungleY/2);
        for(int i = lowerLeft.x; i < upperRight.x; i++){
            for(int j = lowerLeft.y; j < upperRight.y; j++){
                Vector2d position = new Vector2d(i, j);
                if (this.isInJungle(position)){
                    emptyPlacesJungle.add(position);
                }
                emptyPlacesOutsideJungle.add(position);
            }
        }
        //this.energyLoss = energyLoss;
    }
    public int getInitialEnergy(){ return initialEnergy;}
    public Vector2d getUpperRight(){
        return upperRight;
    }
    public Vector2d getLowerLeft(){
        return lowerLeft;
    }
    public Vector2d getLowerLeftJungle(){return lowerLeftJungle;}
    public Vector2d getUpperRightJungle(){return upperRightJungle;}

//    public boolean isInBounds(Vector2d position) {
//        return lowerLeft.precedes(position) && upperRight.follows(position);
//    }

    // generujemy z przedziału <min, max)
    public Vector2d initializeRandomPositionInBounds(){
        int x = (int) (Math.random() * (this.upperRight.x - this.lowerLeft.x + 1) + this.lowerLeft.x);
        int y = (int) (Math.random() * (this.upperRight.y - this.lowerLeft.y + 1) + this.lowerLeft.y);
        return new Vector2d(x, y);
    }

//    public int getEnergyLoss(){
//        return this.energyLoss;
//    }



    //tutaj trzeba będzie doprecyzować te funkcje aby można było z nich korzystać
    //public Object objectAt(Vector2d position){
    //    return elementsMap.get(position);
    //}
    //public boolean isOccupied(Vector2d position){
    //    return elementsMap.containsKey(position);
    //}

    //tutaj póki co będę potrzebował jeszcze listy trawek dlatego muszę mieć position changed w RectangularMap

//    public void positionChanged(Vector2d oldPosition, Animal animal){
//        animalCollection.positionChangedElement(oldPosition, animal);
//    }

    public void energyChanged(Animal oldAnimal, Animal animal){
        this.animalCollection.getAnimalMap().get(animal.getPosition()).remove(oldAnimal);
        this.animalCollection.getAnimalMap().get(animal.getPosition()).add(animal);
    }

    //tutaj trzeba przekazywać jeszcze starego animala żeby dało się tak usunąć
    public void positionChanged(Animal oldAnimal, Animal animal){
        //System.out.println(animal.getPosition());
        //System.out.println(oldAnimal.getPosition());
        //IMapElement animal = elementsMap.get(oldPosition);
        //System.out.println(oldAnimal.getEnergy());
        //System.out.println(animal.getEnergy());
//        System.out.println(this.animalCollection.getAnimalMap().get(oldAnimal.getPosition()).first().getId());
//        System.out.println(this.animalCollection.getAnimalMap().get(oldAnimal.getPosition()).first().getEnergy());
//        System.out.println(oldAnimal.getId());
//        System.out.println(oldAnimal.getEnergy());
        this.animalCollection.getAnimalMap().get(oldAnimal.getPosition()).remove(oldAnimal);
        //jeśli nie ma zwierzątek na oldposition to usuwamy set
        if(this.animalCollection.getAnimalMap().get(oldAnimal.getPosition()).isEmpty()){
            this.animalCollection.getAnimalMap().remove(oldAnimal.getPosition());
            if(!this.grassCollection.getGrassMap().containsKey(oldAnimal.getPosition())) {
                if(oldAnimal.getPosition().isInArea(lowerLeftJungle, upperRightJungle)) {
                    emptyPlacesJungle.add(oldAnimal.getPosition());
                }
                else{
                    emptyPlacesOutsideJungle.add(oldAnimal.getPosition());
                }
            }
        }
        //teraz dodajemy, jeśli nie ma seta na daną pozycję
        //to trzeba go utworzyć
        Vector2d newPosition = animal.getPosition();
        if(!this.animalCollection.getAnimalMap().containsKey(newPosition)){
            SortedSet<Animal> animalsByEnergy = new TreeSet<>((animal1, animal2) -> animal1.getEnergy() >= animal2.getEnergy() ? 1 : -1);
            this.animalCollection.getAnimalMap().put(newPosition, animalsByEnergy);
        }
        this.animalCollection.getAnimalMap().get(newPosition).add(animal);
    }

    public void addElement(IMapElement element){
        if(element instanceof Grass){
            grassCollection.addGrass(element);
        }
        else {
            animalCollection.addAnimal(element);
            ((Animal) element).addObserver(this);
        }
        if(this.emptyPlacesJungle.contains(element.getPosition())){
            this.emptyPlacesJungle.remove(element.getPosition());
        }
        if(this.emptyPlacesOutsideJungle.contains(element.getPosition())){
            this.emptyPlacesOutsideJungle.remove(element.getPosition());
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
        if(!animalCollection.getAnimalMap().containsKey(actualPosition)
        && !grassCollection.getGrassMap().containsKey(actualPosition)){
            if(actualPosition.isInArea(lowerLeftJungle, upperRightJungle)){
                emptyPlacesJungle.add(actualPosition);
            }
            else{
                emptyPlacesOutsideJungle.add(actualPosition);
            }
        }
    }

    public Vector2d getProperPositionForBabyAnimal(Vector2d parentPosition){
        Random random = new Random(42);
        List <Vector2d> potentialPositions = new LinkedList<>();
        List <Vector2d> potentialOccupiedPositions = new LinkedList<>();
        for(int i =0; i < 8; i++){
            if(grassCollection.getGrassMap().containsKey(parentPosition.add(MapDirection.NORTH.toEnum(i).toUnitVector()))){
                continue;
            }
            if(animalCollection.getAnimalMap().containsKey(parentPosition.add(MapDirection.NORTH.toEnum(i).toUnitVector()))){
                potentialOccupiedPositions.add(parentPosition.add(MapDirection.NORTH.toEnum(i).toUnitVector()));
            }
            potentialPositions.add(parentPosition.add(MapDirection.NORTH.toEnum(i).toUnitVector()));
        }
        if(!potentialPositions.isEmpty()){
            return potentialPositions.get(random.nextInt(potentialPositions.size()));
        }
        return potentialOccupiedPositions.get(random.nextInt(potentialOccupiedPositions.size()));
    }

    public Set<Vector2d> getEmptyPlacesJungle(){
        return this.emptyPlacesJungle;
    }

    public Set<Vector2d> getEmptyPlacesOutsideJungle(){
        return this.emptyPlacesOutsideJungle;
    }

    public List<Animal> getListOfAnimals(){
        return animalCollection.animalsToList();
    }

    public List<Grass> getListOfGrasses(){
        return grassCollection.grassesToList();
    }

    public Map<Vector2d, Grass> getGrassesMap(){
        return this.grassCollection.getGrassMap();
    }

    public Map<Vector2d, SortedSet<Animal>> getAnimalMap(){
        return this.animalCollection.getAnimalMap();
    }

    public GrassCollection getGrassCollection(){
        return this.grassCollection;
    }

    public AnimalCollection getAnimalCollection(){
        return this.animalCollection;
    }

    public boolean isInJungle(Vector2d position){
        if(position.x >= this.lowerLeftJungle.x && position.x <= this.upperRightJungle.x &&
                position.y >= this.lowerLeftJungle.y && position.y <= this.upperRightJungle.y){
            return true;
        }
        return false;
    }


}
