//package edu.gatech.justiceleague.mule.controller;
//import edu.gatech.justiceleague.mule.model.*;
//import edu.gatech.justiceleague.mule.model.Player.Color;
//import edu.gatech.justiceleague.mule.model.Player.Race;
//
//public class GameTester {
//
//    public GameTester() {
//        try {
//            GamePlay.setGameConfig(new GameConfig(GameConfig.Difficulty.BEGINNER, GameConfig.MapType.STANDARD, 4));
//        } catch (java.io.FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        // Initialize everything
//        Player players[] = new Player[GamePlay.getGameConfig().getNumPlayers()];
//        for (int i = 0; i < GamePlay.getGameConfig().getNumPlayers(); i++) {
//            Player tempPlayer = new Player("Test", Player.Race.HUMAN, Player.Color.RED, i);
//            tempPlayer.setMoney(100 - i);
//            players[i] = tempPlayer;
//        }
//        GamePlay.getGameConfig().setPlayers(players);
//    }
//
//    public void setCurrentPlayer(int player) {
//        GamePlay.setCurrentPlayer(GamePlay.getGameConfig().getPlayers()[player]);
//    }
//
//    public Player getCurrentPlayer() {
//        return GamePlay.getCurrentPlayer();
//
//    }
//
//    public Player getCurrentPlayerNumber() {
//        return GamePlay.getCurrentPlayer();
//
//    }
//
//
//    /**
//     * Set money
//     * @param money money for a player
//     */
//    public final void setMoney(int money) {
//        GamePlay.getCurrentPlayer().setMoney(money);
//    }
//
//    public int getMoney() {
//        return GamePlay.getCurrentPlayer().getMoney();
//    }
//
//
//    /**
//     * Set number of food resource
//     * @param food food for a player
//     */
//    public final void setFood(int food) {
//        GamePlay.getCurrentPlayer().setFood(food);
//    }
//
//
//    /**
//     * Set number of energy resource
//     * @param energy for a player
//     */
//    public final void setEnergy(int energy) {
//        GamePlay.getCurrentPlayer().setEnergy(energy);;
//    }
//
//    /**
//     * Set number of smithore resource
//     * @param smithore for a player
//     */
//    public final void setSmithore(int smithore) {
//        GamePlay.getCurrentPlayer().setSmithore(smithore);
//    }
//
//    /**
//     * Set number of crystite resource
//     * @param crystite yes
//     */
//    public final void setCrystite(int crystite) {
//        GamePlay.getCurrentPlayer().setCrystite(crystite);
//    }
//
//
//    /**
//     * Set number of mule resource for food
//     * @param mule mule total
//     */
//    public final void setFoodMule(int mule) {
//        GamePlay.getCurrentPlayer().setFoodMule(mule);;
//    }
//
//
//    /**
//     * Set number of mule resource for energy
//     *
//     * @param mule number to be set
//     */
//    public final void setEnergyMule(int mule) {
//        GamePlay.getCurrentPlayer().setEnergyMule(mule);
//    }
//
//    /**
//     * Set number of smithore mule resource
//     *
//     * @param mule number for mule
//     */
//    public final void setSmithoreMule(int mule) {
//        GamePlay.getCurrentPlayer().setSmithoreMule(mule);
//    }
//
//
//    /**
//     * Set number of crystite mule resource
//     *
//     * @param mule number for crystite mule
//     */
//    public final void setCrystiteMule(int mule) {
//        GamePlay.getCurrentPlayer().setCrystiteMule(mule);
//    }
//
//
//}