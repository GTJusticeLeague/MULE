package MULE;

/**
 * A tile must have a particular terrain associated. It may or may not have an owner or mule.
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
    private Mule mule;

    /**
     * Tile constructor. When a gameboard tile is created it must have a terrain
     *
     * @param terrain The terrain type of the Tile
     */
    public Tile(Terrain terrain) {
        this.terrain = terrain;
        this.owner = null;
        this.mule = null;
    }

    /**
     * Calculate the production of this Tile's mule, if it has one
     */
    public void calculateProduction() {
        if (mule != null) {
            mule.calculateProduction();
        }
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

    public Mule getMule() {
        return mule;
    }

    public void setMule(Mule mule) {
        this.mule = mule;
    }

}
