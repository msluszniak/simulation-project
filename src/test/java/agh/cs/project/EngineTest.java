package agh.cs.project;

import agh.cs.project.basics.Genotype;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.engine.Engine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EngineTest {
    Engine engine = new Engine(300, 400, 0.2, 100, 0, 20, 5);
    Animal animal = new Animal(engine.getMap(), new Vector2d(150,200), new Genotype(), 100, 1);

    @Test
    public void isPositionFreeAfterEating(){
        animal.getMap().addElement(animal);
        engine.getMap().addElement(new Grass(new Vector2d(150, 200)));
        Assertions.assertFalse(engine.getMap().getEmptyPlacesJungle().contains(new Vector2d(150, 200)));
        engine.grassEating();
        Assertions.assertEquals(120, animal.getEnergy());
        Assertions.assertFalse(engine.getMap().getEmptyPlacesJungle().contains(new Vector2d(150, 200)));
        animal.move(5);
        Assertions.assertFalse(engine.getMap().getGrassesMap().containsKey(new Vector2d(150,200)));
        Assertions.assertTrue(engine.getMap().getEmptyPlacesJungle().contains(new Vector2d(150, 200)));
    }
}
