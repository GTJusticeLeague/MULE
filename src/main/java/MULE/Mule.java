package MULE;

/**
 * Created by thomas on 10/6/15.
 */
public class Mule {
    public enum MULETYPE {
        FOOD,
        ENERGY,
        ORE,
        CRYSTITE,
        NONE
    }

    private MULETYPE type;
    private Tile.Terrain terrain;
    private Player owner;

    Mule(MULETYPE type, Player owner, Tile.Terrain terrain) {
        this.type = type;
        this.owner = owner;
        this.terrain = terrain;
    }

    public void calculateProduction() {
        if (owner.getEnergy() == 0) {
            return; // Not enough energy to produce
        }
        int production = 0;
        if (this.type == MULETYPE.FOOD) {
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
            }
            owner.setFood(owner.getFood() + production);
        } else if (this.type == MULETYPE.ENERGY) {
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
            }
            owner.setEnergy(owner.getEnergy() + production);
        } else if (this.type == MULETYPE.ORE) {
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
            }
            owner.setSmithore(owner.getSmithore() + production);
        } else if (this.type == MULETYPE.CRYSTITE) {
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
            }
            owner.setCrystite(owner.getCrystite() + production);
        }
        owner.setEnergy(owner.getEnergy() - 1);
    }
}
