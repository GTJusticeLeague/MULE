package MULE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private  GridPane playerStatsGrid;

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

    Image mountain1 = new Image(getClass().getResourceAsStream("/mountain1_tile.png"));
    Image mountain2 = new Image(getClass().getResourceAsStream("/mountain2_tile.png"));
    Image mountain3 = new Image(getClass().getResourceAsStream("/mountain3_tile.png"));
    Image plain = new Image(getClass().getResourceAsStream("/plain_tile.png"));
    Image river = new Image(getClass().getResourceAsStream("/river_tile.png"));
    Image town = new Image(getClass().getResourceAsStream("/town_tile.png"));

    Tile[][] board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();


    EventHandler<MouseEvent> purchaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Text popup = new Text(100, 100, "Would you like to purchase this property?");
            Button yes = new Button("Yes");
            Button no = new Button("No");

            yes.setOnAction(event1 -> {

            buyLand((GridPane.getRowIndex((javafx.scene.Node) event.getSource())),
                    (GridPane.getColumnIndex((javafx.scene.Node) event.getSource())));

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

    @FXML
    public void initialize() {
        buildMap();
        setPlayerStats();
        GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[0];
    }

    @FXML
    private void handleEndTurn(ActionEvent event) {
        if (GamePlay.currentPlayer.getNumber() == GamePlay.GAMECONFIG.getNumPlayers()) {
            GamePlay.round++;
            GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[0];
            return;
        }
        GamePlay.currentPlayer = GamePlay.GAMECONFIG.players[GamePlay.currentPlayer.getNumber() + 1];
    }

    private void buildMap() {
        anchorPane.setStyle("-fx-background-color: #83d95e;");
        board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    ImageView mtn1 = new ImageView(mountain1);
                    mtn1.setFitHeight(90);
                    mtn1.setFitWidth(90);
                    mtn1.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                    Pane.add(mtn1, j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.PLAIN) {
                    ImageView ImagePlain = new ImageView(plain);
                    ImagePlain.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                    Pane.add(ImagePlain, j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.RIVER) {
                    ImageView ImageRiver = new ImageView(river);
                    ImageRiver.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                    Pane.add(ImageRiver, j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    ImageView mtn2 = new ImageView(mountain2);
                    mtn2.setFitHeight(90);
                    mtn2.setFitWidth(90);
                    mtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                    Pane.add(mtn2, j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    ImageView mtn3 = new ImageView(mountain3);
                    mtn3.setFitHeight(90);
                    mtn3.setFitWidth(90);
                    mtn3.addEventHandler(MouseEvent.MOUSE_CLICKED, purchaseHandler);
                    Pane.add(mtn3, j, i);
                } else {
                    //town
                    ImageView townImage = new ImageView(town);
                    townImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<javafx.scene.input.MouseEvent>() {
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
                    });
                    Pane.add(townImage, j, i);
                }
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

    public void UpdatePlayer(int playerNum) {
        String money = "$" + GamePlay.GAMECONFIG.players[playerNum].getMoney();
        Pane playerStatsPane = ((Pane) playerStatsGrid.getChildren().get(playerNum));
        playerMoney = (Text) playerStatsPane.getChildren().get(1);
        playerMoney.setText(money);
    }

    public void buyLand(int x, int y) {
        Tile current = board[x][y];
        Random rand = new Random();
        int price = 300 + GamePlay.round * rand.nextInt(101);

        if (GamePlay.currentPlayer.getMoney() >= price && current.getOwner() == null || GamePlay.round > 3) {
            current.setOwner(GamePlay.currentPlayer);
            GamePlay.currentPlayer.setMoney(GamePlay.currentPlayer.getMoney() - price);
            UpdatePlayer(GamePlay.currentPlayer.getNumber());
        }
         //INSERT UPDATE MAP HERE

    }
}
