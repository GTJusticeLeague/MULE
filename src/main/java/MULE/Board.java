package MULE;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        int town_row = rand.nextInt(5);
        int town_col = rand.nextInt(9);
        tiles[town_row][town_col] = new Tile(Tile.Terrain.TOWN);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != null) {
                    continue; // We have already placed the town here
                }
                Tile.Terrain curTerrain = null;

                // Select a random terrain for the tile
                int rand_terrain = rand.nextInt(5);
                if (rand_terrain == 0) {
                    curTerrain = Tile.Terrain.RIVER;
                } else if (rand_terrain == 1) {
                    curTerrain = Tile.Terrain.PLAIN;
                } else if (rand_terrain == 2) {
                    curTerrain = Tile.Terrain.ONEMOUNTAIN;
                } else if (rand_terrain == 3) {
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
            String row = scan.next();
            if (row == null) {
                System.err.println("Error! Reached end of .csv before reading in " + tiles.length + " rows");
                System.exit(1);
            }
            List<String> rowList = Arrays.asList(row.split(","));
            if (rowList.size() != tiles[0].length) {
                System.err.println("Error! Row " + i + " length != " + tiles[0].length);
                System.exit(1);
            }
            for (int j = 0; j < tiles[0].length; j++) {
                String col = rowList.get(j);
                Tile.Terrain curTerrain = null;
                if (col.equals("R")) {
                    curTerrain = Tile.Terrain.RIVER;
                } else if (col.equals("P")) {
                    curTerrain = Tile.Terrain.PLAIN;
                } else if (col.equals("M1")) {
                    curTerrain = Tile.Terrain.ONEMOUNTAIN;
                } else if (col.equals("M2")) {
                    curTerrain = Tile.Terrain.TWOMOUNTAIN;
                } else if (col.equals("M3")) {
                    curTerrain = Tile.Terrain.THREEMOUNTAIN;
                } else if (col.equals("Town")) {
                    curTerrain = Tile.Terrain.TOWN;
                } else {
                    System.err.println("Error! Type " + col + " is invalid!");
                    System.exit(1);
                }
                tiles[i][j] = new Tile(curTerrain);
            }
        }
    }

    /**
     * Prints the layout of the map to the console
     * @return map design
     */
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                temp.append(tiles[i][j].getTerrain() + ",");
            }
            temp.append("\n");
        }
        return temp.toString();
    }

    /**
     * Get the array of tiles
     * @return array of tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }
}
