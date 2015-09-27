package MULE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import javafx.scene.input.MouseEvent;
import java.io.IOException;

/**
 * Created by Emeke on 9/19/2015.
 */
public class TownController {


    @FXML
    private Button exitButton;

    @FXML
    private Pane pubPane;


    private final EventHandler<javafx.scene.input.MouseEvent> pubHandler = new EventHandler<javafx.scene.input.MouseEvent>() {
        @Override
        public void handle(javafx.scene.input.MouseEvent event) {
            // CALCULATE earned money
            // roundbonus * randInt between 0-timeBonus
            // roundbonus = 1-3 (50), 4-7 (100), 8-11 (150), 12 (200) <-- currentRound (bonusAmt)
            // (37-50 seconds = 200), (25-37 seconds = 150), (12-25 seconds = 100), (0-12 seconds = 50)
            // max is 250 points

            final Stage dialogStage = new Stage();
            dialogStage.setTitle("You've entered the pub.");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Label purchaseLandLabel = new Label("You have earned infinite amounts of money.");
            purchaseLandLabel.setAlignment(Pos.CENTER);

            //TODO: PUT CALCULATIONS HERE & ADD TO PLAYER MONEY, END PLAYER's TURN

            Button okBtn = new Button("OK");
            okBtn.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent arg0) {
                    dialogStage.close();
                    try {
                        exitTown();
                    } catch (java.io.IOException e) {
                        System.out.println("An IO Exception has occured in the pub's handler.");
                        System.out.println(e.getStackTrace());
                    }


                }
            });
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(40.0);
            hBox.getChildren().addAll(okBtn);


            VBox vBox = new VBox();
            vBox.setSpacing(40.0);
            vBox.getChildren().addAll(purchaseLandLabel, hBox);

            dialogStage.setScene(new Scene(vBox));
            dialogStage.show();
        }
    };

    @FXML
    public void initialize() {
        pubPane.addEventHandler(MouseEvent.MOUSE_CLICKED, pubHandler);
    }

    @FXML
    private void handleExitTown(ActionEvent event) throws IOException {
        exitTown();
    }

    private void exitTown() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
