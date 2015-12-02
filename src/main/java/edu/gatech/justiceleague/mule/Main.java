package edu.gatech.justiceleague.mule;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Loads the stage with the initial scene
 */
public class Main extends Application {
    /**
     * Starts the game
     * @param primaryStage FXML Start param
     * @throws java.io.IOException
     */
    @Override
    public final void start(Stage primaryStage) throws java.io.IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/fxml/gameConfiguration.fxml"));
        //scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("/stylesheet.css");
        //stage
        primaryStage.setTitle("M.U.L.E.");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * REALLY launches the stage
     * @param args Unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
