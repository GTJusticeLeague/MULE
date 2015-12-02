package edu.gatech.justiceleague.mule.controller;

import edu.gatech.justiceleague.mule.model.GamePlay;
import edu.gatech.justiceleague.mule.model.Player;
import javafx.collections.FXCollections;
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

public class PlayerConfigController {

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

    /**
     * Constructor with nothing in it. Spooky.
     */
    public PlayerConfigController() {

    }

    /**
     * Initializes player configuration screen
     */
    @FXML
    private void initialize() {

        loadRaceColorValues();

        // Shows player config based on number of players chosen.
        if (GamePlay.getGameConfig().getNumPlayers() == 2) {

            playerGrid.getChildren().remove(2);
            playerGrid.getChildren().remove(2);

        } else if (GamePlay.getGameConfig().getNumPlayers() == 3) {
            playerGrid.getChildren().remove(3);

        }


    }

    /**
     * Load up race and color menus for player config screen
     */
    private void loadRaceColorValues() {
        String humanString = "Human";
        String flapperString = "Flapper";
        String bonzoidString = "Bonzoid";
        String ugaiteString = "Ugaite";
        String buzziteString = "Buzzite";
        String badonkString = "Badonk";
        String dothrakiString = "Dothraki";

        String redString = "Red";
        String greenString = "Green";
        String blueString = "Blue";
        String yellowString = "Yellow";
        String purpleString = "Purple";

        errorMsg.setText("");

        player1Race.setItems(FXCollections.observableArrayList(
                humanString, flapperString, bonzoidString, ugaiteString, buzziteString, badonkString, dothrakiString));
        player1Race.getSelectionModel().selectFirst();
        player1Color.setItems(FXCollections.observableArrayList(
                redString, greenString, blueString, yellowString, purpleString));

        player1Color.getSelectionModel().selectFirst();

        player2Race.setItems(FXCollections.observableArrayList(
                humanString, flapperString, bonzoidString, ugaiteString, buzziteString, badonkString, dothrakiString));
        player2Race.getSelectionModel().selectFirst();
        player2Color.setItems(FXCollections.observableArrayList(
                redString, greenString, blueString, yellowString, purpleString));
        player2Color.getSelectionModel().selectFirst();

        player3Race.setItems(FXCollections.observableArrayList(
                humanString, flapperString, bonzoidString, ugaiteString, buzziteString, badonkString, dothrakiString));
        player3Race.getSelectionModel().selectFirst();
        player3Color.setItems(FXCollections.observableArrayList(
                redString, greenString, blueString, yellowString, purpleString));
        player3Color.getSelectionModel().selectFirst();

        player4Race.setItems(FXCollections.observableArrayList(
                humanString, flapperString, bonzoidString, ugaiteString, buzziteString, badonkString, dothrakiString));
        player4Race.getSelectionModel().selectFirst();
        player4Color.setItems(FXCollections.observableArrayList(
                redString, greenString, blueString, yellowString, purpleString));
        player4Color.getSelectionModel().selectFirst();
    }

    /**
     * Sets player configurations, determines if configurations are valid,
     * loads game screen.
     *
     * @throws IOException
     */
    @FXML
    private void handleStartGame() throws IOException {
        boolean inputMismatch = false;
        Player[] players = new Player[GamePlay.getGameConfig().getNumPlayers()];

        Player firstPlayer = new Player(player1Name.getText(),
                Player.Race.values()[player1Race.getSelectionModel().getSelectedIndex()],
                Player.Color.values()[player1Color.getSelectionModel().getSelectedIndex()], 0);
        players[0] = firstPlayer;

        Player secondPlayer = new Player(player2Name.getText(),
                Player.Race.values()[player2Race.getSelectionModel().getSelectedIndex()],
                Player.Color.values()[player2Color.getSelectionModel().getSelectedIndex()], 1);
        players[1] = secondPlayer;


        if (GamePlay.getGameConfig().getNumPlayers() > 2) {
            Player thirdPlayer = new Player(player3Name.getText(),
                    Player.Race.values()[player3Race.getSelectionModel().getSelectedIndex()],
                    Player.Color.values()[player3Color.getSelectionModel().getSelectedIndex()], 2);
            players[2] = thirdPlayer;

            if (GamePlay.getGameConfig().getNumPlayers() > 3) {
                Player fourthPlayer = new Player(player4Name.getText(),
                        Player.Race.values()[player4Race.getSelectionModel().getSelectedIndex()],
                        Player.Color.values()[player4Color.getSelectionModel().getSelectedIndex()], 3);
                players[3] = fourthPlayer;

            }
        }
        GamePlay.getGameConfig().setPlayers(players);

        ArrayList<Player.Color> colors = new ArrayList<>(Player.Color.values().length);
        colors.add(Player.Color.RED);
        colors.add(Player.Color.GREEN);
        colors.add(Player.Color.BLUE);
        colors.add(Player.Color.YELLOW);
        colors.add(Player.Color.PURPLE);
        for (int i = 0; i < GamePlay.getGameConfig().getNumPlayers(); i++) {
            if (colors.contains(players[i].getColor())) {
                colors.remove(players[i].getColor());
            } else {
                inputMismatch = true;
                errorMsg.setText("ERROR: Two or more players appear to have the same color.");
            }
        }

        if (!inputMismatch) {
            // Set initial player (for land selection)
            GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[0]);
            // Move to the next scene (player configuration)
            Stage stage = (Stage) startGameButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


}
