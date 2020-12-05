package agh.cs.project.elements;

import agh.cs.project.map.AbstractWorldMap;
import agh.cs.project.engine.IPositionChangeObserver;
import agh.cs.project.basics.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Grass implements IMapElement {
    private final Vector2d position;
    private final List<IPositionChangeObserver> observerList = new ArrayList<>(); // wczesniej nie bylo tego

    public Grass(AbstractWorldMap map, Vector2d initPos){ // wczesniej bylo Iwordlmap
        this.position = initPos;
        this.addObserver(map); //wczesniej nie było tego
    }

    void addObserver(IPositionChangeObserver observer){
        observerList.add(observer);
    } //wczeniej nie bylo tego
    public Vector2d getPosition(){
        return position;
    }

    @Override
    public String toString(){
        return "*";
    }

}