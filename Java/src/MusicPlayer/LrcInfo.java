package MusicPlayer;
import java.util.HashMap;
import java.util.Map;

public class LrcInfo {
    private String title;
    private String singer;
    private String album;
    private HashMap<Long,String> infos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public HashMap<Long, String> getInfos() {
        return infos;
    }

    public void setInfos(HashMap<Long, String> infos) {
        this.infos = infos;
    }

    public void setInfos(Map<Long, String> maps) {
    }
}