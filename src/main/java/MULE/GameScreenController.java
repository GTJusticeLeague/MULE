package MULE;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameScreenController {

    @FXML
    private GridPane Pane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Text Player1,Player2,Player3,Player4;
    private Text score1, score2, score3, score4;
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

        Player1.setText(GamePlay.GAMECONFIG.players[0].getName());
        Player2.setText(GamePlay.GAMECONFIG.players[1].getName());
        if (GamePlay.GAMECONFIG.getNumPlayers()  > 2) {
            Player3.setText(GamePlay.GAMECONFIG.players[2].getName());
            if (GamePlay.GAMECONFIG.getNumPlayers() > 3) {
                Player4.setText(GamePlay.GAMECONFIG.players[3].getName());
            }
        }

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

}
