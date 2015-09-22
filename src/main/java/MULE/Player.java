package MULE;

/**
 * Created by Donald on 9/13/2015.
 * 9/13/2015 - Made Race/Color enums, created getters, made variables private
 */
public class Player {

    public enum Race {
        HUMAN,
        FLAPPER,
        BONZOID,
        UGAITE,
        BUZZITE
    }

    public enum Color {
        RED,
        GREEN,
        BLUE,
        YELLOW,
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
    private int mule;
    private int numLand;

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
            this.mule = 0;
        } else if (GamePlay.GAMECONFIG.getDifficulty() == GameConfig.Difficulty.INTERMEDIATE) {
            this.food = 4;
            this.energy = 2;
            this.smithore = 0;
            this.crystite = 0;
            this.mule = 0;
        } else {
            this.food = 4;
            this.energy = 2;
            this.smithore = 0;
            this.crystite = 0;
            this.mule = 0;
        }

    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Race getRace() {
        return race;
    }

    public int getNumber() {
        return number;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getSmithore() {
        return smithore;
    }

    public void setSmithore(int smithore) {
        this.smithore = smithore;
    }

    public int getCrystite() {
        return crystite;
    }

    public void setCrystite(int crystite) {
        this.crystite = crystite;
    }

    public int getMule() {
        return mule;
    }

    public void setMule(int mule) {
        this.mule = mule;
    }

    public String toString() {
        return "name: " + name + ", race: " + race.toString() + ", color: " + color.toString();
    }

    public int getNumLand() {
        return numLand;
    }

    public void incrementLand() {
        numLand = numLand + 1;
    }
}
