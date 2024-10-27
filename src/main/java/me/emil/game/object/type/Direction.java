package me.emil.game.object.type;

public enum Direction
{
    UP(1, 0.5D * Math.PI),
    DOWN(3, 1.5D * Math.PI),
    LEFT(0, Math.PI),
    RIGHT(2, 0);
    
    private final int id;
    private final double rotation;
    
    Direction(int id, double rotation)
    {
        this.id = id;
        this.rotation = rotation;
    }
    
    public int getId()
    {
        return id;
    }
    
    public Direction rotateTo(Direction toRotateTo) {
        return getRotation(this, toRotateTo);
    }
    
    public Direction getOpponent() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }
        
        return null;
    }
    
    public static Direction getDirection(double angle) {
        for(Direction direction : Direction.values()) {
            
            while(angle < 0)
                angle += 2D * Math.PI;
            
            if(direction.rotation == angle)
                return direction;
        }
        
        return null;
    }
    
    public static Direction getRotation(Direction lookingAt, Direction toRotateTo) {
        if(lookingAt == toRotateTo)
            return null;
        
        double difference = toRotateTo.rotation - lookingAt.rotation;
        return getDirection(difference);
    }
}
