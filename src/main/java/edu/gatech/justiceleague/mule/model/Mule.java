package edu.gatech.justiceleague.mule.model;

/**
 * Mule Object has type, terrain its placed on, and owner
 */
public class Mule {
    public enum MULETYPE {
        FOOD,
        ENERGY,
        SMITHORE,
        CRYSTITE,
        NONE
    }

    private MULETYPE type;
    private Tile.Terrain terrain;
    private Player owner;

    /**
     * Constructor for Mules
     * @param type of mule
     * @param owner of mule
     * @param terrain of tile that mule boogies on
     */
    public Mule(MULETYPE type, Player owner, Tile.Terrain terrain) {
        this.type = type;
        this.owner = owner;
        this.terrain = terrain;
    }

    /**
     * @return type of mule
     */
    public final MULETYPE getType() {
        return type;
    }

    /**
     * Calculates and sets production for mule
     * depending on terrain and mule type
     */
    public final void calculateProduction() {
        if (owner.getEnergy() == 0) {
            return; // Not enough energy to produce
        }
        if (this.type == MULETYPE.FOOD) {
            owner.setFood(owner.getFood() + getFoodProduction());
        } else if (this.type == MULETYPE.ENERGY) {
            owner.setEnergy(owner.getEnergy() + getEnergyProduction());
        } else if (this.type == MULETYPE.SMITHORE) {
            owner.setSmithore(owner.getSmithore() + getSmithoreProduction());
        } else if (this.type == MULETYPE.CRYSTITE) {
            owner.setCrystite(owner.getCrystite() + getCrystiteProduction());
        }
        owner.setEnergy(owner.getEnergy() - 1);
    }

    private int getFoodProduction() {
        int production = 0;
        switch (this.terrain) {
            case RIVER:
                production = 4;
                break;
            case PLAIN:
                production = 2;
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
        return production;
    }

    private int getEnergyProduction() {
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
        return production;
    }

    private int getSmithoreProduction() {
        int production = 0;
        switch (this.terrain) {
            case RIVER:
                production = 0;
                break;
            case PLAIN:
                production = 1;
                break;
            case ONEMOUNTAIN:
                production = 2;
                break;
            case TWOMOUNTAIN:
                production = 3;
                break;
            case THREEMOUNTAIN:
                production = 4;
                break;
            default:
                break;
        }
        return production;
    }

    private int getCrystiteProduction() {
        int production = 0;
        switch (this.terrain) {
            case RIVER:
                production = 0;
                break;
            case PLAIN:
                production = 0;
                break;
            case ONEMOUNTAIN:
                production = 0;
                break;
            case TWOMOUNTAIN:
                production = 0;
                break;
            case THREEMOUNTAIN:
                production = 0;
                break;
            default:
                break;
        }
        return production;
    }
}
