package edu.gatech.justiceleague.mule.model;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Created by Donald on 9/13/2015.
 * 9/13/2015 - Changed level to difficulty, make variables final, add getters
 */
public class GameConfig {

    public void setPlayers(Player[] players) {
        this.players = players;
    }

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
    private final Board gameBoard;
    private final MapType mapType;
    private final Store store;
    private final int numPlayers;
    private Player[] players;


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
        int maxNumPlayers = 4;
        this.players = new Player[maxNumPlayers];
        if (mapType == MapType.RANDOM) {
            this.gameBoard = new Board();
        } else {
            this.gameBoard = new Board("defaultMap.csv");
        }
        this.store = new Store(difficulty);
    }

    public GameConfig(Difficulty difficulty, MapType mapType, int numPlayers, Store store, Board gameBoard, Player[] players) {
        this.difficulty = difficulty;
        this.mapType = mapType;
        this.numPlayers = numPlayers;
        this.store = store;
        this.gameBoard = gameBoard;
        this.players = players;
    }

    /**
     * Get the difficulty
     * @return difficulty
     */
    public final Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Get the map type
     * @return mapType
     */
    public final MapType getMapType() {
        return mapType;
    }

    /**
     * Get the array of Players
     * @return array of players
     */
    public final Player[] getPlayers() {
        Player[] playersToGet = new Player[this.players.length];
        System.arraycopy(this.players, 0, playersToGet, 0, this.players.length);
        return playersToGet;
    }

    /**
     * Get the number of players
     * @return numPlayers
     */
    public final int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Get the game board
     * @return game board
     */
    public final Board getGameBoard() {
        return gameBoard;
    }

    /**
     * @return Store for this game
     */
    public final Store getStore() {
        return store;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "difficulty=" + difficulty +
                ", gameBoard=" + gameBoard +
                ", mapType=" + mapType +
                ", store=" + store +
                ", numPlayers=" + numPlayers +
                ", players=" + Arrays.toString(players) +
                '}';
    }
}
