package agh.cs.project.elements;

import agh.cs.project.widget.IObserver;

import java.util.List;

public interface IAnimalObserver extends IObserver {
    int getNumberOfChildren();
    int getNumberOfDescendants();
    void update();
    Animal getTrackedAnimal();
}
