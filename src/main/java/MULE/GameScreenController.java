package MULE;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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

    Image mountain1 = new Image(getClass().getResourceAsStream("/mountain1_tile.png"));
    Image mountain2 = new Image(getClass().getResourceAsStream("/mountain2_tile.png"));
    Image mountain3 = new Image(getClass().getResourceAsStream("/mountain3_tile.png"));
    Image plain = new Image(getClass().getResourceAsStream("/plain_tile.png"));
    Image river = new Image(getClass().getResourceAsStream("/river_tile.png"));
    Image town = new Image(getClass().getResourceAsStream("/town_tile.png"));


    @FXML
    public void initialize() {
        buildMap();
        setPlayerStats();
    }

    private void buildMap() {
        anchorPane.setStyle("-fx-background-color: #83d95e;");
        Tile[][] board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTerrain() == Tile.Terrain.ONEMOUNTAIN) {
                    ImageView mtn1 = new ImageView(mountain1);
                    mtn1.setFitHeight(90);
                    mtn1.setFitWidth(90);
                    Pane.add(mtn1, j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.PLAIN) {
                    Pane.add(new ImageView(plain), j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.RIVER) {
                    Pane.add(new ImageView(river), j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    ImageView mtn2 = new ImageView(mountain2);
                    mtn2.setFitHeight(90);
                    mtn2.setFitWidth(90);
                    Pane.add(mtn2, j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    ImageView mtn3 = new ImageView(mountain3);
                    mtn3.setFitHeight(90);
                    mtn3.setFitWidth(90);
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

            //set the name and money for specific pane
            playerName = (Text) playerStatGridPane.getChildren().get(0);
            playerName.setText(currentPlayer.getName());
            playerMoney = (Text) playerStatGridPane.getChildren().get(1);
            int money = initializeMoney(players[i]);
            playerMoney.setText("$" + money);

        }

    }

    /**
     * Helper method to initial money amount
     * @param player player
     * @return money
     */
    private int initializeMoney(Player player) {
        int money;
        if (player.getRace() == Player.Race.HUMAN) {
            money = 600;
        } else if (player.getRace() == Player.Race.FLAPPER) {
            money = 1600;
        } else {
            money = 1000;
        }
        return money;
    }

}