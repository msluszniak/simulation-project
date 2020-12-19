package agh.cs.project.engine;

import agh.cs.project.widget.IObserver;

public interface IPositionChangeObserver extends IObserver {
    void positionChanged(int date);
}