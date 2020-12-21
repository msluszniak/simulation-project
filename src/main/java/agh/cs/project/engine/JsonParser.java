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

    public static JsonParser loadParametersFromFile(String fileName) throws FileNotFoundException, IllegalArgumentException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(fileName), JsonParser.class);
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

    public void checkIfJsonIsValid() throws IllegalArgumentException{
        if(this.width <= 0) {throw new IllegalArgumentException("Szerokosc mapy musi byc dodatnia!");}
        if(this.height <= 0) {throw new IllegalArgumentException("Wysokosc mapy musi byc dodatnia!");}
        if(this.moveEnergy <= 0) {throw new IllegalArgumentException("Animale nie moga zyskiwac energi tylko na chodzeniu");}
        if(this.jungleRatio <= 0) {throw new IllegalArgumentException("Dzungla musi istniec!");}
        if(this.startEnergy <= 0){ throw new IllegalArgumentException("Nie mozna tworzyc zombie animali!");}
        if(this.initialNumberOfAnimals < 0){throw new IllegalArgumentException("Nie moze byc ujemnej ilosci animali na mapie!");}
    }

}

