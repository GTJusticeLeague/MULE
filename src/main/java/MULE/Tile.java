package MULE;

/**
 * A tile must have a particular terrain associated. It may or may not have an owner.
 */
public class Tile {

    public enum Terrain {
        RIVER,
        PLAIN,
        ONEMOUNTAIN,
        TWOMOUNTAIN,
        THREEMOUNTAIN,
        TOWN
    }

    private Terrain terrain;
    private Player owner;
    private boolean hasMule = false;

    // Tile constructor (every tile needs a terrain)
    public Tile(Terrain terrain) {
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean hasMule() {
        return hasMule;
    }

    public void setHasMule(boolean hasMule) {
        this.hasMule = hasMule;
    }
}
