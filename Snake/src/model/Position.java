package model;

public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }    
    
    public Position(Position p)
    {
        x = p.x;
        y = p.y;
    }
    
    public Position translate(Direction d){
        return new Position(x + d.x, y + d.y);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
            return true;
        
        if(o == null)
            return false;
        
        if(!(o instanceof Position))
            return false;
        
        Position p = (Position) o;
        
        return p.x == x && p.y == y;
    }
}
