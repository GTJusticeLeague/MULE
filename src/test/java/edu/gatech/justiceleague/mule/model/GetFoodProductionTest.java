package edu.gatech.justiceleague.mule.model;

import edu.gatech.justiceleague.mule.model.Mule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the method getFoodProduction in Mule
 */
public class GetFoodProductionTest {

    @Test
    public void getFoodProductionTest() {

        Player testPlayer = new Player("Daniel", Player.Race.HUMAN, Player.Color.RED, 1);

        //No production from the town tile, so default case should occur
        Mule testerMuleDefault = new Mule(Mule.MULETYPE.FOOD, testPlayer, Tile.Terrain.TOWN);
        assertEquals(testerMuleDefault.getFoodProduction(), 0);

        //Test cases for food production with varied valid terrains.
        Mule testerMule = new Mule(Mule.MULETYPE.FOOD, testPlayer, Tile.Terrain.PLAIN);
        assertEquals(testerMule.getFoodProduction(), 2);

        Mule testerMule2 = new Mule(Mule.MULETYPE.FOOD, testPlayer, Tile.Terrain.RIVER);
        assertEquals(testerMule2.getFoodProduction(), 4);

        Mule testerMule3 = new Mule(Mule.MULETYPE.FOOD, testPlayer, Tile.Terrain.ONEMOUNTAIN);
        assertEquals(testerMule3.getFoodProduction(), 1);

        Mule testerMule4 = new Mule(Mule.MULETYPE.FOOD, testPlayer, Tile.Terrain.TWOMOUNTAIN);
        assertEquals(testerMule4.getFoodProduction(), 5);

        Mule testerMule5 = new Mule(Mule.MULETYPE.FOOD, testPlayer, Tile.Terrain.THREEMOUNTAIN);
        assertEquals(testerMule5.getFoodProduction(), 1);


    }
}
