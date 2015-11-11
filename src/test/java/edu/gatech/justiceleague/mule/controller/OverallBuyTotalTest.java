package edu.gatech.justiceleague.mule.controller;
import edu.gatech.justiceleague.mule.controller.StoreController;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

/**
 * Created by Donald on 11/9/2015.
 */
public class OverallBuyTotalTest {
    protected int sol1 = 10, sol2 = 17;
    StoreController storeController = new StoreController();

    @Test
    public void testOverallBuyTotal() {
        int[] test1 = {1, 3, 1, 2, 2, 1};
        int[] test2 = {2, 1, 0, 8, 4, 2};
        assertEquals(sol1, storeController.overallBuyTotal(test1));
        assertEquals(sol2, storeController.overallBuyTotal(test2));
    }
}
