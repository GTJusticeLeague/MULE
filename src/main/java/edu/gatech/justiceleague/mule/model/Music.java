package main.java.edu.gatech.justiceleague.mule.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by jasbrown on 12/3/15.
 */
public class Music {

    private MediaPlayer player;

    /**
     * Create new instance of music
     * @param song should be in the format
     *             Paths.get("src/main/resources/music/MULE_Theme_Piano.mp3").toUri().toString()
     */
    public Music(String song) {
        Media media = new Media(song);
        player = new MediaPlayer(media);
    }

    public void startMusic() {
        player.play();
    }

    public void stopMusic() {
        player.stop();
    }
}
