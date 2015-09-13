package MULE;

/**
 * Created by Donald on 9/13/2015.
 */
public class GameConfig {
    Level level;
    MapType mapType;
    int players;

    public GameConfig(Level level, MapType mapType, int players) {
        this.level = level;
        this.mapType = mapType;
        this.players = players;
    }
}
