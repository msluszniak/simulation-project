package agh.cs.project.map;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.IMapElement;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GrassCollection {
    private final Map<Vector2d, Grass> collection = new LinkedHashMap<>();

    public List<Grass> grassesToList() {
        return new LinkedList<>(collection.values());
    }

    public Map<Vector2d, Grass> getGrassMap(){
        return this.collection;
    }

    public void addGrass(IMapElement element){
        collection.put(element.getPosition(), (Grass) element);
    }

    public void removeGrass(IMapElement element){
        Vector2d actualPosition = element.getPosition();
        collection.remove(actualPosition);
    }

}
