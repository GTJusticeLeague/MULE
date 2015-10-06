package MULE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by danielansher on 10/4/15.
 */
public class StoreController {

    @FXML
    private Text foodInStore;

    @FXML
    private Text energyInStore;

    @FXML
    private Text smithoreInStore;

    @FXML
    private Text crystiteInStore;

    @FXML
    private Text playerFood;

    @FXML
    private Text playerEnergy;

    @FXML
    private Text playerSmithore;

    @FXML
    private Text playerCrystite;

    @FXML
    private TextField foodBuy;

    @FXML
    private TextField energyBuy;

    @FXML
    private TextField smithoreBuy;

    @FXML
    private TextField crystiteBuy;

    @FXML
    private TextField muleBuy;

    @FXML
    private TextField foodSell;

    @FXML
    private TextField energySell;

    @FXML
    private TextField smithoreSell;

    @FXML
    private TextField crystiteSell;

    @FXML
    private Label mule;

    @FXML
    private Label foodTotal;

    @FXML
    private Label energyTotal;

    @FXML
    private Label smithoreTotal;

    @FXML
    private Label crystiteTotal;

    @FXML
    private Label muleTotal;

    @FXML
    private Label foodReceive;

    @FXML
    private Label energyReceive;

    @FXML
    private Label smithoreReceive;

    @FXML
    private Label crystiteReceive;

    @FXML
    private Button returnToTown;

    @FXML
    private Button checkout;

    @FXML
    private void initialize() {
        //populate number of items in store
        //populate number of items player has available
        populatePlayerItems();
        //a player cannot input more items than the store has
        //a player cannot sell more items than they have
    }


    private void populateStore() {

    }

    private void populatePlayerItems() {
        Player current = GamePlay.currentPlayer;
        playerFood.setText(current.getFood() + " Food");
        playerEnergy.setText(current.getEnergy() + " Energy");
        playerSmithore.setText(current.getSmithore() + " Smithore");
        playerCrystite.setText(current.getCrystite() + " Crystite");
    }
}

