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
    private Player current;
    private Timeline ticker;
    private Timeline timer;
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
        ticker = new Timeline(new KeyFrame(Duration.seconds(time), ae -> increment()));
        ticker.setCycleCount(Animation.INDEFINITE);
        timer = new Timeline(new KeyFrame(Duration.seconds(time), ae -> GamePlay.nextPlayer()));
    }
    private void increment() {
        time--;
    }

    public void startTime() {
        ticker.play();
        timer.play();
    }

    public int stopTime() {
        ticker.stop();
        timer.stop();
        return time++;
    }
}
