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

    public int getNumFood() {
        return numFood;
    }

    public void setNumFood(int numFood) {
        this.numFood = numFood;
    }

    public int getNumEnergy() {
        return numEnergy;
    }

    public void setNumEnergy(int numEnergy) {
        this.numEnergy = numEnergy;
    }

    public int getNumSmithore() {
        return numSmithore;
    }

    public void setNumSmithore(int numSmithore) {
        this.numSmithore = numSmithore;
    }

    public int getNumCrystite() {
        return numCrystite;
    }

    public void setNumCrystite(int numCrystite) {
        this.numCrystite = numCrystite;
    }

    public int getNumMule() {
        return numMule;
    }

    public void setNumMule(int numMule) {
        this.numMule = numMule;
    }
}
