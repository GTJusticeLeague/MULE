package edu.gatech.justiceleague.mule.model;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Wrapper to make a timer easier to use
 */
public class PlayerTimer {
    private int elapsedTime = 0;
    private Timeline ticker;

    /**
     * Constructor for Player Timer
     * @param current player
     */
    public PlayerTimer(Player current) {
        int food = current.getFood();
        int round = GamePlay.getRound();
        int time;
        if (round > 0 && round < 5) {
            if (food > 3) {
                time = 50;
            } else if (food == 0) {
                time = 5;
            } else {
                time = 30;
            }
        } else if (round > 4 && round < 9) {
            if (food > 4) {
                time = 50;
            } else if (food == 0) {
                time = 5;
            } else {
                time = 30;
            }
        } else {
            if (food > 5) {
                time = 50;
            } else if (food == 0) {
                time = 5;
            } else {
                time = 30;
            }
        }
        GamePlay.setTurnSeconds(time);
        ticker = new Timeline(new KeyFrame(Duration.seconds(1), ae -> increment()));
        ticker.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Increases elapsed time and stops player turn if time is over
     */
    private void increment() {
        elapsedTime++;
        if (elapsedTime >= GamePlay.getTurnSeconds()) {
            // TODO: Alert that player change is happening
            ticker.stop();
            GamePlay.nextPlayer();
        }
    }

    /**
     * @return time remaining in turn
     */
    public int getRemainingTime() {
        return GamePlay.getTurnSeconds() - elapsedTime;
    }

    /**
     * start the timer
     */
    public void startTime() {
        ticker.play();
    }

    /**
     * Stop timer and
     * @return time remaining
     */
    public int stopTime() {
        ticker.stop();
        return GamePlay.getTurnSeconds() - elapsedTime;
    }
}
