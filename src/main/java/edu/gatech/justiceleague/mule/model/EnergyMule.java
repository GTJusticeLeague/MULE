package edu.gatech.justiceleague.mule.model;

/**
 * An Energy Mule is a Mule that produces energy
 */
public class EnergyMule implements Mule {
    private Tile.Terrain terrain;
    private Player owner;

    /**
     * Constructor for an Energy Mule
     *
     * @param terrain Terrain the mule is on
     * @param owner   Owner of the Mule
     */
    public EnergyMule(Tile.Terrain terrain, Player owner) {
        this.terrain = terrain;
        this.owner = owner;
    }

    /**
     * Calculate production for the mule, and update the fields of its owner as needed
     */
    public void calculateProduction() {
        if (owner.getEnergy() == 0) {
            return; // Not enough energy to produce
        }
        int production = 0;
        switch (this.terrain) {
            case RIVER:
                production = 2;
                break;
            case PLAIN:
                production = 3;
                break;
            case ONEMOUNTAIN:
                production = 1;
                break;
            case TWOMOUNTAIN:
                production = 1;
                break;
            case THREEMOUNTAIN:
                production = 1;
                break;
            default:
                break;
        }
        owner.setEnergy(owner.getEnergy() + production);
        owner.setEnergy(owner.getEnergy() - 1);
    }

    /**
     * Provide the caller with the type of this Mule. This is needed to determine what picture to display for a tile
     *
     * @return The type of the Mule
     */
    public String getType() {
        return "ENERGY";
    }
}
