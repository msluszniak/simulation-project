package agh.cs.project.engine;

import agh.cs.project.basics.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}