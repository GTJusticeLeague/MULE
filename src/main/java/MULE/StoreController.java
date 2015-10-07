package MULE;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Text muleInStore;

    @FXML
    private Label playerMoney;

    @FXML
    private Text playerFood;

    @FXML
    private Text playerEnergy;

    @FXML
    private Text playerSmithore;

    @FXML
    private Text playerCrystite;

    @FXML
    private Text playerMule;

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
    private void initialize() throws IOException {
        //store instance
        Store store = GamePlay.GAMECONFIG.getStore();

        //current player
        Player current = GamePlay.currentPlayer;

        //populate store quantities
        populateStore(store);

        //popular available player quantities
        populatePlayerItems();

        returnToTown.setOnAction(e -> {
            try {
                exitStore();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        checkout.setOnAction(e -> {
            //get data
            int[] totalItemsToBuy = totalItemsToBuy();
            int[] itemPriceTotals =  itemPriceTotals(totalItemsToBuy);
            int[] itemsAvailable = itemsAvailable(store);
            int finalTotal = overallBuyTotal(itemPriceTotals);
            boolean checkoutAllowed = checkAmountsAreAvailable(totalItemsToBuy, itemsAvailable);

            //create dialog box
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Buy & Sell Items");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            //if player doesn't have enough money
            if (current.getMoney() < finalTotal) {
                Text message = new Text("You do not have enough money.");

                Button okay = new Button("Okay");
                okay.setOnAction(arg0 -> dialogStage.close());

                dialogStage.setScene(new Scene(GameScreenController.vBoxMaker(message,
                        GameScreenController.hBoxMaker(null, okay))));
            }

            //if player checks out unavailable number of items
            else if (!checkoutAllowed) {
                Text message = cannotCheckoutMemo(totalItemsToBuy, itemsAvailable);

                Button okay = new Button("Okay");
                okay.setOnAction(arg0 -> dialogStage.close());

                dialogStage.setScene(new Scene(GameScreenController.vBoxMaker(message,
                        GameScreenController.hBoxMaker(null, okay))));
            }

            //if everything is fine
            else if (checkoutAllowed) {
                Text message = generateReceipt(totalItemsToBuy, itemPriceTotals, finalTotal);

                Button submit = new Button("Submit");
                submit.setOnAction(arg0 -> {
                    //update player money
                    current.setMoney(current.getMoney() - finalTotal);
                    //update player resources
                    updatePlayerResources(totalItemsToBuy, current);
                    try {
                        exitStore();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

                Button returnToStore = new Button("Edit Cart");
                returnToStore.setOnAction(arg0 -> dialogStage.close());

                dialogStage.setScene(new Scene(GameScreenController.vBoxMaker(message,
                        GameScreenController.hBoxMaker(null, returnToStore, submit))));
            }

            dialogStage.show();
        });
    }

    //NUM ITEMS ARRAY
    private int[] totalItemsToBuy() {
        int[] itemsToBuy = new int[4];
        itemsToBuy[0] = Integer.parseInt(foodBuy.getText());
        itemsToBuy[1] = Integer.parseInt(energyBuy.getText());
        itemsToBuy[2] = Integer.parseInt(smithoreBuy.getText());
        itemsToBuy[3] = Integer.parseInt(crystiteBuy.getText());
        return itemsToBuy;
    }

    //NUM AVAILABLE ARRAY
    private int[] itemsAvailable(Store store) {
        int[] itemsAvailable = new int[4];
        itemsAvailable[0] = store.getNumFood();
        itemsAvailable[1] = store.getNumEnergy();
        itemsAvailable[2] = store.getNumSmithore();
        itemsAvailable[3] = store.getNumCrystite();
        return itemsAvailable;
    }

    //total for items to buy
    private int[] itemPriceTotals(int[] totalItemsToBuy) {

        int[] totals = new int[4];

        totals[0] = totalItemsToBuy[0] * 30; //food
        totals[1] = totalItemsToBuy[1] * 25; //energy
        totals[2] = totalItemsToBuy[2] * 50; //smithore
        totals[3] = totalItemsToBuy[3] * 100; //crystite

        return totals;
    }

    private int overallBuyTotal(int[] itemPriceTotals) {

        int total = 0;

        for(int i : itemPriceTotals) {
            total += i;
        }

        return total;
    }

    private boolean checkAmountsAreAvailable(int[] totalItemsToBuy, int[] itemsAvailble) {

        for (int i = 0; i < totalItemsToBuy.length; i++) {
            if (totalItemsToBuy[i] > itemsAvailble[i]) {
                return false;
            } else if (totalItemsToBuy[i] < 0) {
                return false;
            }
        }

        return true;
    }

    private Text cannotCheckoutMemo(int[] totalItemsToBuy, int[] itemsAvailble) {

        StringBuilder memo = new StringBuilder();

        for (int i = 0; i < totalItemsToBuy.length; i++) {
            String current = "";
            if (i == 0) current = " food";
            if (i == 1) current = " energy";
            if (i == 2) current = " smithore";
            if (i == 3) current = " crystite";

            if (totalItemsToBuy[i] > itemsAvailble[i]) {
                memo.append("You have too many" + current + " items. \n");
            } else if (totalItemsToBuy[i] < 0) {
                memo.append("You cannot purchase negative" + current + " items. \n");
            }
        }

        return new Text(memo.toString());
    }

    private Text generateReceipt(int[] totalItemsToBuy, int[] itemPriceTotals, int overallTotal) {
        Text receipt = new Text("Your Receipt: \n"
                + totalItemsToBuy[0] + " Food: " + itemPriceTotals[0] + "\n"
                + totalItemsToBuy[1] + " Energy: " + itemPriceTotals[1] + "\n"
                + totalItemsToBuy[2] + " Smithore: " + itemPriceTotals[2] + "\n"
                + totalItemsToBuy[3] + " Crystite: " + itemPriceTotals[3] + "\n"
                + "Total: " + overallTotal);
        return receipt;
    }

    private void updatePlayerResources(int[] totalItemsToBuy, Player player) {
        player.setFood(player.getFood() + totalItemsToBuy[0]);
        player.setEnergy(player.getEnergy() + totalItemsToBuy[1]);
        player.setSmithore(player.getSmithore() + totalItemsToBuy[2]);
        player.setCrystite(player.getCrystite() + totalItemsToBuy[3]);
    }

    private void populateStore(Store store) {
        foodInStore.setText(store.getNumFood() + " Food");
        energyInStore.setText(store.getNumEnergy() + " Energy");
        smithoreInStore.setText(store.getNumSmithore() + " Smithore");
        crystiteInStore.setText(store.getNumCrystite() + " Crystite");
        muleInStore.setText(store.getNumMule() + " Mule");

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
        //need a get num mule function
        playerMule.setText("0 Mule");

        playerMoney.setText("$" + current.getMoney());
    }

    private void exitStore() throws IOException {
        Stage stage = (Stage) returnToTown.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/town.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}