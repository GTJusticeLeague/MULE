package edu.gatech.justiceleague.mule.model;

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
    public final void calculateProduction() {
        if (mule != null) {
            mule.calculateProduction();
        }
    }

    /**
     * @return terrain of tile
     */
    public final Terrain getTerrain() {
        return terrain;
    }

    /**
     * @return owner of tile
     */
    public final Player getOwner() {
        return owner;
    }

    /**
     * Set owner of tile
     * @param owner of tile
     */
    public final void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * @return mule on tile
     */
    public final Mule getMule() {
        return mule;
    }

    /**
     * Sets mule on tile
     * @param mule on tile
     */
    public final void setMule(Mule mule) {
        this.mule = mule;
    }

    @Override
    public final String toString() {
        return "Tile{"
                + "terrain=" + terrain
                + ", owner=" + owner
                + ", mule=" + mule
                + '}';
    }
}
