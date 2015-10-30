package edu.gatech.justiceleague.mule.model;

import edu.gatech.justiceleague.mule.controller.GameScreenController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;


/**
 * Contains the main game logic
 */
public class GamePlay {
    private static GameConfig gameConfig;
    private static int round = 0;
    private static Player currentPlayer;
    private static Queue<Player> playerOrder = new PriorityQueue<>();
    private static int turnSeconds = 0;

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
        Tile[][] tiles = gameConfig.getGameboard().getTiles();

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
}
