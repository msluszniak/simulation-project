package agh.cs.project.engine;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonParser {

    private int width;
    private int height;
    private double jungleRatio;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private int initialNumberOfAnimals;

    public static JsonParser loadParametersFromFile() throws FileNotFoundException, IllegalArgumentException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader("\\Users\\DELL\\IdeaProjects\\ProjektSymulacjaŚwiata\\input.json"), JsonParser.class);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getInitialNumberOfAnimals() {
        return initialNumberOfAnimals;
    }

}

