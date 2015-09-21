package MULE;

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
    private Button GameConfigButton;

    @FXML
    private ToggleGroup NumPlayers;

    @FXML
    private ToggleGroup MapType;

    @FXML
    private ToggleGroup Difficulty;

    @FXML
    private void handleGameConfigButton(ActionEvent event) throws IOException {
        GameConfig.Difficulty GameDifficulty;
        GameConfig.MapType GameMapType;

        // Get selected difficulty from the radio buttons
        String selected_difficulty = ((RadioButton)Difficulty.getSelectedToggle()).getText();
        switch (selected_difficulty) {
            case "Beginner":
                GameDifficulty = GameConfig.Difficulty.BEGINNER;
                break;
            case "Intermediate":
                GameDifficulty = GameConfig.Difficulty.INTERMEDIATE;
                break;
            default:
                GameDifficulty = GameConfig.Difficulty.ADVANCED;
                break;
        }

        // Get selected map type from the radio buttons
        String selected_MapType = ((RadioButton)MapType.getSelectedToggle()).getText();
        if (selected_MapType.equals("Standard")) {
            GameMapType = GameConfig.MapType.STANDARD;
        } else {
            GameMapType = GameConfig.MapType.RANDOM;
        }

        GamePlay.GAMECONFIG = new GameConfig(GameDifficulty, GameMapType,
                Integer.parseInt(((RadioButton)NumPlayers.getSelectedToggle()).getText()));

        // Move to the next scene (player configuration)
        Stage stage=(Stage) GameConfigButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/playerConfiguration.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
