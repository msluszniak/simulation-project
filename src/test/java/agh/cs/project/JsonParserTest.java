package agh.cs.project;

import agh.cs.project.engine.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class JsonParserTest {
    JsonParser parameters = JsonParser.loadParametersFromFile();

    public JsonParserTest() throws FileNotFoundException {
    }

    @Test
    public void checkWidth(){
        Assertions.assertEquals(30, parameters.getWidth());
    }

    @Test
    public void checkHeight(){
        Assertions.assertEquals(30, parameters.getHeight());
    }

    @Test
    public void checkJungleRatio(){
        Assertions.assertEquals(0.2, parameters.getJungleRatio());
    }

    @Test
    public void checkStartEnergy(){
        Assertions.assertEquals(200, parameters.getStartEnergy());
    }

    @Test
    public void checkMoveEnergy(){
        Assertions.assertEquals(1, parameters.getMoveEnergy());
    }

    @Test
    public void checkPlantEnergy(){
        Assertions.assertEquals(100, parameters.getPlantEnergy());
    }

}
