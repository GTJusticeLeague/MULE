package edu.gatech.justiceleague.mule.model;

/**
 * Created by Donald on 9/13/2015.
 * 9/13/2015 - Made Race/Color enums, created getters, made variables private
 */
public class Player implements Comparable<Player> {

    /**
     * Race of player
     */
    public enum Race {
        /**
         * Human race; most difficult
         */
        HUMAN,

        /**
         * Flapper race; intermediate
         */
        FLAPPER,
        /**
         * Bonzoid race; beginner
         */
        BONZOID,

        /**
         * Ugaite race; beginner
         */
        UGAITE,

        /**
         * Buzzite race; beginner
         */
        BUZZITE,

        /**
         * Flapper race; intermediate
         */
        BADONK,

        /**
         * Flapper race; intermediate
         */
        DOTHRAKI,
    }

    /**
     * Color for players' identification throughout the game
     */
    public enum Color {
        /**
         * Red
         */
        RED,

        /**
         * Green
         */
        GREEN,

        /**
         * Blue
         */
        BLUE,

        /**
         * Yellow
         */
        YELLOW,

        /**
         * Purple
         */
        PURPLE
    }

    private final String name;
    private final Race race;
    private final Color color;
    private final int number;
    private int money;
    private int food;
    private int energy;
    private int smithore;
    private int crystite;
    private int foodMule;
    private int energyMule;
    private int smithoreMule;
    private int crystiteMule;
    private int numLand;
    private transient PlayerTimer timer;
    /**
     * Initialization of players. Sets race, color (which cannot
     * be the same as other players),
     * @param name player name
     * @param race player race
     * @param color player color
     * @param number position of player in player array
     */
    public Player(String name, Race race, Color color, int number) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.number = number;

        //initial money amount
        if (race == Race.HUMAN) {
            this.money = 600;
        } else if (race == Race.FLAPPER || race == Race.BADONK || race == Race.DOTHRAKI) {
            this.money = 1600;
        } else {
            this.money = 1000;
        }

