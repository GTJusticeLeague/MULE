package edu.gatech.justiceleague.mule.model;

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
            System.out.println("Error: Invalid difficulty");
            System.exit(1);
        }
    }

    /**
     * @return amount of food
     */
    public int getNumFood() {
        return numFood;
    }

    /**
     * Set amount of food
     * @param numFood
     */
    public void setNumFood(int numFood) {
        this.numFood = numFood;
    }
    /**
     * @return amount of energy
     */
    public int getNumEnergy() {
        return numEnergy;
    }

    /**
     * Set amount of energy
     * @param numEnergy
     */
    public void setNumEnergy(int numEnergy) {
        this.numEnergy = numEnergy;
    }
    /**
     * @return amount of smithore
     */
    public int getNumSmithore() {
        return numSmithore;
    }

    /**
     * set amount of smithore
     * @param numSmithore
     */
    public void setNumSmithore(int numSmithore) {
        this.numSmithore = numSmithore;
    }
    /**
     * @return amount of crystite
     */
    public int getNumCrystite() {
        return numCrystite;
    }

    /**
     * Set amount of crystite
     * @param numCrystite
     */
    public void setNumCrystite(int numCrystite) {
        this.numCrystite = numCrystite;
    }
    /**
     * @return number of mules
     */
    public int getNumMule() {
        return numMule;
    }

    /**
     * set number of mules
     * @param numMule
     */
    public void setNumMule(int numMule) {
        this.numMule = numMule;
    }
}
