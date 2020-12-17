package agh.cs.project.engine;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.ObjectInputFilter;

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
        JsonParser parameters = gson.fromJson(new FileReader("\\Users\\DELL\\IdeaProjects\\ProjektSymulacjaŚwiata\\input.json"), JsonParser.class);
        return parameters;
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

    public int getStartEnergy(){
        return startEnergy;
    }
    public int getInitialNumberOfAnimals(){return initialNumberOfAnimals;}

}

