package edu.gatech.justiceleague.mule.controller;

import edu.gatech.justiceleague.mule.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.edu.gatech.justiceleague.mule.model.Music;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.RejectedExecutionException;

public class GameScreenController {

    @FXML
    private GridPane pane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane playerStatsGrid;

    @FXML
    private Text playerName;

    @FXML
    private Text playerMoney;

    @FXML
    private Text playerTimer;

    @FXML
    private Label food;

    @FXML
    private Label energy;

    @FXML
    private Label smithore;

    @FXML
    private Label crystite;

    @FXML
    private Label mule;

    @FXML
    private Button endButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label currentPlayer;

    @FXML
    private ImageView icon1;

    @FXML
    private ImageView icon2;

    @FXML
    private ImageView icon3;

    @FXML
    private ImageView icon4;


    // Keep track of which players have passed during the land selection phase
    private final HashSet<Player> passedPlayers = new HashSet<>();

    // Used to trigger an update every second
    private Timeline ticker;

    //music
    private Music music;


    /**
     * Event fires when a tile besides the town is clicked on the map
     * The player can choose between buying property, placing a mule
     * or exiting the screen.
     */
    private final EventHandler<MouseEvent> tileClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            //Defaults to purchaseHandler when in
            // land selection phase
            if (GamePlay.getRound() < 1) {
                purchaseHandler.handle(event);
                return;
            }

            Label purchaseLandLabel = new Label("Would you like to Purchase Property, or Place a Mule?");
            purchaseLandLabel.setAlignment(Pos.CENTER);

            Button purchaseBtn = new Button("Purchase Property");
            Button muleBtn = new Button("Place a Mule");

