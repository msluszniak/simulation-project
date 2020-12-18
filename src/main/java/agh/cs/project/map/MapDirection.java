package agh.cs.project.map;

import agh.cs.project.basics.Vector2d;

public enum MapDirection {
    NORTH, NORTHEAST, SOUTH, SOUTHEAST, WEST, SOUTHWEST, EAST, NORTHWEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }
    public MapDirection toEnum(int x) {
        return switch (x) {
            case 0-> NORTH;
            case 1-> NORTHEAST;
            case 2-> EAST;
            case 3-> SOUTHEAST;
            case 4-> SOUTH;
            case 5-> SOUTHWEST;
            case 6-> WEST;
            case 7-> NORTHWEST;
            default -> null;
        };
    }


    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }
}
