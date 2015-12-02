package edu.gatech.justiceleague.mule.model;

/**
 * This MuleFactory builds different types of Mule's based on a String provided
 */
public class MuleFactory {
    /**
     * Get a new Mule based on the type provided
     *
     * @param type    Type of the Mule
     * @param owner   Owner for the Mule
     * @param terrain Terrain of the tile the Mule is on
     * @return
     */
    public static Mule getMule(String type, Player owner, Tile.Terrain terrain) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("FOOD")) {
            return new FoodMule(terrain, owner);
        }
        if (type.equalsIgnoreCase("ENERGY")) {
            return new EnergyMule(terrain, owner);
        }
        if (type.equalsIgnoreCase("SMITHORE")) {
            return new SmithoreMule(terrain, owner);
        }
        if (type.equalsIgnoreCase("CRYSTITE")) {
            return new CrystiteMule(terrain, owner);
        }
        return null;
    }
}