            //Hook up event handler for purchase land and place mule
            purchaseBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
            muleBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, placeMuleHandler);

            final Stage dialogStage = stageMaker("Property Options", vBoxMaker(purchaseLandLabel, hBoxMaker(null, purchaseBtn, muleBtn)));

            //Purchase handler needs to fire when button is clicked with event args
            purchaseBtn.setOnAction(arg0 -> {
                purchaseHandler.handle(event);
                dialogStage.close();
            });

            //Place mule handler needs to fire when button is clicked with event args
            muleBtn.setOnAction(arg0 -> {
                placeMuleHandler.handle(event);
                dialogStage.close();
            });

            dialogStage.show();
        }
    };

    /**
     * Event fires when a tile is clicked in the land selection phase, or a
     * user decides to buy land later in the game
     */
    private final EventHandler<MouseEvent> purchaseHandler = event -> {
        // Setup dialog box to purchase a property


        Label purchaseLandLabel = new Label("Purchase this Property?");
        purchaseLandLabel.setAlignment(Pos.CENTER);

        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        final Stage dialogStage = stageMaker("Property Options", vBoxMaker(purchaseLandLabel, hBoxMaker(null, yesBtn, noBtn)));

        yesBtn.setOnAction(arg0 -> {
            buyLand((GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });
        noBtn.setOnAction(arg0 -> dialogStage.close());

        dialogStage.show();
    };

    private final EventHandler<MouseEvent> placeMuleHandler = event -> {

        Label purchaseLandLabel = new Label("\t\tSelect a Mule");
        purchaseLandLabel.setAlignment(Pos.CENTER);

        Button foodBtn = new Button("Food");
        Button energyBtn = new Button("Energy");
        Button smithBtn = new Button("Smithore");
        Button crystBtn = new Button("Crystite");

        final Stage dialogStage = stageMaker("Property Options", vBoxMaker(purchaseLandLabel,
                hBoxMaker(null, foodBtn, energyBtn, smithBtn, crystBtn)));


        foodBtn.setOnAction(arg0 -> {
            placeMule("FOOD", (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        energyBtn.setOnAction(arg0 -> {
            placeMule("ENERGY", (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        smithBtn.setOnAction(arg0 -> {
            placeMule("SMITHORE", (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        crystBtn.setOnAction(arg0 -> {
            placeMule("CRYSTITE", (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        dialogStage.show();
    };

    private final EventHandler<MouseEvent> townClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            // Don't let player enter town until after land selection phase has been finished
            if (GamePlay.getRound() != 0) {
                Stage stage = (Stage) pane.getScene().getWindow();
                Parent root;
                try {
                    // Load the town when the town is clicked
                    root = FXMLLoader.load(getClass().getResource("/fxml/town.fxml"));
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    throw new RejectedExecutionException();
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                event.consume();
                music.stopMusic();
            }
        }
    };

    /**
     * Initialize is called whenever this screen is loaded (beginning of game and returning from town)
     * Should display the game grid, and set up the player statistics on the bottom of the screen
     */
    @FXML
    private void initialize() {
        displayMap();
        setPlayerStats();
        if (GamePlay.getRound() != 0) {
            endButton.setDisable(true);
        }
        currentPlayer.setText(GamePlay.getCurrentPlayer().getName() + ", it's your turn.");

        ticker = new Timeline(new KeyFrame(Duration.millis(100), ae -> updateScreen()));
        ticker.setCycleCount(Animation.INDEFINITE);
        ticker.play();

        music = new Music(Paths.get("src/main/resources/music/Spanish_Flea.mp3").toUri().toString());
        music.startMusic();
    }

    /**
     * Method to be called by the Timeline every so often, so that things on the screen get updated
     */
    private void updateScreen() {
        for (int i = 0; i < GamePlay.getGameConfig().getNumPlayers(); i++) {
            updatePlayer(i);
        }
    }

    /**
     * Gets called when a player passes during the land selection phase
     *
     * @param event The event from the button click
     */
    @FXML
    private void handleEndTurn(ActionEvent event) {
        passedPlayers.add(GamePlay.getCurrentPlayer());
        if (passedPlayers.size() == GamePlay.getGameConfig().getNumPlayers()) {
            // Remove pass button from screen
            endButton.setDisable(true);
            // All players have passed, begin actual game
            GamePlay.startGame();
            return;
        }
        if ((GamePlay.getCurrentPlayer().getNumber() + 1) == GamePlay.getGameConfig().getNumPlayers()) {
            GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[0]);
            currentPlayer.setText(GamePlay.getCurrentPlayer().getName() + ", it's your turn.");
            return;
        }
        GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[GamePlay.getCurrentPlayer().getNumber() + 1]);
        currentPlayer.setText(GamePlay.getCurrentPlayer().getName() + ", it's your turn.");
    }

    @FXML
    private void handleSaveGame(ActionEvent event) {
        Label saveGame = new Label("Would you like to save your game?\nNOTE: Saving will end your game.");
        saveGame.setAlignment(Pos.CENTER);

        Button save = new Button("Save");
        Button exit = new Button("Exit Without Saving");

        final Stage dialogStage = stageMaker("Save", vBoxMaker(saveGame, hBoxMaker(null, save, exit)));

        save.setOnAction(arg0 -> {
            //Instantiate a gameplay object in order to save the game.
            GamePlay.saveGame();
            System.out.println(GamePlay.gamePlayID());
            dialogStage.close();

            //Shut down game after saving
            System.exit(0);
        });

        exit.setOnAction(arg0 -> dialogStage.close());
        dialogStage.show();
    }

    /**
     * Displays the entire map on the screen by calling the updateTile() method
     * on each tile.
     */
    private void displayMap() {
        anchorPane.setStyle("-fx-background-color: #83d95e;");
        Tile[][] board = GamePlay.getGameConfig().getGameBoard().getTiles();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                updateTile(i, j);
            }
        }
    }

    /**
     * Sets player stat boxes
     */
    private void setPlayerStats() {
        //show player stat boxes based on number of players
        if (GamePlay.getGameConfig().getNumPlayers() == 2) {
            playerStatsGrid.getChildren().remove(2);
            playerStatsGrid.getChildren().remove(2);
        } else if (GamePlay.getGameConfig().getNumPlayers() == 3) {
            playerStatsGrid.getChildren().remove(3);
        }

        Player[] players = GamePlay.getGameConfig().getPlayers();

        for (int i = 0; i < GamePlay.getGameConfig().getNumPlayers(); i++) {

            //get the pane
            Pane playerStatGridPane = (Pane) playerStatsGrid.getChildren().get(i);

            //get the current player
            Player currentPlayer = players[i];

            //set the name, money, and resources
            playerName = (Text) playerStatGridPane.getChildren().get(0);
            playerName.setText(currentPlayer.getName());
            String playerColor = currentPlayer.getColor().toString();
            playerName.setStyle("-fx-stroke: " + playerColor + ";");

            playerMoney = (Text) playerStatGridPane.getChildren().get(1);
            playerMoney.setText("$" + currentPlayer.getMoney());

            food = (Label) playerStatGridPane.getChildren().get(3);
            food.setText(currentPlayer.getFood() + " Food");

            energy = (Label) playerStatGridPane.getChildren().get(4);
            energy.setText(currentPlayer.getEnergy() + " Energy");

            smithore = (Label) playerStatGridPane.getChildren().get(5);
            smithore.setText(currentPlayer.getSmithore() + " Smithore");

            crystite = (Label) playerStatGridPane.getChildren().get(6);
            crystite.setText(currentPlayer.getCrystite() + " Crystite");

            mule = (Label) playerStatGridPane.getChildren().get(7);
            // TODO: Show all mule types?
            //mule.setText(currentPlayer.getMule() + " Mule");

            Player.Race race = currentPlayer.getRace();

            int currentPlayerIndex = i + 1;
            if (currentPlayerIndex == 1) {
                icon1 = setRaceIcons(race, icon1);
            } else if (currentPlayerIndex == 2) {
                icon2 = setRaceIcons(race, icon2);
            } else if (currentPlayerIndex == 3) {
                icon3 = setRaceIcons(race, icon3);
            } else if (currentPlayerIndex == 4) {
                icon4 = setRaceIcons(race, icon4);
            }
        }
    }

    private ImageView setRaceIcons(Player.Race race, ImageView icon) {
        if (race == Player.Race.HUMAN) {
            icon.setImage(new Image(getClass().getResourceAsStream("/img/human.png")));
        } else if (race == Player.Race.BONZOID) {
            icon.setImage(new Image(getClass().getResourceAsStream("/img/bonzoid.png")));
        } else if (race == Player.Race.BUZZITE) {
            icon.setImage(new Image(getClass().getResourceAsStream("/img/buzzite.png")));
        } else if (race == Player.Race.FLAPPER) {
            icon.setImage(new Image(getClass().getResourceAsStream("/img/flapper.png")));
        } else if (race == Player.Race.UGAITE) {
            icon.setImage(new Image(getClass().getResourceAsStream("/img/ugaite.png")));
        } else if (race == Player.Race.BADONK) {
            icon.setImage(new Image(getClass().getResourceAsStream("/img/badonk.png")));
        } else if (race == Player.Race.DOTHRAKI) {
            icon.setImage(new Image(getClass().getResourceAsStream("/img/dothraki.png")));
        }

        return icon;
    }

    /**
     * Updates the player information at the bottom of the screen (player money and time left)
     * Gets called by the Timeline created in initialize()
     *
     * @param playerNum The index of the Player to update in the player array
     */
    private void updatePlayer(int playerNum) {
        Player cur = GamePlay.getGameConfig().getPlayers()[playerNum];
        String money = "$" + cur.getMoney();
        Pane playerStatGridPane = ((Pane) playerStatsGrid.getChildren().get(playerNum));

        playerMoney = (Text) playerStatGridPane.getChildren().get(1);
        playerMoney.setText(money);

        playerTimer = (Text) playerStatGridPane.getChildren().get(2);
        if (cur.getTimer() != null && cur == GamePlay.getCurrentPlayer()) {
            playerTimer.setText(cur.getTimer().getRemainingTime() + " secs");
        } else {
            playerTimer.setText("Timer");
        }

        food = (Label) playerStatGridPane.getChildren().get(3);
        food.setText(GamePlay.getGameConfig().getPlayers()[playerNum].getFood() + " Food");

        energy = (Label) playerStatGridPane.getChildren().get(4);
        energy.setText(GamePlay.getGameConfig().getPlayers()[playerNum].getEnergy() + " Energy");

        smithore = (Label) playerStatGridPane.getChildren().get(5);
        smithore.setText(GamePlay.getGameConfig().getPlayers()[playerNum].getSmithore() + " Smithore");

        crystite = (Label) playerStatGridPane.getChildren().get(6);
        crystite.setText(GamePlay.getGameConfig().getPlayers()[playerNum].getCrystite() + " Crystite");

        mule = (Label) playerStatGridPane.getChildren().get(7);
        // TODO: Show all mule types?
        mule.setText(GamePlay.getGameConfig().getPlayers()[playerNum].getMuleTotal() + " Mule");
    }

    /**
     * Attempt to buy a tile using the current player. Has no effect if the tile already has an owner, or the
     * player doesn't have enough money.
     *
     * @param x The X coordinate of the tile
     * @param y The Y coordinate of the tile
     */
    private void buyLand(int x, int y) {
        Tile current = GamePlay.getGameConfig().getGameBoard().getTiles()[x][y];
        Random rand = new Random();
        int price = 300 + GamePlay.getRound() * rand.nextInt(101);

        if (GamePlay.getCurrentPlayer() == null) {
            System.err.println("ERROR: Current player null!");
            return;
        }

        //Notify user if current tile is taken or cannot afford the property
        if (GamePlay.getCurrentPlayer().getMoney() < price) {
            Label errorLabel = new Label("You cannot afford this property.");
            errorLabel.setAlignment(Pos.CENTER);
            Button okBtn = new Button("OK");
            final Stage dialogStage = stageMaker("Sorry", vBoxMaker(errorLabel, hBoxMaker(null, okBtn)));
            okBtn.setOnAction(arg0 -> dialogStage.close());
            dialogStage.show();
        } else if (current.getOwner() != null) {
            Label errorLabel = new Label("This property already has an owner!");
            errorLabel.setAlignment(Pos.CENTER);
            Button okBtn = new Button("OK");
            final Stage dialogStage = stageMaker("Sorry", vBoxMaker(errorLabel, hBoxMaker(null, okBtn)));
            okBtn.setOnAction(arg0 -> dialogStage.close());
            dialogStage.show();
        }

        //When the player already owns 2 land tiles, buying land costs money
        if (GamePlay.getCurrentPlayer().getMoney() >= price && current.getOwner() == null
                && GamePlay.getCurrentPlayer().getNumLand() >= 2 && current.getTerrain() != Tile.Terrain.TOWN) {
            current.setOwner(GamePlay.getCurrentPlayer());
            GamePlay.getCurrentPlayer().setMoney(GamePlay.getCurrentPlayer().getMoney() - price);
            updatePlayer(GamePlay.getCurrentPlayer().getNumber());
            updateTile(x, y);
            if (GamePlay.getRound() == 0) {
                GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[(GamePlay.getCurrentPlayer().getNumber() + 1) % GamePlay.getGameConfig().getNumPlayers()]);
                currentPlayer.setText(GamePlay.getCurrentPlayer().getName() + ", it's your turn.");
            }
        } else if (current.getOwner() == null && current.getTerrain() != Tile.Terrain.TOWN && GamePlay.getCurrentPlayer().getNumLand() < 2) {
            current.setOwner(GamePlay.getCurrentPlayer());
            GamePlay.getCurrentPlayer().incrementLand();
            updateTile(x, y);
            if (GamePlay.getRound() == 0) {
                GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[(GamePlay.getCurrentPlayer().getNumber() + 1) % GamePlay.getGameConfig().getNumPlayers()]);
                currentPlayer.setText(GamePlay.getCurrentPlayer().getName() + ", it's your turn.");
            }
        }
    }

    /**
     * Places mule at coordinate
     *
     * @param muleType Type to place
     * @param x coordinate
     * @param y coordinate
     */
    private void placeMule(String muleType, Integer x, Integer y) {
        Tile curTile = GamePlay.getGameConfig().getGameBoard().getTiles()[x][y];
        Player curPlayer = GamePlay.getCurrentPlayer();

        // If the player doesn't have any of this muletype, do nothing
        if (curPlayer.getMuleCount(muleType) == 0) {
            return;
        }

        switch (muleType) {
            case "FOOD":
                //Checks if player has lost MULE
                if (curTile.getMule() != null || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    //TODO: alert user they have lost mule
                    curPlayer.setFoodMule(curPlayer.getFoodMule() - 1);
                } else {
                    //Place corresponding MULE
                    curPlayer.setFoodMule(curPlayer.getFoodMule() - 1);
                    curTile.setMule(MuleFactory.getMule(muleType, curPlayer, curTile.getTerrain()));
                    updateTile(x, y);
                }
                updatePlayer(curPlayer.getNumber());
                break;
            case "ENERGY":
                //Checks if player has lost MULE
                if (curTile.getMule() != null || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    curPlayer.setEnergyMule(curPlayer.getEnergyMule() - 1);
                } else {
                    //Place corresponding MULE
                    curPlayer.setEnergyMule(curPlayer.getEnergyMule() - 1);
                    curTile.setMule(MuleFactory.getMule(muleType, curPlayer, curTile.getTerrain()));
                    updateTile(x, y);
                }
                updatePlayer(curPlayer.getNumber());
                break;
            case "SMITHORE":
                //Checks if player has lost MULE
                if (curTile.getMule() != null || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    curPlayer.setSmithoreMule(curPlayer.getSmithoreMule() - 1);
                } else {
                    //Place corresponding MULE
                    curPlayer.setSmithoreMule(curPlayer.getSmithoreMule() - 1);
                    curTile.setMule(MuleFactory.getMule(muleType, curPlayer, curTile.getTerrain()));
                    updateTile(x, y);
                }
                updatePlayer(curPlayer.getNumber());
                break;
            case "CRYSTITE":
                //Checks if player has lost MULE
                if (curTile.getMule() != null || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    curPlayer.setCrystiteMule(curPlayer.getCrystiteMule() - 1);
                } else {
                    //Place corresponding MULE
                    curPlayer.setCrystiteMule(curPlayer.getCrystiteMule() - 1);
                    curTile.setMule(MuleFactory.getMule(muleType, curPlayer, curTile.getTerrain()));
                    updateTile(x, y);
                }
                updatePlayer(curPlayer.getNumber());
                break;
            default:
                System.out.println("Error placing MULE");
                throw new RuntimeException();
        }
    }


    /**
     * Updates or first draws a tile on the screen. Takes into consideration
     * if there is a mule on the tile or not.
     * <p>
     *
     * @param x The X position of the tile
     * @param y The Y position of the tile
     */
    private void updateTile(int x, int y) {
        // Get current tile, check if there is a mule on the tile
        Tile currentTile = GamePlay.getGameConfig().getGameBoard().getTiles()[x][y];
        String mule;
        if (currentTile.getMule() != null) {
            mule = currentTile.getMule().getType();
        } else {
            mule = "NONE";
        }

        // Set the color styles for the border if there is an owner of the tile
        String styles = "";
        if (currentTile.getOwner() != null) {
            String color = currentTile.getOwner().getColor().toString();
            styles = "-fx-border-color: " + color + ";\n"
                    + "-fx-border-width: 5;\n"
                    + "-fx-border-style: solid;\n";
        }

        switch (mule) {
            case "FOOD":
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/plain_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/river_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_food_tile.png")), y, x);
                }
                break;
            case "ENERGY":
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/plain_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/river_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_energy_tile.png")), y, x);
                }
                break;
            case "SMITHORE":
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/plain_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/river_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_smithore_tile.png")), y, x);
                }
                break;
            case "CRYSTITE":
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/plain_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/river_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_crystite_tile.png")), y, x);
                }
                break;
            case "NONE":
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/plain_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/river_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TOWN) {
                    pane.add(hBoxMaker(null, imageMaker("/img/town_tile.png")), y, x);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Makes HBox for game screen
     * @param styles of HBox
     * @param nodes of HBox
     * @return HBox
     */
    public static HBox hBoxMaker(String styles, Node... nodes) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20.0);
        hBox.getChildren().addAll(nodes);

        if (styles != null) {
            hBox.setStyle(styles);
        }

        return hBox;
    }

    /**
     * Makes VBox for game screen
     * @param nodes of VBox
     * @return VBox
     */
    public static VBox vBoxMaker(Node... nodes) {
        VBox vBox = new VBox();
        vBox.setSpacing(20.0);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(nodes);

        return vBox;
    }

    /**
     * Makes stage for game screen from VBox
     * @param title of stage
     * @param vbox of stage
     * @return Stage
     */
    public static Stage stageMaker(String title, VBox vbox) {
        final Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(vbox));
        return dialogStage;

    }

    /**
     * Creates ImageView objects from file paths for use in game screen
     * @param path of image
     * @return ImageView object
     */
    public ImageView imageMaker(String path) {
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream(path)));
        if (path.equals("/img/town_tile.png")) {
            image.addEventHandler(MouseEvent.MOUSE_CLICKED, townClickHandler);
        } else {
            image.addEventHandler(MouseEvent.MOUSE_CLICKED, tileClickHandler);
        }
        image.setFitHeight(80);
        image.setFitWidth(80);

        return image;
    }

    //TODO: Stage maker?
    //TODO: BUG Player ordering when land selction phase
}
