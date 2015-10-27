package edu.gatech.justiceleague.mule.controller;

import edu.gatech.justiceleague.mule.model.GamePlay;
import edu.gatech.justiceleague.mule.model.Player;
import edu.gatech.justiceleague.mule.model.Store;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**This is important
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
    private TextField muleSell;

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

    /**
     * Initializes store
     */
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

        checkout.setOnAction(e -> {
            //get buying data
            int[] totalItemsToBuy = totalItemsToBuy();
            int[] itemPriceTotals = itemPriceTotals(totalItemsToBuy);
            int[] itemsAvailableToBuy = itemsAvailableToBuy(store);
            int finalBuyTotal = overallBuyTotal(itemPriceTotals);
            boolean checkoutAllowed = checkAmountsAreAvailable(totalItemsToBuy, itemsAvailableToBuy);

            //get selling data
            int[] totalItemsToSell = totalItemsToSell();
            int[] itemSellPriceTotals = itemPriceTotals(totalItemsToSell);
            int[] itemsAvailableToSell = itemsAvailableToSell(current);
            int finalSellTotal = overallBuyTotal(itemSellPriceTotals);
            boolean sellAllowed = checkAmountsAreAvailable(totalItemsToSell, itemsAvailableToSell);

            //if player doesn't have enough money
            if (current.getMoney() < finalBuyTotal) {
                notEnoughMoney();
            } else if (!checkoutAllowed || !sellAllowed) {
                //if player checks out unavailable number of items
                //if player sells unavailable number of items
                unavailableNumberOfItems(totalItemsToBuy, itemsAvailableToBuy, totalItemsToSell, itemsAvailableToSell);
            } else {
                //if everything is fine
                if (wantToBuyMule(totalItemsToBuy)) {
                    equipMule(current, totalItemsToBuy, totalItemsToSell, itemPriceTotals,
                            itemSellPriceTotals, finalSellTotal);
                    System.out.println("checkoutAllowed: Mule total: " + itemPriceTotals[4]);
                } else if (!wantToBuyMule(totalItemsToBuy)) {
                    playerCheckout(current, totalItemsToBuy, totalItemsToSell, itemPriceTotals, itemSellPriceTotals,
                            finalBuyTotal, finalSellTotal);
                }

            }
        });

        returnToTown.setOnAction(event -> {
            try {
                returnToTown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Creates an hbox
     * @param elements elements to put in hbox
     * @return hbox with elements
     */
    private HBox createHBox(Node... elements) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20.0);
        hbox.getChildren().addAll(elements);
        return hbox;
    }

    /**
     * Creates a vbox
     * @param elements elements to put in vbox
     * @return vbox with elements
     */
    private VBox createVBox(Node... elements) {
        VBox vbox = new VBox();
        vbox.setSpacing(20.0);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(elements);
        return vbox;
    }

    /**
     * Creates and displays pop up box for when player
     * does not have enough money.
     */
    private void notEnoughMoney() {

        //create dialog box
        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Buy & Sell Items");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Text message = new Text("You do not have enough money.");

        Button okay = new Button("Okay");
        okay.setOnAction(arg0 -> dialogStage.close());

        HBox hBox = createHBox(okay);
        VBox vBox = createVBox(message, hBox);
        dialogStage.setScene(new Scene(vBox));

        dialogStage.show();
    }

    /**
     * Creates a displays pop up box for when the player
     * attempts to buy or sell an unavailable number of items
     * @param totalItemsToBuy array of number of each item to buy
     * @param itemsAvailableToBuy array of number of items available to buy
     * @param totalItemsToSell array of number of each item to sell
     * @param itemsAvailableToSell array of number of items available to sell
     */
    private void unavailableNumberOfItems(int[] totalItemsToBuy, int[] itemsAvailableToBuy, int[] totalItemsToSell, int[] itemsAvailableToSell) {

        //create dialog box
        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Buy & Sell Items");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Text message = cannotCheckoutMemo(totalItemsToBuy, itemsAvailableToBuy, totalItemsToSell, itemsAvailableToSell);

        Button okay = new Button("Okay");
        okay.setOnAction(arg0 -> dialogStage.close());

        HBox hBox = createHBox(okay);
        VBox vBox = createVBox(message, hBox);
        dialogStage.setScene(new Scene(vBox));

        dialogStage.show();
    }

    /**
     * Handles logic for then the player submits their purchase/sell
     * @param current current player
     * @param totalItemsToBuy array of number of each item the player wishes to buy
     * @param totalItemsToSell array of number of each item the player wishes to sell
     * @param itemPriceTotals array of price of items the player wishes to buy
     * @param itemSellPriceTotals array of price of items the player wishes to sell
     * @param finalBuyTotal total cost of players items
     * @param finalSellTotal money player will recieve from selling items to store
     */
    private void playerCheckout(Player current, int[] totalItemsToBuy, int[] totalItemsToSell, int[] itemPriceTotals,
                                int[] itemSellPriceTotals, int finalBuyTotal, int finalSellTotal) {

        //create dialog box
        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Buy & Sell Items");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Text buyMessage = generateBuyReceipt(totalItemsToBuy, itemPriceTotals, finalBuyTotal);
        System.out.println("playerCheckout: Mule Total: " + itemPriceTotals[4]);
        Text sellMessage = generateSellReceipt(totalItemsToSell, itemSellPriceTotals, finalSellTotal);

        Button submit = new Button("Submit");
        submit.setOnAction(arg0 -> {
            //update player money
            current.setMoney(current.getMoney() + finalSellTotal - finalBuyTotal);

            //update player resources
            updatePlayerResources(totalItemsToBuy, totalItemsToSell, current);

            //update player mule if mule is bought
            if (wantToBuyMule(totalItemsToBuy)) {
                updatePlayerBuyMule(itemPriceTotals, current);
            }

            //return to town
            try {
                returnToTown();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            dialogStage.close();
        });

        Button returnToStore = new Button("Edit Cart");
        returnToStore.setOnAction(arg0 -> dialogStage.close());

        HBox hbox = createHBox(returnToStore, submit);
        VBox vBox = createVBox(buyMessage, sellMessage, hbox);
        dialogStage.setScene(new Scene(vBox));

        dialogStage.show();
    }

    /**
     * Creates the array of number of each item to buy
     * @return array of items to buy
     */
    private int[] totalItemsToBuy() {
        int[] itemsToBuy = new int[5];
        itemsToBuy[0] = Integer.parseInt(foodBuy.getText());
        itemsToBuy[1] = Integer.parseInt(energyBuy.getText());
        itemsToBuy[2] = Integer.parseInt(smithoreBuy.getText());
        itemsToBuy[3] = Integer.parseInt(crystiteBuy.getText());
        itemsToBuy[4] = Integer.parseInt(muleBuy.getText());
        return itemsToBuy;
    }

    /**
     * @return array of total items to sell
     */
    private int[] totalItemsToSell() {
        int[] itemsToSell = new int[5];
        itemsToSell[0] = Integer.parseInt(foodSell.getText());
        itemsToSell[1] = Integer.parseInt(energySell.getText());
        itemsToSell[2] = Integer.parseInt(smithoreSell.getText());
        itemsToSell[3] = Integer.parseInt(crystiteSell.getText());
        itemsToSell[4] = Integer.parseInt(muleSell.getText());
        return itemsToSell;
    }

    /**
     * @param store inventory to check
     * @return array of items available to buy
     */
    private int[] itemsAvailableToBuy(Store store) {
        int[] itemsAvailable = new int[5];
        itemsAvailable[0] = store.getNumFood();
        itemsAvailable[1] = store.getNumEnergy();
        itemsAvailable[2] = store.getNumSmithore();
        itemsAvailable[3] = store.getNumCrystite();
        itemsAvailable[4] = store.getNumMule();
        return itemsAvailable;
    }

    /**
     * @param player inventory to check
     * @return array of items available to sell
     */
    private int[] itemsAvailableToSell(Player player) {
        int[] itemsAvailable = new int[5];
        itemsAvailable[0] = player.getFood();
        itemsAvailable[1] = player.getEnergy();
        itemsAvailable[2] = player.getSmithore();
        itemsAvailable[3] = player.getCrystite();
        itemsAvailable[4] = getPlayerMuleTotal(player);
        return itemsAvailable;
    }

    /**
     * @param totalItemsToBuy
     * @return array of total prices for each item
     */
    private int[] itemPriceTotals(int[] totalItemsToBuy) {

        int[] totals = new int[5];

        totals[0] = totalItemsToBuy[0] * 30; //food
        totals[1] = totalItemsToBuy[1] * 25; //energy
        totals[2] = totalItemsToBuy[2] * 50; //smithore
        totals[3] = totalItemsToBuy[3] * 100; //crystite
        totals[4] = totalItemsToBuy[4] * 100; //mule

        return totals;
    }

    /**
     * @param itemPriceTotals
     * @return total bill
     */
    private int overallBuyTotal(int[] itemPriceTotals) {

        int total = 0;

        for (int i : itemPriceTotals) {
            total += i;
        }

        return total;
    }

    /**
     * @param totalItemsToBuy
     * @param itemsAvailable
     * @return whether amounts available are greater than amounts to buy
     */
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

    /**
     * @param totalItemsToBuy
     * @param itemsAvailbleToBuy
     * @param totalItemsToSell
     * @param itemsAvailableToSell
     * @return Text detailing why you can't check out
     */
    private Text cannotCheckoutMemo(int[] totalItemsToBuy, int[] itemsAvailbleToBuy, int[] totalItemsToSell, int[] itemsAvailableToSell) {

        StringBuilder memo = new StringBuilder();

        for (int i = 0; i < totalItemsToBuy.length; i++) {
            String current = "";
            if (i == 0) {
                current = " food";
            }
            if (i == 1) {
                current = " energy";
            }
            if (i == 2) {
                current = " smithore";
            }
            if (i == 3) {
                current = " crystite";
            }
            if (i == 4) {
                current = " mule";
            }

            if (totalItemsToBuy[i] > itemsAvailbleToBuy[i]) {
                memo.append("You have too many");
                memo.append(current);
                memo.append(" items. \n");
            } else if (totalItemsToBuy[i] < 0) {
                memo.append("You cannot purchase negative");
                memo.append(current);
                memo.append(" items. \n");
            }
        }

        if (totalItemsToBuy[4] > 0) {
            memo.append("You may only purchase one mule per store visit. \n");
        }

        for (int i = 0; i < totalItemsToSell.length; i++) {
            String current = "";
            if (i == 0) {
                current = " food";
            }
            if (i == 1) {
                current = " energy";
            }
            if (i == 2) {
                current = " smithore";
            }
            if (i == 3) {
                current = " crystite";
            }
            if (i == 4) {
                current = " mule";
            }

            if (totalItemsToSell[i] > itemsAvailableToSell[i]) {
                memo.append("You do not have that many");
                memo.append(current);
                memo.append(" items to sell. \n");
            } else if (totalItemsToSell[i] < 0) {
                memo.append("You cannot sell negative");
                memo.append(current);
                memo.append(" items to sell. \n");
            }
        }


        return new Text(memo.toString());
    }

    /**
     * @param totalItemsToBuy
     * @param itemPriceTotals
     * @param overallTotal
     * @return receipt for purchase
     */
    private Text generateBuyReceipt(int[] totalItemsToBuy, int[] itemPriceTotals, int overallTotal) {
        return new Text("Your Bought Items: \n"
                + totalItemsToBuy[0] + " Food: " + itemPriceTotals[0] + "\n"
                + totalItemsToBuy[1] + " Energy: " + itemPriceTotals[1] + "\n"
                + totalItemsToBuy[2] + " Smithore: " + itemPriceTotals[2] + "\n"
                + totalItemsToBuy[3] + " Crystite: " + itemPriceTotals[3] + "\n"
                + totalItemsToBuy[4] + " Mule: " + itemPriceTotals[4] + "\n"
                + "Total: " + overallTotal);
    }

    /**
     * @param totalItemsToSell
     * @param itemSellPriceTotals
     * @param finalSellTotal
     * @return receipt for sale
     */
    private Text generateSellReceipt(int[] totalItemsToSell, int[] itemSellPriceTotals, int finalSellTotal) {
        return new Text("Your Sold Items: \n"
                                + totalItemsToSell[0] + " Food: " + itemSellPriceTotals[0] + "\n"
                                + totalItemsToSell[1] + " Energy: " + itemSellPriceTotals[1] + "\n"
                                + totalItemsToSell[2] + " Smithore: " + itemSellPriceTotals[2] + "\n"
                                + totalItemsToSell[3] + " Crystite: " + itemSellPriceTotals[3] + "\n"
                                + totalItemsToSell[4] + " Mule: " + itemSellPriceTotals[4] + "\n"
                                + "Total: " + finalSellTotal);
    }

    /**
     * Updates player resources.
     *
     * @param totalItemsToBuy
     * @param totalItemsToSell
     * @param player
     */
    private void updatePlayerResources(int[] totalItemsToBuy, int[] totalItemsToSell, Player player) {
        player.setFood(player.getFood() + totalItemsToBuy[0]  - totalItemsToSell[0]);
        player.setEnergy(player.getEnergy() + totalItemsToBuy[1] - totalItemsToSell[1]);
        player.setSmithore(player.getSmithore() + totalItemsToBuy[2] - totalItemsToSell[2]);
        player.setCrystite(player.getCrystite() + totalItemsToBuy[3] - totalItemsToSell[3]);
    }

    /**
     * Update player's mule inventory
     *
     * @param itemPriceTotals
     * @param player
     */
    private void updatePlayerBuyMule(int[] itemPriceTotals, Player player) {
        if (itemPriceTotals[4] == 125) {
            player.setFoodMule(player.getFoodMule() + 1);
        } else if (itemPriceTotals[4] == 150) {
            player.setEnergyMule(player.getEnergyMule() + 1);
        } else if (itemPriceTotals[4] == 175) {
            player.setSmithoreMule(player.getSmithoreMule() + 1);
        } else if (itemPriceTotals[4] == 200) {
            player.setCrystiteMule(player.getCrystiteMule() + 1);
        }
    }

    /**
     * Sets up store screen
     * @param store to get inventory from
     */
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
        muleSell.setText("0");

        //showCostPerItem
        foodTotal.setText("$30");
        energyTotal.setText("$25");
        smithoreTotal.setText("$50");
        crystiteTotal.setText("$100");
        muleTotal.setText("$100");
    }

    /**
     * Show player inventory
     */
    private void populatePlayerItems() {
        Player current = GamePlay.currentPlayer;
        playerFood.setText(current.getFood() + " Food");
        playerEnergy.setText(current.getEnergy() + " Energy");
        playerSmithore.setText(current.getSmithore() + " Smithore");
        playerCrystite.setText(current.getCrystite() + " Crystite");
        playerMule.setText(getPlayerMuleTotal(current) + " Mule");

        playerMoney.setText("$" + current.getMoney());
    }

    /**
     * @param player
     * @return total number of mules in player inventory
     */
    private int getPlayerMuleTotal(Player player) {
        return player.getFoodMule() + player.getEnergyMule() + player.getSmithoreMule()
                + player.getCrystiteMule();
    }

    /**
     * @param totalItemsToBuy
     * @return updated array with one mule to buy
     */
    private boolean wantToBuyMule(int[] totalItemsToBuy) {
        return totalItemsToBuy[4] == 1;
    }

    /**
     * Equips the mule
     *
     * @param current player
     * @param totalItemsToBuy
     * @param totalItemsToSell
     * @param itemPriceTotals
     * @param itemSellPriceTotals
     * @param finalSellTotal
     */
    private void equipMule(Player current, int[] totalItemsToBuy, int[] totalItemsToSell, int[] itemPriceTotals,
                           int[] itemSellPriceTotals, int finalSellTotal) {
        if (totalItemsToBuy[4] == 1) {
            //create dialog box
            final Stage dialogStage = new Stage();
            dialogStage.setTitle("Equip Mule");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Label equip = new Label("Equip Your Mule");

            Text info = new Text("Choose which type of mule you would like:");

            //select mule type using radio buttons
            final ToggleGroup muleType = new ToggleGroup();

            RadioButton foodMule = new RadioButton("Food: +$25");
            foodMule.setToggleGroup(muleType);
            foodMule.setSelected(true);

            RadioButton energyMule = new RadioButton("Energy: +$50");
            energyMule.setToggleGroup(muleType);

            RadioButton smithoreMule = new RadioButton("Smithore: +$75");
            smithoreMule.setToggleGroup(muleType);

            RadioButton crystiteMule = new RadioButton("Crystite: +$100");
            crystiteMule.setToggleGroup(muleType);

            Button selectMuleType = new Button("Next");
            selectMuleType.setOnAction(arg0 -> {

                String selectedMuleType = ((RadioButton) muleType.getSelectedToggle()).getText();
                switch (selectedMuleType) {
                    case "Food: +$25":
                        itemPriceTotals[4] = itemPriceTotals[4] + 25;
                        System.out.println("equipMule: Mule Total: " + itemPriceTotals[4]);
                        break;
                    case "Energy: +$50":
                        itemPriceTotals[4] = itemPriceTotals[4] + 50;
                        System.out.println("equipMule: Mule Total: " + itemPriceTotals[4]);
                        break;
                    case "Smithore: +$75":
                        itemPriceTotals[4] = itemPriceTotals[4] + 75;
                        System.out.println("equipMule: Mule Total: " + itemPriceTotals[4]);
                        break;
                    case "Crystite: +$100":
                        itemPriceTotals[4] = itemPriceTotals[4] + 100;
                        System.out.println("equipMule: Mule Total: " + itemPriceTotals[4]);
                        break;
                    default:
                        break;
                }

                int finalBuyTotal = overallBuyTotal(itemPriceTotals);

                dialogStage.close();

                playerCheckout(current, totalItemsToBuy, totalItemsToSell, itemPriceTotals, itemSellPriceTotals,
                finalBuyTotal, finalSellTotal);
            });

            HBox hbox = createHBox(selectMuleType);
            VBox vBox = createVBox(equip, info, foodMule, energyMule, smithoreMule, crystiteMule, hbox);

            dialogStage.setScene(new Scene(vBox));

            dialogStage.show();
        }
    }

    private void handleBuyMule() {
        //check if they're purchasing mule
        //make sure it's not negative
        //make sure it's not more than one
        //make sure that number is available
        //ask what type of mule they would like (pop up?)

    }

    /**
     * Returns player to town
     * @throws IOException
     */
    private void returnToTown() throws IOException {
        Stage stage = (Stage) returnToTown.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/town.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
