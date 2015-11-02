package edu.gatech.justiceleague.mule.model;

import edu.gatech.justiceleague.mule.controller.GameScreenController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


/**
 * Contains the main game logic
 */
public class GamePlay implements Serializable {
    public static GameConfig gameConfig;
    public static int round = 0;
    public static Player currentPlayer;
    public static Queue<Player> playerOrder = new PriorityQueue<>();
    public static int turnSeconds = 0;

    /**
     * This contrustructor is only in order to serialize
     * @param gameConfig game configuration options
     * @param round current round number
     * @param currentPlayer current player in the game
     * @param playerOrder queue determining player order
     * @param turnSeconds number of seconds left in turn
     */
    public GamePlay(GameConfig gameConfig, int round, Player currentPlayer, Queue<Player> playerOrder, int turnSeconds) {
        // TODO: Test if we can just have an empty constructor?
        GamePlay.gameConfig = gameConfig;
        GamePlay.round = round;
        GamePlay.currentPlayer = currentPlayer;
        GamePlay.playerOrder = playerOrder;
        GamePlay.turnSeconds = turnSeconds;
    }
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
     * //todo: show the actual amount of $$ not m
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
                eventLabel = new Label("THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $ 8*m.");
                break;
            case 4:
                currentPlayer.setMoney(currentPlayer.getMoney() + 2 * moneyFactor());
                eventLabel = new Label("YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $2*m");
                break;
            case 5:
                if (!curPlayerHasLowScore()) {
                    currentPlayer.setMoney(currentPlayer.getMoney() - 4 * moneyFactor());
                    eventLabel = new Label("FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $4*m.");
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
                            + "IT COST YOU $6*m TO CLEAN IT UP.");
                }
            break;
            default:
                System.out.println("shit");
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
    public void saveGame() {
        String fileName = "SavedGameData.bin";
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(this);
            out.close();
            //serializeJavaObjectToDB(getConnection());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done writing");
        }
    }


//    public static long serializeJavaObjectToDB(Connection connection) throws SQLException {
//        //todo: USE
//        String SQL_SERIALIZE_OBJECT = "INSERT INTO serialized_java_objects(id, object_name, serialized_object) VALUES (?, ?, ?)";
//        PreparedStatement pstmt = connection
//                .prepareStatement(SQL_SERIALIZE_OBJECT);
//
//        // just pass the text file in!
//        pstmt.setObject(1, gamePlayID());
//        pstmt.setString(2, objectToSerialize.getClass().getName());
//        pstmt.setObject(3, objectToSerialize);
//        pstmt.executeUpdate();
//        ResultSet rs = pstmt.getGeneratedKeys();
//        int serialized_id = -1;
//        if (rs.next()) {
//            serialized_id = rs.getInt(1);
//        }
//        rs.close();
//        pstmt.close();
//        System.out.println("Java object serialized to database. Object: "
//                + objectToSerialize);
//        return serialized_id;
//    }

    /**
     * Loads the GamePlay serializable from the database
     */
    public static GamePlay loadGame() {
        String fileName = "SavedGameData.bin";
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            GamePlay savedGamePlay = (GamePlay) in.readObject();
            in.close();
            return savedGamePlay;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done loading");
        }
        return null;
    }

    /**
     * Generates a uniqueID associated with a game
     * @return String
     */
    public static String gamePlayID() {
        return String.valueOf(currentPlayer.getName().charAt(0)) + turnSeconds + "-" + round + moneyFactor();
    }

    //TODO: database functions should be in attached jre to obscure server and password
}
