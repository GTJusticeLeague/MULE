package MULE;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameScreenController {

    @FXML
    private ImageView tile20;

    @FXML
    private ImageView tile42;

    @FXML
    private ImageView tile21;

    @FXML
    private ImageView tile43;

    @FXML
    private ImageView tile00;

    @FXML
    private ImageView tile22;

    @FXML
    private ImageView tile44;

    @FXML
    private ImageView tile01;

    @FXML
    private ImageView tile23;

    @FXML
    private ImageView tile45;

    @FXML
    private ImageView tile40;

    @FXML
    private ImageView tile41;

    @FXML
    private ImageView tile06;

    @FXML
    private ImageView tile28;

    @FXML
    private ImageView tile07;

    @FXML
    private ImageView tile08;

    @FXML
    private ImageView tile02;

    @FXML
    private ImageView tile24;

    @FXML
    private ImageView tile46;

    @FXML
    private ImageView tile03;

    @FXML
    private ImageView tile25;

    @FXML
    private ImageView tile47;

    @FXML
    private ImageView tile04;

    @FXML
    private ImageView tile26;

    @FXML
    private ImageView tile48;

    @FXML
    private ImageView tile05;

    @FXML
    private ImageView tile27;

    @FXML
    private ImageView tile31;

    @FXML
    private ImageView tile10;

    @FXML
    private ImageView tile32;

    @FXML
    private ImageView tile11;

    @FXML
    private ImageView tile33;

    @FXML
    private ImageView tile12;

    @FXML
    private ImageView tile34;

    @FXML
    private ImageView tile30;

    @FXML
    private ImageView tile17;

    @FXML
    private ImageView tile18;

    @FXML
    private ImageView tile13;

    @FXML
    private ImageView tile35;

    @FXML
    private ImageView tile14;

    @FXML
    private ImageView tile36;

    @FXML
    private ImageView tile15;

    @FXML
    private ImageView tile37;

    @FXML
    private ImageView tile16;

    @FXML
    private ImageView tile38;

    @FXML
    private GridPane Pane;

    Image mountain1 = new Image("/resources/mountain1_tile.png");
    Image mountain2 = new Image("mountain2_tile.png");
    Image mountain3 = new Image("mountain3_tile.png");
    Image plain = new Image("plain_tile.png");
    Image river = new Image("river_tile.png");
    Image town = new Image("town_tile.png");


    @FXML
    public void initialize(){
        buildMap();
    }

    private void buildMap() {
        Tile[][] board = GamePlay.GAMECONFIG.getGAMEBOARD().getTiles();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getTerrain() == Tile.Terrain.ONEMOUNTAIN ) {
                    Pane.add(new ImageView(mountain1), j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.PLAIN) {
                    Pane.add(new ImageView(plain), j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.RIVER) {
                    Pane.add(new ImageView(river), j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.TWOMOUNTAIN) {
                    Pane.add(new ImageView(mountain2), j, i);
                } else if (board[i][j].getTerrain() == Tile.Terrain.THREEMOUNTAIN) {
                    Pane.add(new ImageView(mountain3), j, i);
                } else {
                    //town
                    Pane.add(new ImageView(town), j, i);
                }
            }
        }

    }

}
