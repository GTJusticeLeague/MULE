package MULE;

import java.io.FileNotFoundException;

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
    private final Board GAMEBOARD;
    private final MapType mapType;
    public Player[] players;
    private final int numPlayers;
    private final int MAX_NUM_PLAYERS = 4;


    public GameConfig(Difficulty difficulty, MapType mapType, int numPlayers) throws FileNotFoundException {
        this.difficulty = difficulty;
        this.mapType = mapType;
        this.numPlayers = numPlayers;
        this.players = new Player[MAX_NUM_PLAYERS];
        this.GAMEBOARD = new Board();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public MapType getMapType() {
        return mapType;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Board getGAMEBOARD() { return GAMEBOARD; }
}
