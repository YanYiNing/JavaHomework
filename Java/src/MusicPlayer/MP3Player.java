package MusicPlayer;

import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;

public class MP3Player implements Runnable{
    private String filename;
    private Player player;
    public boolean isPlaying = false;
    public MP3Player(String filename) {
        this.filename = filename;
    }

    public void play() {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
            player = new Player(buffer);
            player.play();
            isPlaying = true;
        } catch (Exception e) {
            System.out.println("Error!");
            System.out.println(e);
        }
    }
    public void run() {
        Scanner in = new Scanner(System.in);
        String filesAdress = in.nextLine();
        MP3Player mp3 = new MP3Player(filesAdress);
        mp3.play();
    }

}