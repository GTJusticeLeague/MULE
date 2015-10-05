package MULE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by danielansher on 10/4/15.
 */
public class StoreController {
    @FXML
    private void initialize() {
        //shows pop up
        //different scene is displayed based on selection
    }

    private final EventHandler<MouseEvent> storeHandler = new EventHandler<MouseEvent> () {
        @Override
        public void handle(MouseEvent event) {
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Buy Or Sell Resources");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Label buyOrSellLabel = new Label("Would you like to buy or sell resources?");
            buyOrSellLabel.setAlignment(Pos.CENTER);

            Button buy = new Button("Buy");
            Button sell = new Button("Sell");

            buy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            sell.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });
        }
    };
}

