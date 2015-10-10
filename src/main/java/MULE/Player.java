package MULE;

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
        BUZZITE
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
    public PlayerTimer timer;
    private int score;
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
        } else if (race == Race.FLAPPER) {
            this.money = 1600;
        } else {
            this.money = 1000;
        }

        //initial resources amounts
        if (GamePlay.GAMECONFIG.getDifficulty() == GameConfig.Difficulty.BEGINNER) {
            this.food = 8;
            this.energy = 4;
            this.smithore = 0;
            this.crystite = 0;
            this.foodMule = 0;
            this.energyMule = 0;
            this.smithoreMule = 0;
            this.crystiteMule = 0;
        } else if (GamePlay.GAMECONFIG.getDifficulty() == GameConfig.Difficulty.INTERMEDIATE) {
            this.food = 4;
            this.energy = 2;
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

    /**
     * Get name
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Get color
     * @return player color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get race
     * @return player race
     */
    public Race getRace() {
        return race;
    }

    /**
     * Get number
     * @return player position in players array
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get money
     * @return player money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Set money
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Get food
     * @return player food
     */
    public int getFood() {
        return food;
    }

    /**
     * Set number of food resource
     * @param food
     */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     * Get energy
     * @return player energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Set number of energy resource
     * @param energy
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Get smithore
     * @return player smithore
     */
    public int getSmithore() {
        return smithore;
    }

    /**
     * Set number of smithore resource
     * @param smithore
     */
    public void setSmithore(int smithore) {
        this.smithore = smithore;
    }

    /**
     * Get crystite
     * @return player crystite
     */
    public int getCrystite() {
        return crystite;
    }

    /**
     * Set number of crystite resource
     * @param crystite
     */
    public void setCrystite(int crystite) {
        this.crystite = crystite;
    }

    /**
     * Get food mule
     * @return player food mule
     */
    public int getFoodMule() {
        return foodMule;
    }

    /**
     * Set number of mule resource for food
     * @param mule
     */
    public void setFoodMule(int mule) {
        this.foodMule = mule;
    }

    /**
     * Get energy mule
     *
     * @return player energy mule
     */
    public int getEnergyMule() {
        return energyMule;
    }

    /**
     * Set number of mule resource for energy
     *
     * @param mule
     */
    public void setEnergyMule(int mule) {
        this.energyMule = mule;
    }

    /**
     * Get smithore mule
     *
     * @return player mule smithore
     */
    public int getSmithoreMule() {
        return smithoreMule;
    }

    /**
     * Set number of smithore mule resource
     *
     * @param mule
     */
    public void setSmithoreMule(int mule) {
        this.smithoreMule = mule;
    }

    /**
     * Get crystite mule
     *
     * @return player crystite mule
     */
    public int getCrystiteMule() {
        return crystiteMule;
    }

    /**
     * Set number of crystite mule resource
     *
     * @param mule
     */
    public void setCrystiteMule(int mule) {
        this.crystiteMule = mule;
    }

    public int getMuleTotal() {
        return getFoodMule() + getEnergyMule() + getSmithoreMule() + getCrystiteMule();
    }

    /**
     * String representtion of player
     * @return string representation of player
     */
    public String toString() {
        return "name: " + name + ", race: " + race.toString() + ", color: " + color.toString();
    }

    /**
     * Get number of land resource
     * @return player land
     */
    public int getNumLand() {
        return numLand;
    }

    /**
     * Increase the number of land resources a player owns
     */
    public void incrementLand() {
        numLand = numLand + 1;
    }

    /**
     * Initialize the player's timer
     */
    public void initTimer() {
        timer = new PlayerTimer(this);
    }

    /**
     * Start the player's timer
     */
    public void startTime() {
        timer.startTime();
    }

    /**
     * Stop the player's timer early and return the number of seconds that are left
     *
     * @return The number of seconds left on the clock
     */
    public int stopTime() {
        return timer.stopTime();
    }

    public int getScore() {
        return score;
    }

    /**
     * Calculates and returns a player's score
     * @return
     */
    public void calcScore() {
        this.score = money + 500*numLand + energy + food + smithore + crystite;
    }

    @Override
    public int compareTo(Player other) {
        this.calcScore();
        other.calcScore();
        return this.score - other.getScore();
    }

    @Override
    public int hashCode() {
        this.calcScore();
        return this.score*17 + this.crystite + this.food + this.energy + this.smithore;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player))
            return false;
        if (obj == this)
            return true;

        Player other = (Player) obj;

        return this.number == other.getNumber();
    }
}
