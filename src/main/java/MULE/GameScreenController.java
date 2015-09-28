package MULE;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import java.util.Random;

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

    private final Image mountain1 = new Image(getClass().getResourceAsStream("/img/mountain1_tile.png"));
    private final Image mountain2 = new Image(getClass().getResourceAsStream("/img/mountain2_tile.png"));
    private final Image mountain3 = new Image(getClass().getResourceAsStream("/img/mountain3_tile.png"));
    private final Image plain = new Image(getClass().getResourceAsStream("/img/plain_tile.png"));
    private final Image river = new Image(getClass().getResourceAsStream("/img/river_tile.png"));
    private final Image town = new Image(getClass().getResourceAsStream("/img/town_tile.png"));

    private final EventHandler<MouseEvent> purchaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            // Setup dialog box to purchase a property
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Purchase a Property");
            dialogStage.initModality(Modality.WINDOW_MODAL);


            Label purchaseLandLabel = new Label("Would you like to purchase this property?");
            purchaseLandLabel.setAlignment(Pos.CENTER);

            Button yesBtn = new Button("Yes");
            Button noBtn = new Button("No");
            yesBtn.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent arg0) {
                    buyLand((GridPane.getRowIndex(((javafx.scene.Node) event.getSource()).getParent())),
                            (GridPane.getColumnIndex(((javafx.scene.Node) event.getSource()).getParent())));
                    dialogStage.close();
                }
            });
            noBtn.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent arg0) {
                    dialogStage.close();
                }
            });

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(40.0);
            hBox.getChildren().addAll(yesBtn, noBtn);


            VBox vBox = new VBox();
            vBox.setSpacing(40.0);
            vBox.getChildren().addAll(purchaseLandLabel, hBox);

            dialogStage.setScene(new Scene(vBox));
            dialogStage.show();
        }
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

    @FXML
    public void initialize() {
        displayMap();
        setPlayerStats();
        //Setting this in PlayerController so only happens at beginning of game
        //GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[0];
        currentPlayer.setText(GamePlay.currentPlayer.getName() + ", take your turn.");
    }

    /**
     * Gets called when a player passes during the land selection phase
     *
     * @param event The event from the button click
     */
    @FXML
    private void handleEndTurn(ActionEvent event) {
        //TODO: Keep track of which players have passed. When all players pass, end land selection
        //TODO: After land selection, begin game (calling GamePlay.startGame())
        if ((GamePlay.currentPlayer.getNumber() + 1) == GamePlay.GAMECONFIG.getNumPlayers()) {
            GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[0];
            currentPlayer.setText(GamePlay.currentPlayer.getName() + ", take your turn.");
            return;
        }
        GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[GamePlay.currentPlayer.getNumber() + 1];
        currentPlayer.setText(GamePlay.currentPlayer.getName() + ", take your turn.");
    }

    /**
     * Displays the entire map on the screen. Adds the images to the GameScreen's GridPane
     */
    private void displayMap() {
        anchorPane.setStyle("-fx-background-color: #83d95e;");
        Tile[][] board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                HBox box = new HBox();
                ImageView image = null;
                switch (board[i][j].getTerrain()) {
                    case ONEMOUNTAIN:
                        image = new ImageView(mountain1);
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                        break;
                    case TWOMOUNTAIN:
                        image = new ImageView(mountain2);
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                        break;
                    case THREEMOUNTAIN:
                        image = new ImageView(mountain3);
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                        break;
                    case PLAIN:
                        image = new ImageView(plain);
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                        break;
                    case RIVER:
                        image = new ImageView(river);
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                        break;
                    case TOWN:
                        image = new ImageView(town);
                        image.addEventHandler(MouseEvent.MOUSE_CLICKED, townClickHandler);
                        break;
                    default:
                        System.err.println("ERROR: Invalid Terrain when displaying map!");
                        System.exit(1);
                }
                image.setFitHeight(80);
                image.setFitWidth(80);
                box.getChildren().add(image);
                if (board[i][j].getOwner() != null) {
                    String color = board[i][j].getOwner().getColor().toString();
                    box.setStyle("-fx-border-color: " + color + ";\n" +
                            "-fx-border-width: 5;\n" +
                            "-fx-border-style: solid;\n");
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
            mule.setText(currentPlayer.getMule() + " Mule");
        }
    }

    private void updatePlayer(int playerNum) {
        String money = "$" + GamePlay.GAMECONFIG.players[playerNum].getMoney();
        Pane playerStatsPane = ((Pane) playerStatsGrid.getChildren().get(playerNum));
        playerMoney = (Text) playerStatsPane.getChildren().get(1);
        playerMoney.setText(money);
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
        //When the player already owns 2 land tiles, buying land costs money
        if (GamePlay.currentPlayer.getMoney() >= price && current.getOwner() == null &&
                GamePlay.currentPlayer.getNumLand() >= 2 && current.getTerrain() != Tile.Terrain.TOWN) {
            current.setOwner(GamePlay.currentPlayer);
            GamePlay.currentPlayer.setMoney(GamePlay.currentPlayer.getMoney() - price);
            updatePlayer(GamePlay.currentPlayer.getNumber());
            updateTile(x, y);
            if (GamePlay.round == 0) {
                GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[(GamePlay.currentPlayer.getNumber() + 1) % GamePlay.GAMECONFIG.getNumPlayers()];
                currentPlayer.setText(GamePlay.currentPlayer.getName() + ", take your turn.");
            }
        } else if (current.getOwner() == null && current.getTerrain() != Tile.Terrain.TOWN && GamePlay.currentPlayer.getNumLand() < 2) {
            current.setOwner(GamePlay.currentPlayer);
            GamePlay.currentPlayer.incrementLand();
            updateTile(x, y);
            if (GamePlay.round == 0) {
                GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[(GamePlay.currentPlayer.getNumber() + 1) % GamePlay.GAMECONFIG.getNumPlayers()];
                currentPlayer.setText(GamePlay.currentPlayer.getName() + ", take your turn.");
            }
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
    private void updateTile(int x, int y) {
        // Get current tile, check what border color should be
        Tile currentTile = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles()[x][y];
        if (currentTile.getOwner() == null) {
            System.err.println("Tile has no owner. Likely due to land purchase failing");
            return; // No need to update border color of tile with no owner
        }
        String color = currentTile.getOwner().getColor().toString();

        // Get the Node that contains this tile
        Node result = null;
        ObservableList<Node> children = Pane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                result = node;
                break;
            }
        }

        // Add colored border to image
        if (result instanceof HBox) {
            result.setStyle("-fx-border-color: " + color + ";\n" +
                    "-fx-border-width: 5;\n" +
                    "-fx-border-style: solid;\n");
        } else {
            System.err.println("ERROR: Not an HBox.");
        }
    }
}
