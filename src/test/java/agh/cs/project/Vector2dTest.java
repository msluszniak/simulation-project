package agh.cs.project;

import agh.cs.project.basics.Vector2d;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Vector2dTest {

    @Test
    public void positionInGivenArea(){
        Vector2d position = new Vector2d(15, 18);
        Assertions.assertTrue(position.isInArea(new Vector2d(0,0), new Vector2d(30,30)));
    }

    @Test
    public void positionNotInGivenArea(){
        Vector2d position = new Vector2d(15, 18);
        Assertions.assertFalse(position.isInArea(new Vector2d(0,0), new Vector2d(15,15)));
    }

    @Test
    public void positionInCornerInGivenArea(){
        Vector2d position = new Vector2d(15, 15);
        Assertions.assertTrue(position.isInArea(new Vector2d(0,0), new Vector2d(15,15)));
    }

    @Test
    public void correctPositionOfVectorInGivenArea(){
        Vector2d position = new Vector2d(15, 18);
        Assertions.assertEquals(new Vector2d(15,18), position.getCorrectPosition(new Vector2d(0,0), new Vector2d(30,30)));
    }

    @Test
    public void correctPositionOfVectorOutsideGivenArea(){
        Vector2d position = new Vector2d(13, 16);
        Assertions.assertEquals(new Vector2d(13,0), position.getCorrectPosition(new Vector2d(0,0), new Vector2d(15,15)));
    }

    @Test
    public void correctPositionOfVectorInCornerOfArea(){
        Vector2d position = new Vector2d(15,15);
        Assertions.assertEquals(new Vector2d(15,15),position.getCorrectPosition(new Vector2d(0,0), new Vector2d(15,15)));
    }

    @Test
    public void correctPositionOfVectorInRightUpperCornerOutsideOfArea(){
        Vector2d position = new Vector2d(16,16);
        Assertions.assertEquals(new Vector2d(0,0),position.getCorrectPosition(new Vector2d(0,0), new Vector2d(15,15)));
    }

    @Test
    public void correctPositionOfVectorInLeftUpperCornerOutsideOfArea(){
        Vector2d position = new Vector2d(-1, 16);
        Assertions.assertEquals(new Vector2d(15, 0), position.getCorrectPosition(new Vector2d(0,0), new Vector2d(15,15)));
    }


}
