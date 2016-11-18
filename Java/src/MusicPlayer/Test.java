package MusicPlayer;

public class Test {
    public static void main(String[] args) {
        LrcParser lrcRun = new LrcParser();
        MP3Player mp3Run = new MP3Player("The MP3");
        Thread lrc = new Thread(lrcRun);
        Thread mp3 = new Thread(mp3Run);
        lrc.start();
        mp3.start();
    }
}
