//package agh.cs.project.map;
//
//import agh.cs.project.engine.IPositionChangeObserver;
//import agh.cs.project.basics.Vector2d;
//import agh.cs.project.elements.Animal;
//import agh.cs.project.elements.Grass;
//import agh.cs.project.elements.IMapElement;
//
//import java.util.*;
//
//public abstract class AbstractWorldMap implements  IPositionChangeObserver {
//
//    protected Map<Vector2d, IMapElement> elementsMap = new LinkedHashMap<>();
//
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
//        IMapElement animal = elementsMap.get(oldPosition);
//        elementsMap.remove(oldPosition);
//        elementsMap.put(newPosition, animal);
//    }
//
//    public Object objectAt(Vector2d position){
//        return elementsMap.get(position);
//    }
//    public boolean isOccupied(Vector2d position){
//        return elementsMap.containsKey(position);
//    }
//
//    @Override
//    public abstract boolean canMoveTo(Vector2d position);
//
//    @Override
//    public boolean place(IMapElement element) {
//        if (element instanceof Animal && objectAt(element.getPosition()) instanceof Animal){
//            throw new IllegalArgumentException("false");
//        }
//        if (!canMoveTo(element.getPosition()) || element instanceof Grass && objectAt(element.getPosition()) instanceof Grass) {
//            return false;
//        }
//        elementsMap.put(element.getPosition(), element);
//        return true;
//    }
//
//    @Override
//    public abstract Vector2d getLowerLeft();
//
//    @Override
//    public abstract Vector2d getUpperRight();
//
//}
