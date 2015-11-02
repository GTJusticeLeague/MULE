package edu.gatech.justiceleague.mule.controller;

import edu.gatech.justiceleague.mule.model.GameConfig;
import edu.gatech.justiceleague.mule.model.GamePlay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class GameConfigController {

    @FXML
    private Button gameConfigButton;

    @FXML
    private ToggleGroup numPlayers;

    @FXML
    private ToggleGroup mapType;

    @FXML
    private ToggleGroup difficulty;

    /**
     * Configures the game based on selections on
     * game config screen.
     *
     * @param event event for gameconfig
     * @throws IOException
     */
    @FXML
    private void handleGameConfigButton(ActionEvent event) throws IOException {
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
