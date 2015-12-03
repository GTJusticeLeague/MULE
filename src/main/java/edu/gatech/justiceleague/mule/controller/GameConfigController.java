package edu.gatech.justiceleague.mule.controller;

import edu.gatech.justiceleague.mule.model.Database;
import edu.gatech.justiceleague.mule.model.GameConfig;
import edu.gatech.justiceleague.mule.model.GamePlay;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import main.java.edu.gatech.justiceleague.mule.model.Music;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

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
    private ImageView welcomeImgView;

    private Music music;

    @FXML
    private void initialize() {
        welcomeImgView.setImage(new Image(getClass().getResourceAsStream("/img/welcome.png")));
        welcomeImgView.setFitHeight(295);
        welcomeImgView.setFitWidth(700);
        //music
        music = new Music(Paths.get("src/main/resources/music/Spanish_Flea.mp3").toUri().toString());
        music.startMusic();
    }

    @FXML
    private void handleLoadGame() throws IOException, SQLException, ClassNotFoundException {
        Label saveGame = new Label("Select a game to load.");
        saveGame.setAlignment(Pos.CENTER);

        Button load = new Button("Load");
        Button cancel = new Button("Cancel");

        //Add items to ChoiceBox
        ObservableList<String> gameIDs = Database.queryDatabaseForGameIDs();
        ChoiceBox<String> gameList = new ChoiceBox<>(gameIDs);

        final Stage dialogStage = GameScreenController.stageMaker("Load a Game", GameScreenController.vBoxMaker(saveGame,
                gameList, GameScreenController.hBoxMaker(null, load, cancel)));
        dialogStage.setWidth(175);
        dialogStage.setHeight(175);

        load.setOnAction(arg0 -> {
            String output = gameList.getSelectionModel().getSelectedItem();
            if (output != null) {
                GamePlay.loadGame(output);
                try {
                    Stage stage = (Stage) loadGameButton.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScreen.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            dialogStage.close();
        });
        cancel.setOnAction(arg0 -> dialogStage.close());
        dialogStage.show();
    }

    /**
     * Configures the game based on selections on
     * game config screen.
     *
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

        music.stopMusic();

        // Move to the next scene (player configuration)
            Stage stage = (Stage) gameConfigButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/playerConfiguration.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/stylesheet.css");
            stage.setScene(scene);
            stage.show();

        //todo: load game here?
    }
}
