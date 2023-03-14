package model;

public enum Direction {
    DOWN(0, 1), LEFT(-1, 0), UP(0, -1), RIGHT(1, 0);
    
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
    public final int x, y;
    
    public static Direction opposite(Direction direction)
    {
        return Direction.values()[(direction.ordinal() + 2) % 4];
    }
}
