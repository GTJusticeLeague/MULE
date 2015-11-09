package edu.gatech.justiceleague.mule.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckAmountsAreAvailableTest {

    @Test
    public void amountsAreNotAvailable() {
        assertEquals(3, 5); //fails
    }

    @Test
    public void amountsAreAvailable() {
        assertEquals(3, 3); //passes
    }
}
