package MULE;

import java.io.FileNotFoundException;

/**
 * Created by Donald on 9/13/2015.
 * 9/13/2015 - Changed level to difficulty, make variables final, add getters
 */
public class GameConfig {

    /**
     * Game difficulty
     */
    public enum Difficulty {
        /**
         * Beginner difficulty
         */
        BEGINNER,

        /**
         * Intermediate difficulty
         */
        INTERMEDIATE,

        /**
         * Advanced difficulty
         */
        ADVANCED
    }

    /**
     * Type of map for game play
     */
    public enum MapType {
        /**
         * Original map layout
         */
        STANDARD,

        /**
         * Random map layout
         */
        RANDOM
    }

    private final Difficulty difficulty;
    private final Board GAMEBOARD;
    private final MapType mapType;
    public Player[] players;
    private final int numPlayers;


    /**
     * Sets up the game with the user-specified configurations
     * @param difficulty difficulty
     * @param mapType mapType
     * @param numPlayers number of players
     * @throws FileNotFoundException
     */
    public GameConfig(Difficulty difficulty, MapType mapType, int numPlayers) throws FileNotFoundException {
        this.difficulty = difficulty;
        this.mapType = mapType;
        this.numPlayers = numPlayers;
        int MAX_NUM_PLAYERS = 4;
        this.players = new Player[MAX_NUM_PLAYERS];
        this.GAMEBOARD = new Board();
    }

    /**
     * Get the difficulty
     * @return difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Get the map type
     * @return mapType
     */
    public MapType getMapType() {
        return mapType;
    }

    /**
     * Get the array of Players
     * @return array of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Get the number of players
     * @return numPlayers
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Get the game board
     * @return game board
     */
    public Board getGAMEBOARD() { return GAMEBOARD; }
}
