package edu.gatech.justiceleague.mule.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test the method curPlayerHasLowScore in GamePlay
 */
public class CurPlayerHasLowScoreTest {

    @Test
    public void lowScore() {
        try {
            GamePlay.setGameConfig(new GameConfig(GameConfig.Difficulty.BEGINNER, GameConfig.MapType.STANDARD, 4));
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize everything
        Player players[] = new Player[GamePlay.getGameConfig().getNumPlayers()];
        for (int i = 0; i < GamePlay.getGameConfig().getNumPlayers(); i++) {
            Player tempPlayer = new Player("Test", Player.Race.HUMAN, Player.Color.RED, i);
            tempPlayer.setMoney(100 - i);
            players[i] = tempPlayer;
        }
        GamePlay.getGameConfig().setPlayers(players);

        // Test Player 0
        GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[0]);
        boolean test0 = GamePlay.curPlayerHasLowScore();
        assertFalse(test0);

        // Test Player 1
        GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[1]);
        boolean test1 = GamePlay.curPlayerHasLowScore();
        assertFalse(test1);

        // Test Player 2
        GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[2]);
        boolean test2 = GamePlay.curPlayerHasLowScore();
        assertFalse(test2);

        // Test Player 3 (this one should have lowest score because lowest money)
        GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[3]);
        boolean test3 = GamePlay.curPlayerHasLowScore();
        assertTrue(test3);
    }
}
