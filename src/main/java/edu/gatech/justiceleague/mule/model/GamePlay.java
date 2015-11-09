package edu.gatech.justiceleague.mule.model;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import edu.gatech.justiceleague.mule.controller.GameScreenController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


/**
 * Contains the main game logic
 */
public class GamePlay {
    public static GameConfig gameConfig;
    public static int round = 0;
    public static Player currentPlayer;
    public static transient Queue<Player> playerOrder = new PriorityQueue<>();
    public static int turnSeconds = 0;

    /**
     * Start the regular gameplay. Should be called at the end of land selection phase
     */
    public static void startGame() {
        nextPlayer(); // increments round number and initializes queue if empty
    }

    /**
     * End the current players turn by beginning the turn of the next player
     */
    public static void nextPlayer() {
        Player nextPlayer = playerOrder.peek();
        if (nextPlayer == null) {
            // We have gone through all players in the queue, round is over
            nextRound();
        }
        currentPlayer = playerOrder.poll();

        //27% chance of random event occurring
        if (Math.random() * 100 > 73) {
            randomEvent();
        }

        currentPlayer.initTimer();
        currentPlayer.startTime();
    }

    /**
     * Initiate random event
     */
    private static void randomEvent() {
        Random r = new Random();
        int event = r.nextInt(7) + 1;
        Label eventLabel = null;
        switch (event) {
            case 1:
                currentPlayer.setFood(currentPlayer.getFood() + 3);
                currentPlayer.setEnergy(currentPlayer.getFood() + 2);
                eventLabel = new Label("YOU JUST  RECEIVED A PACKAGE FROM THE GT ALUMNI "
                        + "CONTAINING 3 FOOD AND 2 ENERGY UNITS");
                break;
            case 2:
                currentPlayer.setSmithore(currentPlayer.getSmithore() + 2);
                eventLabel = new Label("A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.");
                break;
            case 3:
                currentPlayer.setMoney(currentPlayer.getMoney() + 8 * moneyFactor());
                eventLabel = new Label("THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $" + (8 * moneyFactor()) + ".");
                break;
            case 4:
                currentPlayer.setMoney(currentPlayer.getMoney() + 2 * moneyFactor());
                eventLabel = new Label("YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $" + (2 * moneyFactor()) + ".");
                break;
            case 5:
                if (!curPlayerHasLowScore()) {
                    currentPlayer.setMoney(currentPlayer.getMoney() - 4 * moneyFactor());
                    eventLabel = new Label("FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $" + (4 * moneyFactor()) + ".");
                }
                break;
            case 6:
                if (!curPlayerHasLowScore()) {
                    currentPlayer.setFood(currentPlayer.getFood() / 2);
                    eventLabel = new Label("MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE "
                            + "SHED AND STOLE HALF YOUR FOOD.");
                }
                break;
            case 7:
                if (!curPlayerHasLowScore()) {
                    currentPlayer.setMoney(currentPlayer.getMoney() - 6 * moneyFactor());
                    eventLabel = new Label("YOUR SPACE GYPSY INLAWS MADE A MESS OF THE TOWN. "
                            + "IT COST YOU $" + (6 * moneyFactor()) + "TO CLEAN IT UP.");
                }
                break;
            default:
                break;
        }
        if (eventLabel != null) {
            Button okBtn = new Button("OK");
            final Stage dialogStage = GameScreenController.stageMaker("Random Event",
                    GameScreenController.vBoxMaker(eventLabel, GameScreenController.hBoxMaker(null, okBtn)));
            okBtn.setOnAction(arg0 -> dialogStage.close());
            dialogStage.show();
        }

    }

