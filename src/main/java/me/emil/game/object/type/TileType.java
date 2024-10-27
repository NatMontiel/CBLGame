package me.emil.game.object.type;

public enum TileType
{
    WATER(0), GRASS(1), ROAD(2);
    
    private final int id;
    
    TileType(int id)
    {
        this.id = id;
    }
    
    public static TileType get(int id) {
        for(TileType tileType : TileType.values()) {
            if(tileType.id == id)
                return tileType;
        }
        
        return null;
    }
    
    public int getId()
    {
        return id;
    }
}
