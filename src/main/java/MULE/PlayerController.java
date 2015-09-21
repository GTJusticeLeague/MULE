package MULE;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerController {

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    private TextField player3Name;

    @FXML
    private TextField player4Name;

    @FXML
    private ChoiceBox player1Race;

    @FXML
    private ChoiceBox player2Race;

    @FXML
    private ChoiceBox player3Race;

    @FXML
    private ChoiceBox player4Race;

    @FXML
    private ChoiceBox player1Color;

    @FXML
    private ChoiceBox player2Color;

    @FXML
    private ChoiceBox player3Color;

    @FXML
    private ChoiceBox player4Color;

    @FXML
    private GridPane playerGrid;

    @FXML
    private Button startGameButton;

    @FXML
    private Label errorMsg;

    public PlayerController() {

    }

    @FXML
    private void initialize() {

        loadRaceColorValues();

        // Shows player config based on number of players chosen.
        if (GamePlay.GAMECONFIG.getNumPlayers() == 2) {

            playerGrid.getChildren().remove(2);
            playerGrid.getChildren().remove(2);

        } else if (GamePlay.GAMECONFIG.getNumPlayers() == 3) {
            playerGrid.getChildren().remove(3);

        }


    }

    private void loadRaceColorValues() {

        errorMsg.setText("");

        player1Race.setItems(FXCollections.observableArrayList(
                "Human", "Flapper", "Bonzoid", "Ugaite", "Buzzite"));
        player1Race.getSelectionModel().selectFirst();
        player1Color.setItems(FXCollections.observableArrayList(
                "Red", "Green", "Blue", "Yellow", "Purple"));

        player1Color.getSelectionModel().selectFirst();

        player2Race.setItems(FXCollections.observableArrayList(
                "Human", "Flapper", "Bonzoid", "Ugaite", "Buzzite"));
        player2Race.getSelectionModel().selectFirst();
        player2Color.setItems(FXCollections.observableArrayList(
                "Red", "Green", "Blue", "Yellow", "Purple"));
        player2Color.getSelectionModel().selectFirst();

        player3Race.setItems(FXCollections.observableArrayList(
                "Human", "Flapper", "Bonzoid", "Ugaite", "Buzzite"));
        player3Race.getSelectionModel().selectFirst();
        player3Color.setItems(FXCollections.observableArrayList(
                "Red", "Green", "Blue", "Yellow", "Purple"));
        player3Color.getSelectionModel().selectFirst();

        player4Race.setItems(FXCollections.observableArrayList(
                "Human", "Flapper", "Bonzoid", "Ugaite", "Buzzite"));
        player4Race.getSelectionModel().selectFirst();
        player4Color.setItems(FXCollections.observableArrayList(
                "Red", "Green", "Blue", "Yellow", "Purple"));
        player4Color.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleStartGame(ActionEvent event) throws IOException {
        boolean inputMismatch = false;

        Player firstPlayer = new Player(player1Name.getText(),
                Player.Race.values()[player1Race.getSelectionModel().getSelectedIndex()],
                Player.Color.values()[player1Color.getSelectionModel().getSelectedIndex()], 1);
        GamePlay.GAMECONFIG.players[0] = firstPlayer;

        Player secondPlayer = new Player(player2Name.getText(),
                Player.Race.values()[player2Race.getSelectionModel().getSelectedIndex()],
                Player.Color.values()[player2Color.getSelectionModel().getSelectedIndex()], 2);
        GamePlay.GAMECONFIG.players[1] = secondPlayer;


        if (GamePlay.GAMECONFIG.getNumPlayers() > 2) {
            Player thirdPlayer = new Player(player3Name.getText(),
                    Player.Race.values()[player3Race.getSelectionModel().getSelectedIndex()],
                    Player.Color.values()[player3Color.getSelectionModel().getSelectedIndex()], 3);
            GamePlay.GAMECONFIG.players[2] = thirdPlayer;

            if (GamePlay.GAMECONFIG.getNumPlayers() > 3) {
                Player fourthPlayer = new Player(player4Name.getText(),
                        Player.Race.values()[player4Race.getSelectionModel().getSelectedIndex()],
                        Player.Color.values()[player4Color.getSelectionModel().getSelectedIndex()], 4);
                GamePlay.GAMECONFIG.players[3] = fourthPlayer;

            }
        }

        ArrayList<Player.Color> colors = new ArrayList<Player.Color>(Player.Color.values().length);
        colors.add(Player.Color.RED);
        colors.add(Player.Color.GREEN);
        colors.add(Player.Color.BLUE);
        colors.add(Player.Color.YELLOW);
        colors.add(Player.Color.PURPLE);
        for (int i = 0; i < GamePlay.GAMECONFIG.getNumPlayers(); i++) {
            Player[] players = GamePlay.GAMECONFIG.players;
            if (colors.contains(players[i].getColor())) {
                colors.remove(players[i].getColor());
            } else {
                inputMismatch = true;
                errorMsg.setText("ERROR: Two or more players appear to have the same color.");
            }
        }

        if (!inputMismatch) {
            // Move to the next scene (player configuration)
            Stage stage = (Stage) startGameButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/gameScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


}
