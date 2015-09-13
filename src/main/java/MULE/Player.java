package MULE;

/**
 * Created by Donald on 9/13/2015.
 * 9/13/2015 - Made Race/Color enums, created getters, made variables private
 */
public class Player {

    public enum Race {
        HUMAN,
        FLAPPER,
        BONZOID,
        UGAITE,
        BUZZITE
    }

    public enum Color {
        RED,
        GREEN,
        BLUE,
        YELLOW,
        PURPLE
    }

    private String name;
    private Race race;
    private Color color;

    public Player (String name, Race race, Color color) {
            this.name = name;
            this.race = race;
            this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Race getRace() {
        return race;
    }
}
