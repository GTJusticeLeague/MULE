package MULE;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by Donald on 9/27/2015.
 */
public class PlayerTimer {
    private int time;
    private int elapsedTime = 0;
    private Player current;
    private Timeline ticker;

    public PlayerTimer (Player current) {
        this.current = current;
        int food = current.getFood();
        int round = GamePlay.round;
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
        GamePlay.turnSeconds = time;
        ticker = new Timeline(new KeyFrame(Duration.seconds(1), ae -> increment()));
        ticker.setCycleCount(Animation.INDEFINITE);
    }
    private void increment() {
        elapsedTime++;
        System.err.println("Time remaining: " + (GamePlay.turnSeconds - elapsedTime));
        // TODO: show remaining time on the game screen
        if (elapsedTime >= GamePlay.turnSeconds) {
            // TODO: Alert that player change is happening
            ticker.stop();
            GamePlay.nextPlayer();
        }
    }

    public void startTime() {
        ticker.play();
    }

    public int stopTime() {
        ticker.stop();
        return GamePlay.turnSeconds - elapsedTime;
    }
}
