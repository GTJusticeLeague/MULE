package MULE;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Random;

import static MULE.Mule.MULETYPE.FOOD;
import static MULE.Mule.MULETYPE.NONE;

public class GameScreenController {

    @FXML
    private GridPane Pane;

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
    private Label currentPlayer;

    // Images to use for the tiles
    private final Image town = new Image(getClass().getResourceAsStream("/img/town_tile.png"));

    // Keep track of which players have passed during the land selection phase
    private final HashSet<Player> passedPlayers = new HashSet<>();

    // Used to trigger an update every second
    private Timeline ticker;


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
            if (GamePlay.round < 1) {
                purchaseHandler.handle(event);
                return;
            }
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Property Options");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Label purchaseLandLabel = new Label("Would you like to Purchase Property, or Place a Mule?");
            purchaseLandLabel.setAlignment(Pos.CENTER);

            Button purchaseBtn = new Button("Purchase Property");
            Button muleBtn = new Button("Place a Mule");

            //Hook up event handler for purchase land and place mule
            purchaseBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
            muleBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, placeMuleHandler);

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

            dialogStage.setScene(new Scene(vBoxMaker(purchaseLandLabel, hBoxMaker(null, purchaseBtn, muleBtn))));
            dialogStage.show();
        }
    };

    private final EventHandler<MouseEvent> purchaseHandler = event -> {
        // Setup dialog box to purchase a property
        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Property Options");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Label purchaseLandLabel = new Label("Purchase this Property?");
        purchaseLandLabel.setAlignment(Pos.CENTER);

        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        yesBtn.setOnAction(arg0 -> {
            buyLand((GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });
        noBtn.setOnAction(arg0 -> dialogStage.close());

        dialogStage.setScene(new Scene(vBoxMaker(purchaseLandLabel, hBoxMaker(null, yesBtn, noBtn))));
        dialogStage.show();
    };

    private final EventHandler<MouseEvent> placeMuleHandler = event -> {
        // Setup dialog box to purchase a property
        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Property Options");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Label purchaseLandLabel = new Label("\t\tSelect a Mule");
        purchaseLandLabel.setAlignment(Pos.CENTER);

        Button foodBtn = new Button("Food");
        Button energyBtn = new Button("Energy");
        Button smithBtn = new Button("Smithore");
        Button crystBtn = new Button("Crystite");

        foodBtn.setOnAction(arg0 -> {
            placeMule(FOOD, (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        energyBtn.setOnAction(arg0 -> {
            placeMule(Mule.MULETYPE.ENERGY, (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        smithBtn.setOnAction(arg0 -> {
            placeMule(Mule.MULETYPE.ORE, (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        crystBtn.setOnAction(arg0 -> {
            placeMule(Mule.MULETYPE.CRYSTITE, (GridPane.getRowIndex(((Node) event.getSource()).getParent())),
                    (GridPane.getColumnIndex(((Node) event.getSource()).getParent())));
            dialogStage.close();
        });

        dialogStage.setScene(new Scene(vBoxMaker(purchaseLandLabel, hBoxMaker(null, foodBtn, energyBtn, smithBtn, crystBtn))));
        dialogStage.show();
    };

    private final EventHandler<MouseEvent> townClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            // Don't let player enter town until after land selection phase has been finished
            if (GamePlay.round != 0) {
                Stage stage = (Stage) Pane.getScene().getWindow();
                Parent root = null;
                try {
                    // Load the town when the town is clicked
                    root = FXMLLoader.load(getClass().getResource("/fxml/town.fxml"));
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                event.consume();
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
        if (GamePlay.round != 0) {
            endButton.setDisable(true);
        }
        currentPlayer.setText(GamePlay.currentPlayer.getName() + ", it's your turn.");

        ticker = new Timeline(new KeyFrame(Duration.millis(100), ae -> updateScreen()));
        ticker.setCycleCount(Animation.INDEFINITE);
        ticker.play();
    }

    /**
     * Method to be called by the Timeline every so often, so that things on the screen get updated
     */
    private void updateScreen() {
        for (int i = 0; i < GamePlay.GAMECONFIG.getNumPlayers(); i++) {
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
        passedPlayers.add(GamePlay.currentPlayer);
        if (passedPlayers.size() == GamePlay.GAMECONFIG.getNumPlayers()) {
            // Remove pass button from screen
            endButton.setDisable(true);
            // All players have passed, begin actual game
            GamePlay.startGame();
            return;
        }
        if ((GamePlay.currentPlayer.getNumber() + 1) == GamePlay.GAMECONFIG.getNumPlayers()) {
            GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[0];
            currentPlayer.setText(GamePlay.currentPlayer.getName() + ", it's your turn.");
            return;
        }
        GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[GamePlay.currentPlayer.getNumber() + 1];
        currentPlayer.setText(GamePlay.currentPlayer.getName() + ", it's your turn.");
    }

    /**
     * Displays the entire map on the screen. Adds the images to the GameScreen's GridPane
     */
    private void displayMap() {
        anchorPane.setStyle("-fx-background-color: #83d95e;");
        Tile[][] board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
//                HBox box = new HBox();
                ImageView image = null;
                switch (board[i][j].getTerrain()) {
                    case ONEMOUNTAIN:
                        image = imageMaker("/img/mountain1_tile.png");
                        break;
                    case TWOMOUNTAIN:
                        image = imageMaker("/img/mountain2_tile.png");
                        break;
                    case THREEMOUNTAIN:
                        image = imageMaker("/img/mountain3_tile.png");
                        break;
                    case PLAIN:
                        image = imageMaker("/img/plain_tile.png");
                        break;
                    case RIVER:
                        image = imageMaker("/img/river_tile.png");
                        break;
                    case TOWN:
                        image = new ImageView(town);
                        //TODO: add handlers to imageMaker
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED, townClickHandler);
                        image.setFitHeight(80);
                        image.setFitWidth(80);
                        break;
                    default:
                        System.err.println("ERROR: Invalid Terrain when displaying map!");
                        System.exit(1);
                }

                HBox box;
                if (board[i][j].getOwner() != null) {
                    String color = board[i][j].getOwner().getColor().toString();
                    String styles = "-fx-border-color: " + color + ";\n" +
                            "-fx-border-width: 5;\n" +
                            "-fx-border-style: solid;\n";
                    box = hBoxMaker(styles, image);

                } else {
                    box = hBoxMaker(null, image);
                }
                Pane.add(box, j, i);
            }
        }
    }

    private void setPlayerStats() {
        //show player stat boxes based on number of players
        if (GamePlay.GAMECONFIG.getNumPlayers() == 2) {
            playerStatsGrid.getChildren().remove(2);
            playerStatsGrid.getChildren().remove(2);
        } else if (GamePlay.GAMECONFIG.getNumPlayers() == 3) {
            playerStatsGrid.getChildren().remove(3);
        }

        Player[] players = GamePlay.GAMECONFIG.getPlayers();

        for (int i = 0; i < GamePlay.GAMECONFIG.getNumPlayers(); i++) {

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
        }
    }

    /**
     * Updates the player information at the bottom of the screen (player money and time left)
     * Gets called by the Timeline created in initialize()
     *
     * @param playerNum The index of the Player to update in the player array
     */
    private void updatePlayer(int playerNum) {
        Player cur = GamePlay.GAMECONFIG.players[playerNum];
        String money = "$" + cur.getMoney();
        Pane playerStatGridPane = ((Pane) playerStatsGrid.getChildren().get(playerNum));

        playerMoney = (Text) playerStatGridPane.getChildren().get(1);
        playerMoney.setText(money);

        playerTimer = (Text) playerStatGridPane.getChildren().get(2);
        if (cur.timer != null && cur == GamePlay.currentPlayer) {
            playerTimer.setText(cur.timer.getRemainingTime() + " secs");
        } else {
            playerTimer.setText("Timer");
        }
        
        food = (Label) playerStatGridPane.getChildren().get(3);
        food.setText(GamePlay.GAMECONFIG.players[playerNum].getFood() + " Food");

        energy = (Label) playerStatGridPane.getChildren().get(4);
        energy.setText(GamePlay.GAMECONFIG.players[playerNum].getEnergy() + " Energy");

        smithore = (Label) playerStatGridPane.getChildren().get(5);
        smithore.setText(GamePlay.GAMECONFIG.players[playerNum].getSmithore() + " Smithore");

        crystite = (Label) playerStatGridPane.getChildren().get(6);
        crystite.setText(GamePlay.GAMECONFIG.players[playerNum].getCrystite() + " Crystite");

        mule = (Label) playerStatGridPane.getChildren().get(7);
        // TODO: Show all mule types?
        //mule.setText(GamePlay.GAMECONFIG.players[playerNum].getMule() + " Mule");
    }

    /**
     * Attempt to buy a tile using the current player. Has no effect if the tile already has an owner, or the
     * player doesn't have enough money.
     *
     * @param x The X coordinate of the tile
     * @param y The Y coordinate of the tile
     */
    private void buyLand(int x, int y) {
        Tile current = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles()[x][y];
        Random rand = new Random();
        int price = 300 + GamePlay.round * rand.nextInt(101);

        if (GamePlay.currentPlayer == null) {
            System.err.println("ERROR: Current player null!");
            return;
        }

        //Notify user if current tile is taken or cannot afford the property
        if (GamePlay.currentPlayer.getMoney() < price) {
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Sorry");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Label errorLabel = new Label("You cannot afford this property.");
            errorLabel.setAlignment(Pos.CENTER);

            Button okBtn = new Button("OK");
            okBtn.setOnAction(arg0 -> dialogStage.close());

            dialogStage.setScene(new Scene(vBoxMaker(errorLabel, hBoxMaker(null, okBtn))));
            dialogStage.show();
        } else if (current.getOwner() != null) {
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Sorry");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Label errorLabel = new Label("This property already has an owner!");
            errorLabel.setAlignment(Pos.CENTER);

            Button okBtn = new Button("OK");
            okBtn.setOnAction(arg0 -> dialogStage.close());

            dialogStage.setScene(new Scene(vBoxMaker(errorLabel, hBoxMaker(null, okBtn))));
            dialogStage.show();
        }

        //When the player already owns 2 land tiles, buying land costs money
        if (GamePlay.currentPlayer.getMoney() >= price && current.getOwner() == null &&
                GamePlay.currentPlayer.getNumLand() >= 2 && current.getTerrain() != Tile.Terrain.TOWN) {
            current.setOwner(GamePlay.currentPlayer);
            GamePlay.currentPlayer.setMoney(GamePlay.currentPlayer.getMoney() - price);
            updatePlayer(GamePlay.currentPlayer.getNumber());
            updateTile(x, y, NONE);
            if (GamePlay.round == 0) {
                GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[(GamePlay.currentPlayer.getNumber() + 1) % GamePlay.GAMECONFIG.getNumPlayers()];
                currentPlayer.setText(GamePlay.currentPlayer.getName() + ", it's your turn.");
            }
        } else if (current.getOwner() == null && current.getTerrain() != Tile.Terrain.TOWN && GamePlay.currentPlayer.getNumLand() < 2) {
            current.setOwner(GamePlay.currentPlayer);
            GamePlay.currentPlayer.incrementLand();
            updateTile(x, y, NONE);
            if (GamePlay.round == 0) {
                GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[(GamePlay.currentPlayer.getNumber() + 1) % GamePlay.GAMECONFIG.getNumPlayers()];
                currentPlayer.setText(GamePlay.currentPlayer.getName() + ", it's your turn.");
            }
        }
    }
    private void placeMule(Mule.MULETYPE MULE, Integer x, Integer y) {
        Tile curTile = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles()[x][y];
        Player curPlayer = GamePlay.currentPlayer;

        switch (MULE) {
            case FOOD:
                //Checks if player has lost MULE
                if (curTile.getMule() != null || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    //Decrement if MULE count is above 0
                    if (curPlayer.getFoodMule() > 0) {
                        //TODO: alert user they have lost mule
                        curPlayer.setFoodMule(curPlayer.getFoodMule() - 1);
                    }
                } else {
                    //Place corresponding MULE else do nothing
                    //TODO: FIX HARDCODED TEST
                    //if (curPlayer.getFood() > 0 && curPlayer.getMule > 0)
                    if (curPlayer.getFoodMule() > 0) {
                        curPlayer.setFoodMule(curPlayer.getFoodMule() - 1);
                        curTile.setMule(new Mule(FOOD, curPlayer, curTile.getTerrain()));
                        //TODO: update UI to draw mule
                        updateTile(x, y, MULE);
                    }
                }
                updatePlayer(curPlayer.getNumber());
                break;
            case ENERGY:
                //Checks if player has lost MULE
                if (curTile.getMule() != null || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    //Decrement if MULE count is above 0
                    if (curPlayer.getEnergyMule() > 0) {
                        curPlayer.setEnergyMule(curPlayer.getEnergyMule() - 1);
                    }
                } else {
                    //Place corresponding MULE else do nothing
                    //todo: remove hardcoded test
                    //if (curPlayer.getEnergy() > 0 && curPlayer.getMule() > 0) {
                    if (curPlayer.getEnergyMule() > 0) {
                        curPlayer.setEnergyMule(curPlayer.getEnergyMule() - 1);
                        curTile.setMule(new Mule(Mule.MULETYPE.ENERGY, curPlayer, curTile.getTerrain()));
                        updateTile(x, y, MULE);
                    }
                }
                updatePlayer(curPlayer.getNumber());
                break;
            case ORE:
                //Checks if player has lost MULE
                if (curTile.hasMule() || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    //Decrement if MULE count is above 0
                    if (curPlayer.getSmithoreMule() > 0) {
                        curPlayer.setSmithoreMule(curPlayer.getSmithoreMule() - 1);
                    }
                } else {
                    //Place corresponding MULE else do nothing
                    if (curPlayer.getSmithoreMule() > 0) {
                        curPlayer.setSmithoreMule(curPlayer.getSmithoreMule() - 1);
                        curTile.setMule(new Mule(Mule.MULETYPE.ORE, curPlayer, curTile.getTerrain()));
                        updateTile(x, y, MULE);
                    }
                }
                updatePlayer(curPlayer.getNumber());
                break;
            case CRYSTITE:
                //Checks if player has lost MULE
                if (curTile.hasMule() || curTile.getOwner() == null || !curPlayer.equals(curTile.getOwner())) {
                    //Decrement if MULE count is above 0
                    if (curPlayer.getCrystiteMule() > 0) {
                        curPlayer.setCrystiteMule(curPlayer.getCrystiteMule() - 1);
                    }
                } else {
                    //Place corresponding MULE else do nothing
                    if (curPlayer.getCrystiteMule() > 0) {
                        curPlayer.setCrystiteMule(curPlayer.getCrystiteMule() - 1);
                        curTile.setMule(new Mule(Mule.MULETYPE.CRYSTITE, curPlayer, curTile.getTerrain()));
                        updateTile(x, y, MULE);
                    }
                }
                updatePlayer(curPlayer.getNumber());
                break;
            default:
                System.out.println("Error placing MULE");
                System.exit(1);
        }
    }


    /**
     * Updates a tile displayed on the screen (adds a colored border based on the owner)
     * <p>
     * If the tile does not have an owner, this method does nothing
     *
     * @param x The X position of the tile
     * @param y The Y position of the tile
     */
    private void updateTile(int x, int y, Mule.MULETYPE MULE) {
        // Get current tile, check what border color should be
        Tile currentTile = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles()[x][y];
        if (currentTile.getOwner() == null) {
            System.err.println("Tile has no owner. Likely due to land purchase failing");
            return; // No need to update border color of tile with no owner
        }

        // Get the Node that contains this tile
        Node result = null;
        ObservableList<Node> children = Pane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                result = node;
                break;
            }
        }
        String color = currentTile.getOwner().getColor().toString();
        String styles = "-fx-border-color: " + color + ";\n" +
                "-fx-border-width: 5;\n" +
                "-fx-border-style: solid;\n";
        switch (MULE) {
            case FOOD:
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/plain_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/river_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_food_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_food_tile.png")), y, x);
                }
                break;
            case ENERGY:
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/plain_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/river_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_energy_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_energy_tile.png")), y, x);
                }
                break;
            case ORE:
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/plain_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/river_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_smithore_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_smithore_tile.png")), y, x);
                }
                break;
            case CRYSTITE:
                if (currentTile.getTerrain() == Tile.Terrain.PLAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/plain_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.RIVER) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/river_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain1_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain2_crystite_tile.png")), y, x);
                } else if (currentTile.getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    Pane.add(hBoxMaker(styles, imageMaker("/img/mountain3_crystite_tile.png")), y, x);
                }
                break;
            default:
                if (result instanceof HBox) {
                    result.setStyle("-fx-border-color: " + color + ";\n" +
                            "-fx-border-width: 5;\n" +
                            "-fx-border-style: solid;\n");
                }
        }
    }

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

    public static VBox vBoxMaker(Node... nodes) {
        VBox vBox = new VBox();
        vBox.setSpacing(20.0);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(nodes);

        return vBox;
    }

    public ImageView imageMaker(String path) {
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream(path)));
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, tileClickHandler);
        image.setFitHeight(80);
        image.setFitWidth(80);

        return image;
    }

    //TODO: Stage maker?
    //TODO: BUG Player ordering when land selction phase
}
