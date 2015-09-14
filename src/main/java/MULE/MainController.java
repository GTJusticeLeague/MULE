package MULE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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
    private void handleGameConfigButton (ActionEvent event) {
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
    }


}
