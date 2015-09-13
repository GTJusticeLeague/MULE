package MULE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

public class MainController {
    private GameConfig GAMECONFIG;

    @FXML
    private Button GameConfigButton;

//    @FXML
//    private RadioButton PlayerNum_4;
//
//    @FXML
//    private RadioButton PlayerNum_3;
//
//    @FXML
//    private RadioButton PlayerNum_2;
//
//    @FXML
//    private RadioButton MapType_Standard;
//
//    @FXML
//    private RadioButton MapType_Random;
//
//    @FXML
//    private RadioButton Difficulty_Beginner;
//
//    @FXML
//    private RadioButton Difficulty_Intermediate;
//
//    @FXML
//    private RadioButton Difficulty_Advanced;

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
        String selected_dificulty = Difficulty.getSelectedToggle().toString();
        DifficultyLabel.setText(selected_dificulty);
        //String selected_MapType = MapType.selectedToggleProperty().toString();
        //int selected_Num = Integer.parseInt(NumPlayers.selectedToggleProperty().toString());

        //GameConfig = new GameConfig();
    }


}
