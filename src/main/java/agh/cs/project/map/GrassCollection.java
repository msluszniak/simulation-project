package agh.cs.project.map;

import agh.cs.project.basics.Vector2d;
import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.IMapElement;

import java.util.*;

public class GrassCollection {
    private Map<Vector2d, Grass> grassList = new LinkedHashMap<>();

    public GrassCollection(){}
    public GrassCollection(Map<Vector2d, Grass> grassList){
        this.grassList = grassList;
    }

    public List<Grass> grassesToList() {
        List<Grass> list = new LinkedList<>();
        for (Grass grass : grassList.values()) {
            list.add(grass);
        }
        return list;
    }

    public Map<Vector2d, Grass> getGrassMap(){
        return this.grassList;
    }

    public void addGrass(IMapElement element){
//        if(!grassList.containsKey(element.getPosition())){
//            grassList.put(element.getPosition(), new HashSet<>());
//        }
        grassList.put(element.getPosition(), (Grass) element);
        //grassList.get(element.getPosition()).add((Grass) element);
    }

    public void removeGrass(IMapElement element){
        Vector2d actualPosition = element.getPosition();
        grassList.remove(actualPosition);
        //grassList.get(actualPosition).remove(element);
//        if (grassList.get(actualPosition).isEmpty()) {
//            grassList.remove(actualPosition);
//        }
    }

}