        //initial resources amounts
        if (GamePlay.getGameConfig().getDifficulty() == GameConfig.Difficulty.BEGINNER) {
            this.food = 8;
            this.energy = 4;
            this.smithore = 0;
            this.crystite = 0;
            this.foodMule = 0;
            this.energyMule = 0;
            this.smithoreMule = 0;
            this.crystiteMule = 0;
        } else {
            this.food = 4;
            this.energy = 2;
            this.smithore = 0;
            this.crystite = 0;
            this.foodMule = 0;
            this.energyMule = 0;
            this.smithoreMule = 0;
            this.crystiteMule = 0;
        }

    }

    public Player(String name, Race race, Color color, int number, int money, int food, int energy, int smithore, int crystite, int foodMule, int energyMule, int smithoreMule, int crystiteMule, int numLand) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.number = number;
        this.money = money;
        this.food = food;
        this.energy = energy;
        this.smithore = smithore;
        this.crystite = crystite;
        this.foodMule = foodMule;
        this.energyMule = energyMule;
        this.smithoreMule = smithoreMule;
        this.crystiteMule = crystiteMule;
        this.numLand = numLand;
    }

    /**
     * Get name
     * @return player name
     */
    public final String getName() {
        return name;
    }

    /**
     * Get color
     * @return player color
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Get race
     * @return player race
     */
    public final Race getRace() {
        return race;
    }

    /**
     * Get number
     * @return player position in players array
     */
    public final int getNumber() {
        return number;
    }

    /**
     * Get money
     * @return player money
     */
    public final int getMoney() {
        return money;
    }

    /**
     * Set money
     * @param money money for a player
     */
    public final void setMoney(int money) {
        this.money = money;
    }

    /**
     * Get food
     * @return player food
     */
    public final int getFood() {
        return food;
    }

    /**
     * Set number of food resource
     * @param food food for a player
     */
    public final void setFood(int food) {
        this.food = food;
    }

    /**
     * Get energy
     * @return player energy
     */
    public final int getEnergy() {
        return energy;
    }

    /**
     * Set number of energy resource
     * @param energy for a player
     */
    public final void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Get smithore
     * @return player smithore
     */
    public final int getSmithore() {
        return smithore;
    }

    /**
     * Set number of smithore resource
     * @param smithore for a player
     */
    public final void setSmithore(int smithore) {
        this.smithore = smithore;
    }

    /**
     * Get crystite
     * @return player crystite
     */
    public final int getCrystite() {
        return crystite;
    }

    /**
     * Set number of crystite resource
     * @param crystite yes
     */
    public final void setCrystite(int crystite) {
        this.crystite = crystite;
    }

    /**
     * Get food mule
     * @return player food mule
     */
    public final int getFoodMule() {
        return foodMule;
    }

    /**
     * Set number of mule resource for food
     * @param mule mule total
     */
    public final void setFoodMule(int mule) {
        this.foodMule = mule;
    }

    /**
     * Get energy mule
     *
     * @return player energy mule
     */
    public final int getEnergyMule() {
        return energyMule;
    }

    /**
     * Set number of mule resource for energy
     *
     * @param mule number to be set
     */
    public final void setEnergyMule(int mule) {
        this.energyMule = mule;
    }

    /**
     * Get smithore mule
     *
     * @return player mule smithore
     */
    public final int getSmithoreMule() {
        return smithoreMule;
    }

    /**
     * Set number of smithore mule resource
     *
     * @param mule number for mule
     */
    public final void setSmithoreMule(int mule) {
        this.smithoreMule = mule;
    }

    /**
     * Get crystite mule
     *
     * @return player crystite mule
     */
    public final int getCrystiteMule() {
        return crystiteMule;
    }

    /**
     * Set number of crystite mule resource
     *
     * @param mule number for crystite mule
     */
    public final void setCrystiteMule(int mule) {
        this.crystiteMule = mule;
    }


    /**
     * Gets the total number of mules a player has.
     *
     * @return total number of mules
     */
    public final int getMuleTotal() {
        return getFoodMule() + getEnergyMule() + getSmithoreMule() + getCrystiteMule();
    }

    /**
     * Get the count of this type mule that the player owns
     *
     * @param muleType Type of the mule
     * @return Count of this type
     */
    public final int getMuleCount(String muleType) {
        switch (muleType) {
            case "FOOD":
                return getFoodMule();
            case "ENERGY":
                return getEnergyMule();
            case "SMITHORE":
                return getSmithoreMule();
            case "CRYSTITE":
                return getCrystiteMule();
            default:
                return 0;
        }
    }

    /**
     * String representtion of player
     * @return string representation of player
     */
    public final String toString() {
        return "name: " + name + ", race: " + race.toString() + ", color: " + color.toString();
    }

    /**
     * Get number of land resource
     * @return player land
     */
    public final int getNumLand() {
        return numLand;
    }

    /**
     * Increase the number of land resources a player owns
     */
    public final void incrementLand() {
        numLand = numLand + 1;
    }

    /**
     * Initialize the player's timer
     */
    public final void initTimer() {
        timer = new PlayerTimer(this);
    }

    /**
     * Start the player's timer
     */
    public final void startTime() {
        timer.startTime();
    }

    /**
     * Stop the player's timer early and return the number of seconds that are left
     *
     * @return The number of seconds left on the clock
     */
    public final int stopTime() {
        if (timer != null) {
            return timer.stopTime();
        } else {
            return 0;
        }
    }

    /**
     * Calculates and sets score
     * @return score
     */
    public final int getScore() {
        return money + 500 * numLand + energy + food + smithore + crystite;
    }

    public final PlayerTimer getTimer() {
        return timer;
    }

    /**
     * Compares one player to another
     * @param other player to compare
     * @return whether player is greater than or less than other
     */
    @Override
    public final int compareTo(Player other) {
        return this.getScore() - other.getScore();
    }

    /**
     * @return hashcode for player
     */
    @Override
    public final int hashCode() {
        return this.getScore() * 17 + this.crystite + this.food + this.energy + this.smithore;
    }

    /**
     * @param obj to compare
     * @return true if equal, false if not
     */
    @Override
    public final boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Player other = (Player) obj;
        return this.getNumber() == other.getNumber();
    }
}
