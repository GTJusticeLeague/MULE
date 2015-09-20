package MULE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Emeke on 9/19/2015.
 */
public class TownController {


    @FXML
    private Button exitButton;

    @FXML
    private void handleExitTown(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gameScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
