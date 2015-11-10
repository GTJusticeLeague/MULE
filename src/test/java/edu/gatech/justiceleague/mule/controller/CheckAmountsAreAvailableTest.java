package edu.gatech.justiceleague.mule.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckAmountsAreAvailableTest {

    @Test
    public void amountsAreAvailable() {
        StoreController store = new StoreController();
        int[] totalItemsToBuy = {10, 2, 4, 5, 1};
        int[] itemsAvailable = {10, 10, 10, 10, 10};
        boolean canBuy = store.checkAmountsAreAvailable(totalItemsToBuy, itemsAvailable);
        assertEquals(true, canBuy);
    }

    @Test
    public void amountsAreNotAvailable() {
        StoreController store = new StoreController();
        int[] totalItemsToBuy = {12, 34, 2, 35, 7};
        int[] itemsAvailable = {10, 10, 10, 10, 10};
        boolean canBuy = store.checkAmountsAreAvailable(totalItemsToBuy, itemsAvailable);
        assertEquals(false, canBuy);
    }
}
