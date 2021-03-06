package edu.gatech.justiceleague.mule.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.RejectedExecutionException;

/**
 * A Board is the grid that the game is played on. Consists of a 2D array of tiles.
 */
public class Board {

    private Tile[][] tiles;

    /**
     * Default constructor for a board (build a random map)
     */
    public Board() {
        tiles = new Tile[5][9];
        Random rand = new Random();

        // Place the town
        int townRow = rand.nextInt(5);
        int townCol = rand.nextInt(9);
        tiles[townRow][townCol] = new Tile(Tile.Terrain.TOWN);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != null) {
                    continue; // We have already placed the town here
                }
                Tile.Terrain curTerrain;

                // Select a random terrain for the tile
                int randTerrain = rand.nextInt(5);
                if (randTerrain == 0) {
                    curTerrain = Tile.Terrain.RIVER;
                } else if (randTerrain == 1) {
                    curTerrain = Tile.Terrain.PLAIN;
                } else if (randTerrain == 2) {
                    curTerrain = Tile.Terrain.ONEMOUNTAIN;
                } else if (randTerrain == 3) {
                    curTerrain = Tile.Terrain.TWOMOUNTAIN;
                } else {
                    curTerrain = Tile.Terrain.THREEMOUNTAIN;
                }

                // Create the tile
                tiles[i][j] = new Tile(curTerrain);
            }
        }
    }

    /**
     * Places land images on the board based on map type
     * @param map map
     */
    public Board(String map) {
        tiles = new Tile[5][9];
        Scanner scan = new Scanner(getClass().getClassLoader().getResourceAsStream(map));
        for (int i = 0; i < tiles.length; i++) {
            if (!scan.hasNext()) {
                throw new RejectedExecutionException("Error! Reached end of .csv before reading in " + tiles.length + " rows");
            }
            String row = scan.next();
            List<String> rowList = Arrays.asList(row.split(","));
            if (rowList.size() != tiles[0].length) {
                throw new RejectedExecutionException("Error! Row " + i + " length != " + tiles[0].length);
            }
            for (int j = 0; j < tiles[0].length; j++) {
                String col = rowList.get(j);
                Tile.Terrain curTerrain;
                switch (col) {
                    case "R":
                        curTerrain = Tile.Terrain.RIVER;
                        break;
                    case "P":
                        curTerrain = Tile.Terrain.PLAIN;
                        break;
                    case "M1":
                        curTerrain = Tile.Terrain.ONEMOUNTAIN;
                        break;
                    case "M2":
                        curTerrain = Tile.Terrain.TWOMOUNTAIN;
                        break;
                    case "M3":
                        curTerrain = Tile.Terrain.THREEMOUNTAIN;
                        break;
                    case "Town":
                        curTerrain = Tile.Terrain.TOWN;
                        break;
                    default:
                        throw new RejectedExecutionException("Error! Type " + col + " is invalid!");
                }
                tiles[i][j] = new Tile(curTerrain);
            }
        }
    }

    /**
     * Constructor for Board
     * @param tiles The 2D array of Tiles to make up the board
     */
    public Board(Tile[][] tiles) {
        Tile[][] tempTiles = new Tile[tiles.length][];
        for (int i = 0; i < tempTiles.length; i++) {
            tempTiles[i] = tiles[i].clone();
        }
        this.tiles = tempTiles;
    }

    /**
     * Prints the layout of the map to the console
     * @return map design
     */
    public final String toString() {
        StringBuilder temp = new StringBuilder();
        for (Tile[] tile : tiles) {
            for (int j = 0; j < tiles[0].length; j++) {
                temp.append(tile[j].getTerrain());
                temp.append(",");
            }
            temp.append("\n");
        }
        return temp.toString();
    }

    /**
     * Get the array of tiles
     * @return array of tiles
     */
    public final Tile[][] getTiles() {
        Tile[][] tempTiles = new Tile[tiles.length][];
        for (int i = 0; i < tempTiles.length; i++) {
            tempTiles[i] = tiles[i].clone();
        }
        return tempTiles;
    }
}
