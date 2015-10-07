package MULE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by danielansher on 10/4/15.
 */
public class StoreController {

    @FXML
    private GridPane buySellGrid;

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
        Store store = GamePlay.GAMECONFIG.getStore();
        //populate number of items in store
        populateStore(store);
        //populate number of items player has available
        populatePlayerItems();
        //a player cannot input more items than the store has
        //a player cannot sell more items than they have

        int food = Integer.parseInt(foodBuy.getText());
        int foodTotal = food * 30;

        int energy = Integer.parseInt(energyBuy.getText());
        int energyTotal = energy * 25;

        int smithore = Integer.parseInt(smithoreBuy.getText());
        int smithoreTotal = smithore * 50;

        int crystite = Integer.parseInt(crystiteBuy.getText());
        int crystiteTotal = crystite * 100;

        int finalTotal = foodTotal + energyTotal + smithoreTotal + crystiteTotal;

        checkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                final Stage dialogStage = new Stage();
                dialogStage.setTitle("Buy and Sell Items");
                dialogStage.initModality(Modality.WINDOW_MODAL);

                //if the user does not have enough money
                if (GamePlay.currentPlayer.getMoney() < finalTotal) {
                    Text receipt = new Text("You don't have enough money.");

                    Button okay = new Button("Okay");
                    okay.setOnAction(arg0 -> {
                        dialogStage.close();
                    });

                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(20.0);
                    hbox.getChildren().addAll(okay);

                    VBox vbox = new VBox();
                    vbox.setSpacing(20.0);
                    vbox.setPadding(new Insets(10, 10, 10, 10));
                    vbox.getChildren().addAll(receipt, hbox);

                    dialogStage.setScene(new Scene(vbox));
                }

                //if the user attempts to checkout an unavailable num items
                if (!checkForAccuracy(store)) {
                    Text receipt = cannotCheckout(store);

                    Button okay = new Button("Okay");
                    okay.setOnAction(arg0 -> {
                        dialogStage.close();
                    });

                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(20.0);
                    hbox.getChildren().addAll(okay);

                    VBox vbox = new VBox();
                    vbox.setSpacing(20.0);
                    vbox.setPadding(new Insets(10, 10, 10, 10));
                    vbox.getChildren().addAll(receipt, hbox);

                    dialogStage.setScene(new Scene(vbox));
                }

                //if everything is fine show the final dialog box
                if (checkForAccuracy(store)) {
                    Text receipt = receipt();

                    Button editPurchases = new Button("Edit Purchases");
                    editPurchases.setOnAction(arg0 -> {
                        dialogStage.close();
                    });

                    Button checkout = new Button("Checkout");

                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(20.0);
                    hbox.getChildren().addAll(editPurchases, checkout);

                    VBox vbox = new VBox();
                    vbox.setSpacing(20.0);
                    vbox.setPadding(new Insets(10, 10, 10, 10));
                    vbox.getChildren().addAll(receipt, hbox);


                    dialogStage.setScene(new Scene(vbox));
                }

                dialogStage.show();
            }
        });
    }

    /*Current issue: This will always populate to initial
    values - does not account for change after buying and selling.
     */
    private void populateStore(Store store) {
        foodInStore.setText(store.getNumFood() + " Food");
        energyInStore.setText(store.getNumEnergy() + " Energy");
        smithoreInStore.setText(store.getNumSmithore() + " Smithore");
        crystiteInStore.setText(store.getNumCrystite() + " Crystite");

        //setAllTextBoxesToZero
        foodBuy.setText("0");
        energyBuy.setText("0");
        smithoreBuy.setText("0");
        crystiteBuy.setText("0");
        muleBuy.setText("0");
        foodSell.setText("0");
        energySell.setText("0");
        smithoreSell.setText("0");
        crystiteSell.setText("0");

        //showCostPerItem
        foodTotal.setText("$30");
        energyTotal.setText("$25");
        smithoreTotal.setText("$50");
        crystiteTotal.setText("$100");
        muleTotal.setText("$100");
    }

    private void populatePlayerItems() {
        Player current = GamePlay.currentPlayer;
        playerFood.setText(current.getFood() + " Food");
        playerEnergy.setText(current.getEnergy() + " Energy");
        playerSmithore.setText(current.getSmithore() + " Smithore");
        playerCrystite.setText(current.getCrystite() + " Crystite");
    }

    private boolean checkForAccuracy(Store store) {

        int[] itemsToBuy = new int[4];
        itemsToBuy[0] = Integer.parseInt(foodBuy.getText());
        itemsToBuy[1] = Integer.parseInt(energyBuy.getText());
        itemsToBuy[2] = Integer.parseInt(smithoreBuy.getText());
        itemsToBuy[3] = Integer.parseInt(crystiteBuy.getText());

        int[] itemsAvailable = new int[4];
        itemsAvailable[0] = store.getNumFood();
        itemsAvailable[1] = store.getNumEnergy();
        itemsAvailable[2] = store.getNumSmithore();
        itemsAvailable[3] = store.getNumCrystite();

        for (int i = 0; i < itemsToBuy.length; i++) {
            if (itemsToBuy[i] > itemsAvailable[i]) {
                return false;
            } else if (itemsToBuy[i] < itemsAvailable[i]){
                return false;
            } else if (itemsToBuy[i] < 0) {
                return false;
            }
        }

        return true;
    }

    private Text cannotCheckout(Store store) {
        StringBuilder memo = new StringBuilder();

        int[] itemsToBuy = new int[4];
        itemsToBuy[0] = Integer.parseInt(foodBuy.getText());
        itemsToBuy[1] = Integer.parseInt(energyBuy.getText());
        itemsToBuy[2] = Integer.parseInt(smithoreBuy.getText());
        itemsToBuy[3] = Integer.parseInt(crystiteBuy.getText());

        int[] itemsAvailable = new int[4];
        itemsAvailable[0] = store.getNumFood();
        itemsAvailable[1] = store.getNumEnergy();
        itemsAvailable[2] = store.getNumSmithore();
        itemsAvailable[3] = store.getNumCrystite();

        for (int i = 0; i < itemsToBuy.length; i++) {

            String current = "";
            if (i == 0) current = " food";
            if (i == 1) current = " energy";
            if (i == 2) current = " smithore";
            if (i == 3) current = " crystite";

            if (itemsToBuy[i] > itemsAvailable[i]) {
                memo.append("You have too many" + current + " items. \n");
            } else if (itemsToBuy[i] < 0) {
                memo.append("You cannot purchase negative items. \n");
            }
        }

        return new Text(memo.toString());
    }

    private Text receipt() {
        int food = Integer.parseInt(foodBuy.getText());
        int foodTotal = food * 30;

        int energy = Integer.parseInt(energyBuy.getText());
        int energyTotal = energy * 25;

        int smithore = Integer.parseInt(smithoreBuy.getText());
        int smithoreTotal = smithore * 50;

        int crystite = Integer.parseInt(crystiteBuy.getText());
        int crystiteTotal = crystite * 100;

        int finalTotal = foodTotal + energyTotal + smithoreTotal + crystiteTotal;

        Text receipt = new Text("Your receipt: \n"
                                + food + " Food: " + foodTotal + "\n"
                                + energy + " Energy: " + energyTotal + "\n"
                                + smithore + " Smithore: " + smithoreTotal + "\n"
                                + crystite + " Crystite: " + crystiteTotal + "\n"
                                + "Total: " + finalTotal);

        return receipt;
    }
}

