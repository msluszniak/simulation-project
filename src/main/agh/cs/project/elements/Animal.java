package agh.cs.project.elements;

import agh.cs.project.basics.MoveDirection;
import agh.cs.project.basics.Vector2d;
import agh.cs.project.engine.IPositionChangeObserver;
import agh.cs.project.map.AbstractWorldMap;
import agh.cs.project.map.MapDirection;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private final AbstractWorldMap map;
    private final List<IPositionChangeObserver> observerList = new ArrayList<>();

    public Animal(AbstractWorldMap map, Vector2d initPos){
        this.map = map;
        this.position = initPos;
        this.addObserver(map);
        map.place(this);
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        System.out.println(observerList.size());
        for (IPositionChangeObserver observer: observerList) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }


    public Animal(AbstractWorldMap map){
        this(map, new Vector2d(2, 2));
    }

    void addObserver(IPositionChangeObserver observer){
        observerList.add(observer);
    }
    void removeObserver(IPositionChangeObserver observer){
        for (int i = 0; i < observerList.size(); i++){
            if(observerList.get(i).equals(observer)){
                observerList.remove(i);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return orientation.toString();
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition;
        switch (direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                newPosition = position.add(orientation.toUnitVector());
                if (map.canMoveTo(newPosition)) {
                    if (map.objectAt(newPosition) instanceof Grass){
                        ((GrassField) map).generateGrass();
                    }
                    Vector2d oldPosition = position;
                    position = newPosition;
                    this.positionChanged(oldPosition,newPosition);
                    if(map instanceof GrassField){
                        ((GrassField) map).boundary.positionChanged(oldPosition, newPosition);
                    }
                }
                break;
            case BACKWARD:
                newPosition = position.subtract(orientation.toUnitVector());
                if (map.canMoveTo(newPosition)) {
                    if (map.objectAt(newPosition) instanceof Grass){
                        ((GrassField) map).generateGrass();
                    }
                    Vector2d oldPosition = position;
                    position = newPosition;
                    this.positionChanged(oldPosition,newPosition);
                    if(map instanceof GrassField){
                        ((GrassField) map).boundary.positionChanged(oldPosition, newPosition);
                    }
                }
                break;
        }
    }
}
