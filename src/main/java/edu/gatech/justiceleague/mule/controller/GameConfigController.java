package edu.gatech.justiceleague.mule.controller;

import edu.gatech.justiceleague.mule.model.GameConfig;
import edu.gatech.justiceleague.mule.model.GamePlay;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


import java.io.IOException;

public class GameConfigController {

    @FXML
    private Button gameConfigButton;

    @FXML
    private Button loadGameButton;

    @FXML
    private ToggleGroup numPlayers;

    @FXML
    private ToggleGroup mapType;

    @FXML
    private ToggleGroup difficulty;

    @FXML
    private void handleLoadGame() throws IOException {
        Label saveGame = new Label("Select a game to load.");
        saveGame.setAlignment(Pos.CENTER);

        Button load = new Button("Load");
        Button cancel = new Button("Cancel");
        SplitMenuButton gameList = new SplitMenuButton();
        gameList.setPrefWidth(500);
        gameList.setText("Select Game");
        gameList.getItems().addAll(new MenuItem("Game1"), new MenuItem("Game2"), new MenuItem("Game3"));

        final Stage dialogStage = GameScreenController.stageMaker("Load a Game", GameScreenController.vBoxMaker(saveGame, gameList, GameScreenController.hBoxMaker(null, load, cancel)));
        dialogStage.setWidth(350);
        dialogStage.setHeight(150);

        load.setOnAction(arg0 -> {
            // todo: code for loading a game should be here!
            dialogStage.close();

        });

        cancel.setOnAction(arg0 -> dialogStage.close());
        dialogStage.show();
    }

    /**
     * Configures the game based on selections on
     * game config screen.
     *
     * @param event event for gameconfig
     * @throws IOException
     */
    @FXML
    private void handleGameConfigButton() throws IOException {
        GameConfig.Difficulty gameDifficulty;
        GameConfig.MapType gameMapType;

        // Get selected difficulty from the radio buttons
        String selectedDifficulty = ((RadioButton) difficulty.getSelectedToggle()).getText();
        switch (selectedDifficulty) {
            case "Beginner":
                gameDifficulty = GameConfig.Difficulty.BEGINNER;
                break;
            case "Intermediate":
                gameDifficulty = GameConfig.Difficulty.INTERMEDIATE;
                break;
            default:
                gameDifficulty = GameConfig.Difficulty.ADVANCED;
                break;
        }

        // Get selected map type from the radio buttons
        String selectedMapType = ((RadioButton) mapType.getSelectedToggle()).getText();
        if (selectedMapType.equals("Standard")) {
            gameMapType = GameConfig.MapType.STANDARD;
        } else {
            gameMapType = GameConfig.MapType.RANDOM;
        }

        GamePlay.setGameConfig(new GameConfig(gameDifficulty, gameMapType,
                Integer.parseInt(((RadioButton) numPlayers.getSelectedToggle()).getText())));

        // Move to the next scene (player configuration)
        Stage stage = (Stage) gameConfigButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/playerConfiguration.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
