package agh.cs.project.elements;

import agh.cs.project.basics.Vector2d;

public class Grass implements IMapElement {
    private final Vector2d position;

    public Grass(Vector2d initPos) {
        this.position = initPos;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }

}