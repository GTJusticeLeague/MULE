package edu.gatech.justiceleague.mule.model;

import java.util.concurrent.RejectedExecutionException;

/**
 * Store class keeps track of the store's inventory
 */
public class Store {

    private int numFood;
    private int numEnergy;
    private int numSmithore;
    private int numCrystite;
    private int numMule;

    /**
     * Store constructor. Takes in a difficulty to determine initial quantities of items.
     *
     * @param difficulty The game difficulty
     */
    public Store(GameConfig.Difficulty difficulty) {
        if (difficulty == GameConfig.Difficulty.BEGINNER) {
            numFood = 16;
            numEnergy = 16;
            numSmithore = 0;
            numCrystite = 0;
            numMule = 25;
        } else if (difficulty == GameConfig.Difficulty.INTERMEDIATE
                || difficulty == GameConfig.Difficulty.ADVANCED) {
            numFood = 8;
            numEnergy = 8;
            numSmithore = 8;
            numCrystite = 0;
            numMule = 14;
        } else {
            throw new RejectedExecutionException("Error: Invalid difficulty");
        }
    }

    public Store(int numFood, int numEnergy, int numSmithore, int numMule) {
        this.numFood = numFood;
        this.numEnergy = numEnergy;
        this.numSmithore = numSmithore;
        this.numMule = numMule;
    }

    /**
     * @return amount of food
     */
    public final int getNumFood() {
        return numFood;
    }

    /**
     * Set amount of food
     * @param numFood Amount to set
     */
    public final void setNumFood(int numFood) {
        this.numFood = numFood;
    }
    /**
     * @return amount of energy
     */
    public final int getNumEnergy() {
        return numEnergy;
    }

    /**
     * Set amount of energy
     * @param numEnergy Amount to set
     */
    public final void setNumEnergy(int numEnergy) {
        this.numEnergy = numEnergy;
    }
    /**
     * @return amount of smithore
     */
    public final int getNumSmithore() {
        return numSmithore;
    }

    /**
     * set amount of smithore
     * @param numSmithore Amount to set
     */
    public final void setNumSmithore(int numSmithore) {
        this.numSmithore = numSmithore;
    }
    /**
     * @return amount of crystite
     */
    public final int getNumCrystite() {
        return numCrystite;
    }

    /**
     * Set amount of crystite
     * @param numCrystite Amount to set
     */
    public final void setNumCrystite(int numCrystite) {
        this.numCrystite = numCrystite;
    }
    /**
     * @return number of mules
     */
    public final int getNumMule() {
        return numMule;
    }

    /**
     * set number of mules
     * @param numMule Amount to set
     */
    public final void setNumMule(int numMule) {
        this.numMule = numMule;
    }

    @Override
    public final String toString() {
        return "Store{"
                + "numFood=" + numFood
                + ", numEnergy=" + numEnergy
                + ", numSmithore=" + numSmithore
                + ", numCrystite=" + numCrystite
                + ", numMule=" + numMule
                + '}';
    }
}
