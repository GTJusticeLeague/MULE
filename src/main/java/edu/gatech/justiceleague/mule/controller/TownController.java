package edu.gatech.justiceleague.mule.controller;

import edu.gatech.justiceleague.mule.model.GamePlay;
import edu.gatech.justiceleague.mule.model.Slots;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.edu.gatech.justiceleague.mule.model.Music;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

/**Import file
 * Created by Emeke on 9/19/2015.
 */
public class TownController {


    @FXML
    private Button exitButton;

    @FXML
    private Pane pubPane;

    @FXML
    private Pane storePane;

    @FXML
    private Pane casinoPane;

    private Music music;

    private final EventHandler<javafx.scene.input.MouseEvent> casinoHandler = event -> {
        Slots.main(null);
    };


    private final EventHandler<javafx.scene.input.MouseEvent> pubHandler = event -> {
        // CALCULATE earned money
        // roundbonus * randInt between 0-timeBonus
        // roundbonus = 1-3 (50), 4-7 (100), 8-11 (150), 12 (200) <-- currentRound (bonusAmt)
        // (37-50 seconds = 200), (25-37 seconds = 150), (12-25 seconds = 100), (0-12 seconds = 50)
        // max is 250 points

        // TODO: Remove print lines once tested
        int playerMoney = GamePlay.getCurrentPlayer().getMoney();

        System.out.println("playerMoney: " + playerMoney);
        //Normal Bonus Calculation
        int timeRemaining = GamePlay.getCurrentPlayer().stopTime();
        int timerBonus;
        if (timeRemaining >= 37) {
            timerBonus = 200;
        } else if (timeRemaining >= 25) {
            timerBonus = 150;
        } else if (timeRemaining >= 12) {
            timerBonus = 100;
        } else {
            timerBonus = 50;
        }
        Random rand = new Random();
        int randomBonus = rand.nextInt(timerBonus + 1);
        System.out.println("TimeRemaining: " + timeRemaining + " randomBonus: " + randomBonus);

        //Round Bonus Calculation
        int currentRound = GamePlay.getRound();
        int roundBonus = 0;
        if (currentRound >= 1 && currentRound <= 3) {
            roundBonus = 50;
        } else if (currentRound >= 4 && currentRound <= 7) {
            roundBonus = 100;
        } else if (currentRound >= 8 && currentRound <= 11) {
            roundBonus = 150;
        } else if (currentRound == 12) {
            roundBonus = 200;
        }
        System.out.println("curRound: " + currentRound);
        System.out.println("roundBonus: " + roundBonus);

        // Total Bonus Check
        int totalBonus = roundBonus * randomBonus;
        if (totalBonus > 250) {
            totalBonus = 250;
        }
        int newPlayerMoney = playerMoney + totalBonus;
        GamePlay.getCurrentPlayer().setMoney(newPlayerMoney);

        System.out.println("totalBonus: " + totalBonus);
        System.out.println("newPlayerMoney: " + newPlayerMoney);

        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Congrats!");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Label purchaseLandLabel = new Label("You have earned " + totalBonus + " money.");
        purchaseLandLabel.setAlignment(Pos.CENTER);

        Button okBtn = new Button("OK");
        okBtn.setOnAction(arg0 -> {
            dialogStage.close();
            try {
                GamePlay.nextPlayer();
                exitTown();
            } catch (IOException e) {
                System.out.println("An IO Exception has occurred in the pub's handler.");
                System.out.println(Arrays.toString(e.getStackTrace()));
            }


        });
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20.0);
        hBox.getChildren().addAll(okBtn);


        VBox vBox = new VBox();
        vBox.setSpacing(20.0);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(purchaseLandLabel, hBox);

        dialogStage.setScene(new Scene(vBox));
        dialogStage.show();
    };

    private final EventHandler<javafx.scene.input.MouseEvent> storeHandler = new EventHandler<javafx.scene.input.MouseEvent>() {
        @Override
        public void handle(javafx.scene.input.MouseEvent event) {
            Stage stage = (Stage) storePane.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/store.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (java.io.IOException e) {
                System.out.println("An IO Exception has occurred in the store's handler.");
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                System.out.println(sw.toString());
            }
            stage.show();
            music.stopMusic();
        }
    };

    /**
     * Initializes town
     */
    @FXML
    public void initialize() {
        pubPane.addEventHandler(MouseEvent.MOUSE_CLICKED, pubHandler);
        storePane.addEventHandler(MouseEvent.MOUSE_CLICKED, storeHandler);
        casinoPane.addEventHandler(MouseEvent.MOUSE_CLICKED, casinoHandler);

        music = new Music(Paths.get("src/main/resources/music/The_Western_Pub.mp3").toUri().toString());
        music.startMusic();
    }

    /**
     * Exits the town
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleExitTown(ActionEvent event) throws IOException {
        exitTown();
    }

    /**
     * REALLY exits the town
     * @throws IOException
     */
    private void exitTown() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        music.stopMusic();
    }


}
