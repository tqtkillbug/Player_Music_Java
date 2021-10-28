package com.tientran.player.musicplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.SAXException;

public class HelloController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label singerName;
    @FXML
    private Label songName;
    @FXML
    private Label songTime;
    @FXML
    private Button playButton, pauseButton, nextButton, perviousButton, loopButton, randomButtonq;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volumeBar;
    @FXML
    private ProgressBar songProgressBar;

    private Media media;
    private MediaPlayer mediaPlayer;

    @FXML
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private int[] speeds = {75, 100, 125, 150, 175};
    private Timer timer;
    private TimerTask task;
    private boolean running;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs = new ArrayList<>();
        directory = new File("Music");
        files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                songs.add(file);
                System.out.println(file);
            }
        }
        initMedia();
        for (int i = 0; i < speeds.length; i++) {
            speedBox.getItems().add(Integer.toString(speeds[i]));
        }
        speedBox.setOnAction(this::changeSpeed);
        volumeBar.valueProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeBar.getValue() * 0.01);
            }
        });

    }


    public void getSongInfo()  {
        String fileLocation = songs.get(songNumber).toPath().toString();
        try {
            InputStream input = new FileInputStream(fileLocation);
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();
//           System.out.println("Genre : " + metadata.get("xmpDM:genre"));
            songName.setText( "");
            singerName.setText(metadata.get("xmpDM:artist"));
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
    }

    public void initMedia() {
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        getSongInfo();

    }

    public void playSong() {
        mediaPlayer.play();
        getSongInfo();
        Duration songTimes =   mediaPlayer.getCurrentTime();
        double songTimeSecon = songTimes.toSeconds();
        String songTimesString = String.valueOf(songTimeSecon);
        songTime.setText(songTimesString);
    }

    public void pauseSong() {
        mediaPlayer.pause();
    }

    public void nextSong() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();
            initMedia();
            playSong();
        } else {
            songNumber = 0;
            mediaPlayer.stop();
            initMedia();
            playSong();
        }
    }

    public void perviousSong() {
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();
            initMedia();
            playSong();
        }
    }

    public void loopSong() {
        Duration currentTime = mediaPlayer.getCurrentTime();
        Duration duration = mediaPlayer.getTotalDuration();
        if (currentTime == duration){

        }

    }

    public void randomSong() {

    }

    public void changeVolume() {

    }

    public void changeSpeed(ActionEvent event) {
        mediaPlayer.setRate(Integer.parseInt(speedBox.getValue()) * 0.01);
    }

    public void beginTime() {

    }

    public void canelTime() {

    }
}