package MULE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private GameConfig GAMECONFIG;

    Difficulty GameDifficulty;

    MapType GameMapType;

    @FXML
    private Button GameConfigButton;

    @FXML
    private Label DifficultyLabel;

    @FXML
    private ToggleGroup NumPlayers;

    @FXML
    private ToggleGroup MapType;

    @FXML
    private ToggleGroup Difficulty;

    @FXML
    private void handleGameConfigButton (ActionEvent event) throws IOException {
        String selected_dificulty = ((RadioButton)Difficulty.getSelectedToggle()).getText();
        if (selected_dificulty.equals("Beginner")) {
            GameDifficulty = MULE.Difficulty.BEGINNER;
        } else if (selected_dificulty.equals("Intermediate")) {
            GameDifficulty = MULE.Difficulty.INTERMEDIATE;
        } else {
            GameDifficulty = MULE.Difficulty.ADVANCED;
        }

        String selected_MapType = ((RadioButton)MapType.getSelectedToggle()).getText();
        if (selected_MapType.equals("Standard")) {
            GameMapType = MULE.MapType.STANDARD;
        } else {
            GameMapType = MULE.MapType.RANDOM;
        }

        GAMECONFIG = new GameConfig(GameDifficulty, GameMapType,
                Integer.parseInt(((RadioButton)NumPlayers.getSelectedToggle()).getText()));

        // Move to the next scene (player configuration)
        Stage stage=(Stage) GameConfigButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/playerConfiguration.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
