package agh.cs.project;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.map.RectangularMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangularMapTest {
    RectangularMap map = new RectangularMap(300, 400, 0.2, 100);

    @Test
    public void findJungleBounds(){
        Assertions.assertEquals(new Vector2d(120, 160), map.getLowerLeftJungle());
        Assertions.assertEquals(new Vector2d(180, 240), map.getUpperRightJungle());
    }
}