    /**
     * Determines if the current Player has the lowest score
     *
     * @return if the current Player has the lowest score
     */
    private static boolean curPlayerHasLowScore() {
        //Assume the curPlayer's score is the lowest
        int lowScore = currentPlayer.getScore();

        //Determine which player has the lowest score
        for (int i = 0; i < gameConfig.getNumPlayers(); i++) {
            if (gameConfig.getPlayers()[i].getScore() < lowScore) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculate money factor for score
     *
     * @return money factor
     */
    private static int moneyFactor() {
        if (round < 4) {
            return 25;
        } else if (round < 8) {
            return 50;
        } else if (round < 12) {
            return 75;
        } else {
            return 100;
        }
    }

    /**
     * Do all actions that should occur at the start of each round
     */
    private static void nextRound() {
        round++;
        calculateProduction();
        initializePlayerOrder();
    }

    /**
     * Calculate production for all tiles in the GAMEBOARD
     */
    private static void calculateProduction() {
        Tile[][] tiles = gameConfig.getGameBoard().getTiles();

        // Loop through all tiles, calculate their production
        for (Tile[] tile : tiles) {
            for (int j = 0; j < tiles[0].length; j++) {
                tile[j].calculateProduction();
            }
        }
    }

    /**
     * Sets up the stack of players in the correct order (based on score)
     */
    private static void initializePlayerOrder() {
        playerOrder.addAll(Arrays.asList(gameConfig.getPlayers()).subList(0, gameConfig.getNumPlayers()));
    }

    public static GameConfig getGameConfig() {
        return gameConfig;
    }

    public static void setGameConfig(GameConfig game) {
        gameConfig = game;
    }

    public static int getRound() {
        return round;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player current) {
        currentPlayer = current;
    }

    public static int getTurnSeconds() {
        return turnSeconds;
    }

    public static void setTurnSeconds(int seconds) {
        turnSeconds = seconds;
    }

    /**
     * Serializes the GamePlay object and writes the contents to the database
     */
    public static void saveGame() {
        Gson gson = new Gson();

        //Creates JSON for GamePlay field
        String idJson = gson.toJson(gamePlayID());
        String configJson = gson.toJson(gameConfig);
        String roundJson = gson.toJson(round);
        String playerJson = gson.toJson(currentPlayer);
        String turnJson = gson.toJson(turnSeconds);

        //Write to database
        Database.saveGame(idJson, configJson, roundJson, playerJson, turnJson);
    }

    /**
     * Loads the GamePlay serializable from the database
     */
    public static void loadGame(String loadedGameID) {
        //todo: complete
        ArrayList<String> gameInfo = Database.getGame(loadedGameID);
        Gson gson = new Gson();

        //Creates mapping for JSON objects to be deserialized
        Map<String, Object> gameConfigRootMapObject = gson.fromJson(gameInfo.get(0), Map.class);
        Map<String, Object> currentPlayerRootMapObject = gson.fromJson(gameInfo.get(2), Map.class);

        //Uses the map to set value for currentPlayer
        GamePlay.setGameConfig(gameConfigRootMapObject);
        GamePlay.round = Integer.parseInt(gameInfo.get(1));
        GamePlay.setCurrentPlayer(setCurrentPlayer(currentPlayerRootMapObject));
        GamePlay.turnSeconds = Integer.parseInt(gameInfo.get(3));
        //TODO: restart timer and players seconds
    }

    private static GameConfig setGameConfig(Map<String, Object> gameConfigRootMapObject) {
        Gson gson = new Gson();

        //Extract Json for Difficulty
        GameConfig.Difficulty difficulty = null;
        String objDifficulty = (String) gameConfigRootMapObject.get("difficulty");
        switch (objDifficulty) {
            case "BEGINNER":
                difficulty = GameConfig.Difficulty.BEGINNER;
                break;
            case "INTERMEDIATE":
                difficulty = GameConfig.Difficulty.INTERMEDIATE;
                break;
            case "ADVANCED":
                difficulty = GameConfig.Difficulty.ADVANCED;
                break;
            default:
                break;
        }

        //Extract JSON for mapType
        GameConfig.MapType mapType = null;
        String objMapType = (String) gameConfigRootMapObject.get("mapType");
        switch (objMapType) {
            case "STANDARD":
                mapType = GameConfig.MapType.STANDARD;
                break;
            case "RANDOM":
                mapType = GameConfig.MapType.RANDOM;
                break;
            default:
                break;
        }

        //Extract numPlayers from JSON
        int numPlayers = ((Double) gameConfigRootMapObject.get("numPlayers")).intValue();

        //Extract Store from jSON
        LinkedTreeMap storeMap = (LinkedTreeMap) gameConfigRootMapObject.get("store");
        Store store = new Store(((Double) storeMap.get("numFood")).intValue(),
                ((Double) storeMap.get("numEnergy")).intValue(),
                ((Double) storeMap.get("numSmithore")).intValue(),
                ((Double) storeMap.get("numMule")).intValue());


        //Extract Player[] from JSON
        ArrayList playerList = (ArrayList) gameConfigRootMapObject.get("players");
        Player[] players = new Player[playerList.size()];
        for (int i = 0; i < playerList.size(); i++) {
            players[i] = setCurrentPlayer((LinkedTreeMap) playerList.get(i));
        }

        //Extract JSON for Board
        //TODO: BUG FIX this doesn't write all of the tiles correctly
        Board board;
        LinkedTreeMap boardMap = (LinkedTreeMap) gameConfigRootMapObject.get("gameBoard");
        ArrayList list = (ArrayList) boardMap.get("tiles");
        Tile[][] tiles = new Tile[5][9];

        for(int i = 0; i < list.size(); i++) {
            for (int j = 0; j < ((ArrayList) list.get(i)).size(); j++) {
                LinkedTreeMap tileMap = (LinkedTreeMap) ((ArrayList) list.get(i)).get(j);
                Player owner;
                //TODO: Mule Logic
                String objTerrain = (String) tileMap.get("terrain");
                Tile.Terrain terrain = null;
                switch (objTerrain) {
                    case "RIVER":
                        terrain = Tile.Terrain.RIVER;
                        break;
                    case "TOWN":
                        terrain = Tile.Terrain.TOWN;
                        break;
                    case "ONEMOUNTAIN":
                        terrain = Tile.Terrain.ONEMOUNTAIN;
                        break;
                    case "TWOMOUNTAIN":
                        terrain = Tile.Terrain.TWOMOUNTAIN;
                        break;
                    case "THREEMOUTAIN":
                        terrain = Tile.Terrain.THREEMOUNTAIN;
                        break;
                    case "PLAIN":
                        terrain = Tile.Terrain.PLAIN;
                        break;
                    default:
                        break;
                }
                tiles[i][j] = new Tile(terrain);
                if (tileMap.get("owner") != null) {
                    LinkedTreeMap ownerMap = (LinkedTreeMap) tileMap.get("owner");
                    owner = setCurrentPlayer(ownerMap);
                    tiles[i][j].setOwner(owner);
                }
            }
        }
        board = new Board(tiles);

        GameConfig gameConfig =  new GameConfig(difficulty,mapType, numPlayers, store, board, players);
        GamePlay.setGameConfig(gameConfig);
        return new GameConfig(difficulty,mapType, numPlayers, store, board, players);
    }

    /**
     * Takes in GSON map for player and sets the current player for game
     *
     * @param currentPlayerRootMapObject yes
     * @return
     */
    private static Player setCurrentPlayer(Map<String, Object> currentPlayerRootMapObject) {
        Player.Color color = null;
        Player.Race race = null;
        String objColor = (String) currentPlayerRootMapObject.get("color");
        String objRace = (String) currentPlayerRootMapObject.get("race");

        switch (objColor) {
            case "RED":
                color = Player.Color.RED;
                break;
            case "GREEN":
                color = Player.Color.GREEN;
                break;
            case "BLUE":
                color = Player.Color.BLUE;
                break;
            case "YELLOW":
                color = Player.Color.YELLOW;
                break;
            case "PURPLE":
                color = Player.Color.PURPLE;
                break;
            default:
                break;
        }
        switch (objRace) {
            case "HUMAN":
                race = Player.Race.HUMAN;
                break;
            case "FLAPPER":
                race = Player.Race.FLAPPER;
                break;
            case "BONZOID":
                race = Player.Race.BONZOID;
                break;
            case "BUZZITE":
                race = Player.Race.BUZZITE;
                break;
            case "UGAITE":
                race = Player.Race.UGAITE;
        }

        Player player = new Player((String) currentPlayerRootMapObject.get("name"),
                race,
                color,
                ((Double) currentPlayerRootMapObject.get("number")).intValue(),
                ((Double) currentPlayerRootMapObject.get("money")).intValue(),
                ((Double) currentPlayerRootMapObject.get("food")).intValue(),
                ((Double) currentPlayerRootMapObject.get("energy")).intValue(),
                ((Double) currentPlayerRootMapObject.get("smithore")).intValue(),
                ((Double) currentPlayerRootMapObject.get("crystite")).intValue(),
                ((Double) currentPlayerRootMapObject.get("foodMule")).intValue(),
                ((Double) currentPlayerRootMapObject.get("energyMule")).intValue(),
                ((Double) currentPlayerRootMapObject.get("smithoreMule")).intValue(),
                ((Double) currentPlayerRootMapObject.get("crystiteMule")).intValue(),
                ((Double) currentPlayerRootMapObject.get("numLand")).intValue());

        return player;
    }


    /**
     * Generates a uniqueID associated with a game
     *
     * @return String
     */
    public static String gamePlayID() {
        return String.valueOf(currentPlayer.getName()) + turnSeconds + "-" + round + moneyFactor() +
                gameConfig.getNumPlayers() + gameConfig.getDifficulty();
    }
}
