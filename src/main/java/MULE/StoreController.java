package MULE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    private void initialize() {
        //store instance
        Store store = GamePlay.GAMECONFIG.getStore();

        //current player
        Player current = GamePlay.currentPlayer;

        //populate store quantities
        populateStore(store);

        //popular available player quantities
        populatePlayerItems();

        checkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //get buying data
                int[] totalItemsToBuy = totalItemsToBuy();
                int[] itemPriceTotals =  itemPriceTotals(totalItemsToBuy);
                int[] itemsAvailableToBuy = itemsAvailableToBuy(store);
                int finalBuyTotal = overallBuyTotal(itemPriceTotals);
                boolean checkoutAllowed = checkAmountsAreAvailable(totalItemsToBuy, itemsAvailableToBuy);

                //get selling data
                int[] totalItemsToSell = totalItemsToSell();
                int[] itemSellPriceTotals = itemPriceTotals(totalItemsToSell);
                int[] itemsAvailableToSell = itemsAvailbleToSell(current);
                int finalSellTotal = overallBuyTotal(itemSellPriceTotals);
                boolean sellAllowed = checkAmountsAreAvailable(totalItemsToSell, itemsAvailableToSell);

                //create dialog box
                final Stage dialogStage = new Stage();
                dialogStage.setTitle("Buy & Sell Items");
                dialogStage.initModality(Modality.WINDOW_MODAL);

                //if player doesn't have enough money
                if (current.getMoney() < finalBuyTotal) {
                    Text message = new Text("You do not have enough money.");

                    Button okay = new Button("Okay");
                    okay.setOnAction(arg0 -> {
                        dialogStage.close();
                    });

                    HBox hBox = createHBox(okay);
                    VBox vBox = createVBox(message, hBox);
                    dialogStage.setScene(new Scene(vBox));
                }

                //if player checks out unavailable number of items
                //if player sells unavailable number of items
                else if (!checkoutAllowed || !sellAllowed) {
                    Text message = cannotCheckoutMemo(totalItemsToBuy, itemsAvailableToBuy, totalItemsToSell, itemsAvailableToSell);

                    Button okay = new Button("Okay");
                    okay.setOnAction(arg0 -> {
                        dialogStage.close();
                    });

                    HBox hBox = createHBox(okay);
                    VBox vBox = createVBox(message, hBox);
                    dialogStage.setScene(new Scene(vBox));
                }

                //if everything is fine
                else if (checkoutAllowed && sellAllowed) {
                    Text buyMessage = generateBuyReceipt(totalItemsToBuy, itemPriceTotals, finalBuyTotal);
                    Text sellMessage = generateSellReceipt(totalItemsToSell, itemSellPriceTotals, finalSellTotal);

                    Button submit = new Button("Submit");
                    submit.setOnAction(arg0 -> {
                        //update player money
                        current.setMoney(current.getMoney() + finalSellTotal - finalBuyTotal);

                        //update player resources
                        updatePlayerResources(totalItemsToBuy, totalItemsToSell, current);
                        //return to town
                        try {
                            returnToTown();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });

                    Button returnToStore = new Button("Edit Cart");
                    returnToStore.setOnAction(arg0 -> {
                        dialogStage.close();
                    });

                    HBox hbox = createHBox(returnToStore, submit);
                    VBox vBox = createVBox(buyMessage, sellMessage, hbox);
                    dialogStage.setScene(new Scene(vBox));
                }

                dialogStage.show();
            }
        });

        returnToTown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    returnToTown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //HBOX
    private HBox createHBox(Node... elements) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20.0);
        hbox.getChildren().addAll(elements);
        return hbox;
    }

    //VBOX
    private VBox createVBox(Node... elements) {
        VBox vbox = new VBox();
        vbox.setSpacing(20.0);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(elements);
        return vbox;
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

    private int[] totalItemsToSell() {
        int[] itemsToSell = new int[4];
        itemsToSell[0] = Integer.parseInt(foodSell.getText());
        itemsToSell[1] = Integer.parseInt(energySell.getText());
        itemsToSell[2] = Integer.parseInt(smithoreSell.getText());
        itemsToSell[3] = Integer.parseInt(crystiteSell.getText());
        return itemsToSell;
    }

    //NUM AVAILABLE ARRAY
    private int[] itemsAvailableToBuy(Store store) {
        int[] itemsAvailable = new int[4];
        itemsAvailable[0] = store.getNumFood();
        itemsAvailable[1] = store.getNumEnergy();
        itemsAvailable[2] = store.getNumSmithore();
        itemsAvailable[3] = store.getNumCrystite();
        return itemsAvailable;
    }

    private int[] itemsAvailbleToSell(Player player) {
        int[] itemsAvailable = new int[4];
        itemsAvailable[0] = player.getFood();
        itemsAvailable[1] = player.getEnergy();
        itemsAvailable[2] = player.getSmithore();
        itemsAvailable[3] = player.getCrystite();
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

    private boolean checkAmountsAreAvailable(int[] totalItemsToBuy, int[] itemsAvailable) {

        for (int i = 0; i < totalItemsToBuy.length; i++) {
            if (totalItemsToBuy[i] > itemsAvailable[i]) {
                return false;
            } else if (totalItemsToBuy[i] < 0) {
                return false;
            }
        }

        return true;
    }

    private Text cannotCheckoutMemo(int[] totalItemsToBuy, int[] itemsAvailbleToBuy, int[] totalItemsToSell, int[] itemsAvailableToSell) {

        StringBuilder memo = new StringBuilder();

        for (int i = 0; i < totalItemsToBuy.length; i++) {
            String current = "";
            if (i == 0) current = " food";
            if (i == 1) current = " energy";
            if (i == 2) current = " smithore";
            if (i == 3) current = " crystite";

            if (totalItemsToBuy[i] > itemsAvailbleToBuy[i]) {
                memo.append("You have too many" + current + " items. \n");
            } else if (totalItemsToBuy[i] < 0) {
                memo.append("You cannot purchase negative" + current + " items. \n");
            }
        }

        for (int i = 0; i < totalItemsToSell.length; i++) {
            String current = "";
            if (i == 0) current = " food";
            if (i == 1) current = " energy";
            if (i == 2) current = " smithore";
            if (i == 3) current = " crystite";

            if (totalItemsToSell[i] > itemsAvailableToSell[i]) {
                memo.append("You do not have that many" + current + " items to sell. \n");
            } else if (totalItemsToSell[i] < 0) {
                memo.append("You cannot sell negative" + current + " items. \n");
            }
        }

        return new Text(memo.toString());
    }

    private Text generateBuyReceipt(int[] totalItemsToBuy, int[] itemPriceTotals, int overallTotal) {
        //TODO: Add sell items to receipt
        Text receipt = new Text("Your Bought Items: \n"
                + totalItemsToBuy[0] + " Food: " + itemPriceTotals[0] + "\n"
                + totalItemsToBuy[1] + " Energy: " + itemPriceTotals[1] + "\n"
                + totalItemsToBuy[2] + " Smithore: " + itemPriceTotals[2] + "\n"
                + totalItemsToBuy[3] + " Crystite: " + itemPriceTotals[3] + "\n"
                + "Total: " + overallTotal);
        return receipt;
    }

    private Text generateSellReceipt(int[] totalItemsToSell, int[] itemSellPriceTotals, int finalSellTotal) {
        Text receipt = new Text("Your Sold Items: \n"
                                + totalItemsToSell[0] + " Food: " + itemSellPriceTotals[0] + "\n"
                                + totalItemsToSell[1] + " Energy: " + itemSellPriceTotals[1] + "\n"
                                + totalItemsToSell[2] + " Smithore: " + itemSellPriceTotals[2] + "\n"
                                + totalItemsToSell[3] + " Crystite: " + itemSellPriceTotals[3] + "\n"
                                + "Total: " + finalSellTotal);
        return receipt;
    }

    private void updatePlayerResources(int[] totalItemsToBuy, int[] totalItemsToSell, Player player) {
        player.setFood(player.getFood() + totalItemsToBuy[0]  - totalItemsToSell[0]);
        player.setEnergy(player.getEnergy() + totalItemsToBuy[1] - totalItemsToSell[1]);
        player.setSmithore(player.getSmithore() + totalItemsToBuy[2] - totalItemsToSell[2]);
        player.setCrystite(player.getCrystite() + totalItemsToBuy[3] - totalItemsToSell[3]);
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

    private void returnToTown() throws IOException {
        Stage stage = (Stage) returnToTown.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/town.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}