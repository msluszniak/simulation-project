package agh.cs.project.widget;

import agh.cs.project.elements.Animal;

public interface IObserver {
    void startObserving(Animal animal);
    void stopObserving(Animal animal);
}
