package edu.gatech.justiceleague.mule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Loads the stage with the initial scene
 */
public class Main extends Application {
    /**
     * Starts the game
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("/fxml/gameConfiguration.fxml"));
        primaryStage.setTitle("M.U.L.E.");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * REALLY launches the stage
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
