package agh.cs.project;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Grass;
import agh.cs.project.map.RectangularMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Random;

public class GrassTest {
    RectangularMap map = new RectangularMap(300, 400, 0.2, 100);

    @Test
    public void ifGrassIsAddedToMap(){
        LinkedList<Vector2d> emptyPlacesOutsideJungle = new LinkedList<>(map.getEmptyPlacesOutsideJungle());
        Random random = new Random(42);
        Vector2d randomPosition = emptyPlacesOutsideJungle.get(random.nextInt(emptyPlacesOutsideJungle.size()));
        Grass grass = new Grass(map, randomPosition);
        map.addElement(grass);

        Assertions.assertEquals(grass, map.getGrassesMap().get(randomPosition));
        Assertions.assertEquals(1, map.getListOfGrasses().size());

    }
}
