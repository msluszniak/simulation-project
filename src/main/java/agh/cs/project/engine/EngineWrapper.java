package agh.cs.project.engine;

public class EngineWrapper implements Runnable {
    private final Engine engine;

    public EngineWrapper(int width, int height, int energyFromGrass, int startEnergy, double jungleRatio, int initialNumberOfAnimals, int energyLoss) {
        this.engine = new Engine(width, height, jungleRatio, startEnergy, initialNumberOfAnimals, energyFromGrass, energyLoss);
        engine.spawnInitialAnimals();
    }

    public Engine getEngine() {
        return engine;
    }

    public void run(){
        engine.step();
    }
}
