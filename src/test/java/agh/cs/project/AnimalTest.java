package agh.cs.project;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.map.RectangularMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    RectangularMap map = new RectangularMap(300, 400, 0.2, 100);
    Animal animal = new Animal(map, new Vector2d(100,200), new Genotype(), 100, 0);

    @Test
    public void isAlreadyDeadTestOfAliveAnimal(){
        animal.getMap().addElement(animal);
        Assertions.assertFalse(animal.isAlreadyDead(2));
    }

    @Test
    public void isAlreadyDeadTestOfDeadAnimal(){
        animal.getMap().addElement(animal);
        Animal deadAnimal = new Animal(map, new Vector2d(100,200), new Genotype(), -5, 0);
        animal.getMap().addElement(deadAnimal);
        Assertions.assertTrue(deadAnimal.isAlreadyDead(2));
    }

    @Test
    public void isDeathDateUpdated(){
        animal.getMap().addElement(animal);
        Animal deadAnimal = new Animal(map, new Vector2d(100,200), new Genotype(), -5, 0);
        animal.getMap().addElement(deadAnimal);
        deadAnimal.isAlreadyDead(2);
        Assertions.assertEquals(2, deadAnimal.getDeathDate());
    }

    @Test
    public void getEnergyTest(){
        Assertions.assertEquals(100, animal.getEnergy());
    }

    @Test
    public void moveTestIfAnimalChangePosition(){
        animal.getMap().addElement(animal);
        Vector2d animalPosition = animal.getPosition();
        animal.move(5);
        Assertions.assertNotEquals(animalPosition, animal.getPosition());
    }

    @Test
    public void moveTestIfAnimalChangeEnergy(){
        animal.getMap().addElement(animal);
        animal.move(5);
        Assertions.assertEquals(95, animal.getEnergy());
        Assertions.assertEquals(95, map.getAnimalCollection().getAnimalMap().get(animal.getPosition()).first().getEnergy());
    }

    @Test
    public void moveTestIfAnimalPositionOnMapUpdate(){
        animal.getMap().addElement(animal);
        Vector2d oldPosition = animal.getPosition();
        animal.move(5);
        Assertions.assertFalse(map.getAnimalCollection().getAnimalMap().containsKey(oldPosition));
        Assertions.assertFalse(map.getAnimalCollection().getAnimalMap().get(animal.getPosition()).isEmpty());
    }


    @Test
    public void reproduceAnimalTest(){
        animal.getMap().addElement(animal);
        Animal newAnimal = new Animal(map, new Vector2d(100,200), new Genotype(), 120, 0);
        animal.getMap().addElement(newAnimal);
        Animal kid = animal.reproduce(newAnimal, 5);
        animal.getMap().addElement(kid);
        for(int i = 0; i < animal.getMap().getListOfAnimals().size(); i++)
            System.out.println(animal.getMap().getListOfAnimals().get(i).getId());
        Assertions.assertEquals(3, map.getListOfAnimals().size());
        Vector2d position = animal.getPosition();
        animal.move(5);
        Assertions.assertNotEquals(position, animal.getPosition());
    }

}
