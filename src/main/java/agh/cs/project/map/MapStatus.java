package agh.cs.project.map;

import agh.cs.project.elements.Animal;
import agh.cs.project.engine.Engine;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class MapStatus {

    private final Engine engine;
    private int makeTxtSummaryAtDay = -1;

    private static final List<Integer> averageNumberOfAliveAnimalsAfterGivenNumberOfEpochs = new LinkedList<>();
    private static final List<Integer> averageNumberOfGrassAfterGivenNumberOfEpochs = new LinkedList<>();
    private static final List<Double> averageEnergyAfterGivenNumberOfEpochs = new LinkedList<>();
    private static final List<Double> averageLifespanAfterGivenNumberOfEpochs = new LinkedList<>();
    private static final List<Double> averageNumberOfBabiesAfterGivenNumberOfEpochs = new LinkedList<>();
    private static final Map<List<Integer>, Set<Animal>> dominantGenotypeAfterGivenNumberOfEpochs = new HashMap<>();


    public void setMakeTxtSummaryAtDay(int i) {
        this.makeTxtSummaryAtDay = i;
    }

    public int getMakeTxtSummaryAtDay() {
        return this.makeTxtSummaryAtDay;
    }

    public MapStatus(Engine engine) {
        this.engine = engine;
    }

    public int numberOfAliveAnimals() {
        return numberOfAliveAnimals(true);
    }

    public int numberOfAliveAnimals(boolean flag) {
        if (flag) {
            averageNumberOfAliveAnimalsAfterGivenNumberOfEpochs.add(this.engine.getMap().getListOfAnimals().size());
        }
        return this.engine.getMap().getListOfAnimals().size();
    }

    public int numberOfGrass(boolean flag) {
        if (flag) {
            averageNumberOfGrassAfterGivenNumberOfEpochs.add(this.engine.getMap().getListOfGrasses().size());
        }
        return this.engine.getMap().getListOfGrasses().size();
    }

    public int numberOfGrass() {
        return numberOfGrass(true);
    }


    public double averageEnergy(boolean flag) {
        List<Animal> animalList = this.engine.getMap().getListOfAnimals();
        int cumulativeEnergy = 0;
        for (Animal animal : animalList) {
            cumulativeEnergy += animal.getEnergy();
        }
        if (flag && this.numberOfAliveAnimals() != 0) {
            averageEnergyAfterGivenNumberOfEpochs.add(cumulativeEnergy / ((double) this.numberOfAliveAnimals()));
        }
        if (flag && this.numberOfAliveAnimals() == 0) {
            averageEnergyAfterGivenNumberOfEpochs.add(0.0);
        }
        return cumulativeEnergy / ((double) this.numberOfAliveAnimals());
    }

    public double averageEnergy() {
        return averageEnergy(true);
    }

    public double averageNumberOfBabies(boolean flag) {
        List<Animal> animalList = this.engine.getMap().getListOfAnimals();
        int cumulativeNumberOfBabies = 0;
        for (Animal animal : animalList) {
            cumulativeNumberOfBabies += animal.getChildren().size();
        }
        if (flag && this.numberOfAliveAnimals() != 0) {
            averageNumberOfBabiesAfterGivenNumberOfEpochs.add(cumulativeNumberOfBabies / ((double) this.numberOfAliveAnimals()));
        }

        if (flag && this.numberOfAliveAnimals() == 0) {
            averageNumberOfBabiesAfterGivenNumberOfEpochs.add(0.0);
        }
        return cumulativeNumberOfBabies / ((double) this.numberOfAliveAnimals());
    }

    public double averageNumberOfBabies() {
        return averageNumberOfBabies(true);
    }

    public double averageLifespan(boolean flag) {
        if (flag && this.engine.getNumberOfDeadAnimals() != 0) {
            averageLifespanAfterGivenNumberOfEpochs.add(this.engine.getCumulativeDeadAnimalsDays() / ((double) this.engine.getNumberOfDeadAnimals()));
        }
        if (this.engine.getNumberOfDeadAnimals() != 0) {
            return this.engine.getCumulativeDeadAnimalsDays() / ((double) this.engine.getNumberOfDeadAnimals());
        }
        else{
            return 0.0;
        }
    }

    public double averageLifespan() {
        return averageLifespan(true);
    }

    public Map.Entry<List<Integer>, Set<Animal>> dominantGenotype(boolean flag) {
        List<Animal> animals = this.engine.getMap().getListOfAnimals();
        Map<List<Integer>, Set<Animal>> resultMapping = new HashMap<>();
        for (Animal animal : animals) {
            if (!resultMapping.containsKey(animal.getGenotype())) {
                resultMapping.put(animal.getGenotype(), new HashSet<>(Set.of(animal)));
            }
            if (resultMapping.containsKey(animal.getGenotype())) {
                resultMapping.get(animal.getGenotype()).add(animal);
            }
            if (flag) {
                if (!dominantGenotypeAfterGivenNumberOfEpochs.containsKey(animal.getGenotype())) {
                    dominantGenotypeAfterGivenNumberOfEpochs.put(animal.getGenotype(), new HashSet<>(Set.of(animal)));
                }
                if (dominantGenotypeAfterGivenNumberOfEpochs.containsKey(animal.getGenotype())) {
                    dominantGenotypeAfterGivenNumberOfEpochs.get(animal.getGenotype()).add(animal);
                }
            }

        }
        int max_size = 0;
        Map.Entry<List<Integer>, Set<Animal>> dominantGenotype = null;
        for (Map.Entry<List<Integer>, Set<Animal>> pair : resultMapping.entrySet()) {
            if (max_size < pair.getValue().size()) {
                max_size = pair.getValue().size();
                dominantGenotype = pair;
            }
        }
        return dominantGenotype;
    }

    public Map.Entry<List<Integer>, Set<Animal>> dominantGenotype() {
        return dominantGenotype(true);
    }

    public void makeFullLoopOfStatistics() {
        this.dominantGenotype();
        this.averageNumberOfBabies();
        this.averageLifespan();
        this.numberOfAliveAnimals();
        this.numberOfGrass();
        this.averageEnergy();
    }

    public double averageLifespanAfterGivenNumberOfEpochs() {
        Double cumulativeSum = 0.0;
        for (Double num : averageLifespanAfterGivenNumberOfEpochs) {
            cumulativeSum += num;
        }
        return cumulativeSum / averageLifespanAfterGivenNumberOfEpochs.size();
    }

    public double averageNumberOfAliveAnimalsAfterGivenNumberOfEpochs() {
        Double cumulativeSum = 0.0;
        for (Integer num : averageNumberOfAliveAnimalsAfterGivenNumberOfEpochs) {
            cumulativeSum += num;
        }
        return cumulativeSum / ((double) averageNumberOfAliveAnimalsAfterGivenNumberOfEpochs.size());
    }

    public double averageNumberOfGrassAfterGivenNumberOfEpochs() {
        Double cumulativeSum = 0.0;
        for (Integer num : averageNumberOfGrassAfterGivenNumberOfEpochs) {
            cumulativeSum += num;
        }
        return cumulativeSum / ((double) averageNumberOfGrassAfterGivenNumberOfEpochs.size());
    }

    public double averageEnergyAfterGivenNumberOfEpochs() {
        Double cumulativeSum = 0.0;
        for (Double num : averageEnergyAfterGivenNumberOfEpochs) {
            cumulativeSum += num;
        }
        return cumulativeSum / ((double) averageEnergyAfterGivenNumberOfEpochs.size());
    }

    public double averageNumberOfBabiesAfterGivenNumberOfEpochs() {
        Double cumulativeSum = 0.0;
        for (Double num : averageNumberOfBabiesAfterGivenNumberOfEpochs) {
            cumulativeSum += num;
        }
        return cumulativeSum / ((double) averageNumberOfBabiesAfterGivenNumberOfEpochs.size());
    }

    public List<Integer> dominantGenotypeAfterGivenNumberOfEpochs() {
        int max_size = 0;
        Map.Entry<List<Integer>, Set<Animal>> dominantGenotype = null;
        for (Map.Entry<List<Integer>, Set<Animal>> pair : dominantGenotypeAfterGivenNumberOfEpochs.entrySet()) {
            if (max_size < pair.getValue().size()) {
                max_size = pair.getValue().size();
                dominantGenotype = pair;
            }
        }
        return dominantGenotype.getKey();
    }

    public void makeTxt(String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println("srednia dlugosc zycia to: " + averageLifespanAfterGivenNumberOfEpochs());
            out.println("srednia energia to: " + averageEnergyAfterGivenNumberOfEpochs());
            out.println("srednia ilosc dzieci to to: " + averageNumberOfBabiesAfterGivenNumberOfEpochs());
            out.println("srednia ilosc trawy to: " + averageNumberOfGrassAfterGivenNumberOfEpochs());
            out.println("srednia ilosc zwierzat to: " + averageNumberOfAliveAnimalsAfterGivenNumberOfEpochs());
            out.println("dominujacy genotyp przez n epok to: " + dominantGenotypeAfterGivenNumberOfEpochs());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Engine getEngine() {
        return engine;
    }
}
