package MULE;

/**
 * Created by Donald on 9/13/2015.
 * 9/13/2015 - Changed level to difficulty, make variables final, add getters
 */
public class GameConfig {

    public enum Difficulty {
        BEGINNER, INTERMEDIATE, ADVANCED
    }

    public enum MapType {
        STANDARD, RANDOM
    }

    private final Difficulty difficulty;
    private final MapType mapType;
    private final int players;

    public GameConfig(Difficulty difficulty, MapType mapType, int players) {
        this.difficulty = difficulty;
        this.mapType = mapType;
        this.players = players;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public MapType getMapType() {
        return mapType;
    }

    public int getPlayers() {
        return players;
    }
}
