package agh.cs.project.elements;

import agh.cs.project.map.AbstractWorldMap;
import agh.cs.project.engine.IPositionChangeObserver;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.map.RectangularMap;

import java.util.ArrayList;
import java.util.List;

public class Grass implements IMapElement {
    private final Vector2d position;

    public Grass(RectangularMap map, Vector2d initPos){ // wczesniej bylo Iwordlmap
        this.position = initPos;
    }

    public Vector2d getPosition(){
        return position;
    }

//    public void die(){
//
//    }
    @Override
    public String toString(){
        return "*";
    }

}