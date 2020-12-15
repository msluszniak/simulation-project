package agh.cs.project;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.map.RectangularMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class AnimalTest {
    RectangularMap map = new RectangularMap(300, 400, 0.2, 5);
    Animal animal = new Animal(map, new Vector2d(100,200), new Genotype(), 100, 0);

    @Test
    public void isAlreadyDeadTestOfAliveAnimal(){
       Assertions.assertFalse(animal.isAlreadyDead(2));
    }

    @Test
    public void isAlreadyDeadTestOfDeadAnimal(){
        Animal deadAnimal = new Animal(map, new Vector2d(100,200), new Genotype(), -5, 0);
        Assertions.assertTrue(deadAnimal.isAlreadyDead(2));
    }

    @Test
    public void isDeathDateUpdated(){
        Animal deadAnimal = new Animal(map, new Vector2d(100,200), new Genotype(), -5, 0);
        deadAnimal.isAlreadyDead(2);
        Assertions.assertEquals(2, deadAnimal.getDeathDate());
    }

    @Test
    public void getEnergyTest(){
        Assertions.assertEquals(100, animal.getEnergy());
    }

    @Test
    public void moveTestIfAnimalChangePosition(){
        Vector2d animalPosition = animal.getPosition();
        animal.move();
        Assertions.assertNotEquals(animalPosition, animal.getPosition());
    }

    @Test
    public void moveTestIfAnimalChangeEnergy(){
        int animalEnergy = animal.getEnergy();
        animal.move();
        Assertions.assertEquals(95, animal.getEnergy());
        Assertions.assertEquals(95, map.getAnimalCollection().getAnimalMap().get(animal.getPosition()).first().getEnergy());
    }

    @Test
    public void moveTestIfAnimalPositionOnMapUpdate(){
        Vector2d oldPosition = animal.getPosition();
        animal.move();
        Assertions.assertFalse(map.getAnimalCollection().getAnimalMap().containsKey(oldPosition));
        Assertions.assertFalse(map.getAnimalCollection().getAnimalMap().get(animal.getPosition()).isEmpty());
    }

}
