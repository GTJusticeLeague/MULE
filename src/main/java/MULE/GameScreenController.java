package MULE;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.text.Text;
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

    private final Image mountain1 = new Image(getClass().getResourceAsStream("/mountain1_tile.png"));
    private final Image mountain2 = new Image(getClass().getResourceAsStream("/mountain2_tile.png"));
    private final Image mountain3 = new Image(getClass().getResourceAsStream("/mountain3_tile.png"));
    private final Image plain = new Image(getClass().getResourceAsStream("/plain_tile.png"));
    private final Image river = new Image(getClass().getResourceAsStream("/river_tile.png"));
    private final Image town = new Image(getClass().getResourceAsStream("/town_tile.png"));

    private final EventHandler<MouseEvent> purchaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Text popup = new Text(100, 100, "Would you like to purchase this property?");
            Button yes = new Button("Yes");
            Button no = new Button("No");

            yes.setOnAction(event1 -> {
                buyLand((GridPane.getRowIndex(((javafx.scene.Node) event.getSource()).getParent())),
                        (GridPane.getColumnIndex(((javafx.scene.Node) event.getSource()).getParent())));

                Pane.getChildren().remove(popup);
                Pane.getChildren().remove(yes);
                Pane.getChildren().remove(no);
            });
            no.setOnAction(event1 -> {
                Pane.getChildren().remove(popup);
                Pane.getChildren().remove(yes);
                Pane.getChildren().remove(no);
            });
            Pane.add(popup, 98, 100);
            Pane.add(yes, 98, 101);
            Pane.add(no, 99, 101);
        }
    };

    private final EventHandler<MouseEvent> townClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Stage stage = (Stage) Pane.getScene().getWindow();
            Parent root = null;
            try {
                // Load the town when the town is clicked
                root = FXMLLoader.load(getClass().getResource("/town.fxml"));
            } catch (java.io.IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            event.consume();
        }
    };

    @FXML
    public void initialize() {
        displayMap();
        setPlayerStats();
        GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[0];
    }

    @FXML
    private void handleEndTurn(ActionEvent event) {
        if ((GamePlay.currentPlayer.getNumber() + 1) == GamePlay.GAMECONFIG.getNumPlayers()) {
            //TODO: ADD AUCTION LOGIC HERE
            //auction();
            GamePlay.round++;
            GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[0];
            return;
        }
        GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[GamePlay.currentPlayer.getNumber()+ 1];
    }

//
//    private void auction() {
//        Tile property = getFreeTile();
//        Text popup = new Text("WELCOME TO THE AUCTION!");
//        Player[] players = GamePlay.GAMECONFIG.players;
//        Pair<Player, Integer> bidder = null;
//
//        while(property.getOwner() == null) {
//            for (int i = 0; i < GamePlay.GAMECONFIG.getNumPlayers(); i++) {
//                //TODO: Add x, y coordinates to tile constructor, and toString method to Tile
//                String text = "How much is your bid for: " + property.getTerrain() + " (-1 to pass) ";
//                Text info = new Text(text);
//                TextField textField = new TextField();
//                Button button = new Button("Input");
//                button.setOnAction(event -> {
//                    int bid = Integer.parseInt(textField.getText());
//
//                });
//            }
//        }
//
//    }

    /*
        Returns a random Tile which has no owner
     */
    private Tile getFreeTile() {
        Random rand = new Random();
        Tile[][] board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();
        Tile randTile = null;

        while (randTile == null) {
            int x = rand.nextInt(board.length);
            int y = rand.nextInt(board[0].length);

            if (board[x][y].getOwner() == null) {
                randTile = board[x][y];
            }
        }

        return randTile;
    }

    /**
     * Displays the map on the screen. Adds the images to the GameScreen's GridPane
     */
    private void displayMap() {
        anchorPane.setStyle("-fx-background-color: #83d95e;");
        Tile[][] board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                HBox box = new HBox();
                box.setMaxHeight(90);
                box.setMaxWidth(90);
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
                image.setFitHeight(90);
                image.setFitWidth(90);
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

    private void buyLand(int x, int y) {
        Tile current = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles()[x][y];
        Random rand = new Random();
        int price = 300 + GamePlay.round * rand.nextInt(101);

        if (GamePlay.currentPlayer == null) {
            System.err.println("ERROR: Current player null!");
            return;
        }
        //When the round is above 2 purchasing property costs money
        if (GamePlay.currentPlayer.getMoney() >= price && current.getOwner() == null &&
                GamePlay.currentPlayer.getNumLand() >= 2 && current.getTerrain() != Tile.Terrain.TOWN) {
            current.setOwner(GamePlay.currentPlayer);
            GamePlay.currentPlayer.setMoney(GamePlay.currentPlayer.getMoney() - price);
            updatePlayer(GamePlay.currentPlayer.getNumber());
        } else if(current.getOwner() == null && current.getTerrain() != Tile.Terrain.TOWN && GamePlay.currentPlayer.getNumLand() < 2) {
            current.setOwner(GamePlay.currentPlayer);
            GamePlay.currentPlayer.incrementLand();
        }
        // Update the board
        updateTile(x, y);
        GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[(GamePlay.currentPlayer.getNumber()+ 1) % GamePlay.GAMECONFIG.getNumPlayers()];
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
