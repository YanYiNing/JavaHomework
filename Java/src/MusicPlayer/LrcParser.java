package MusicPlayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrcParser implements Runnable{
    private LrcInfo lrcinfo = new LrcInfo();

    private long currentTime = 0;
    private String currentContent = null;
    private Map<Long, String> maps = new HashMap<Long, String>();

    private InputStream readLrcFile(String path) throws FileNotFoundException {
        File f = new File(path);
        InputStream ins = new FileInputStream(f);
        return ins;
    }

    public LrcInfo parser(String path) throws Exception {
        InputStream in = readLrcFile(path);
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inr);
        String line = null;
        while ((line = reader.readLine()) != null) {
            parserLine(line);
        }
        lrcinfo.setInfos(maps);
        return lrcinfo;
    }

    private void parserLine(String str) {
        if (str.startsWith("[ti:")) {
            String title = str.substring(4, str.length() - 1);
            System.out.println("title--->" + title);
            lrcinfo.setTitle(title);
        }
        else if (str.startsWith("[ar:")) {
            String singer = str.substring(4, str.length() - 1);
            System.out.println("singer--->" + singer);
            lrcinfo.setSinger(singer);
        }
        else if (str.startsWith("[by:")) {
            String album = str.substring(4, str.length() - 1);
            System.out.println("album--->" + album);
            lrcinfo.setAlbum(album);
        }
        else {
            String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                String msg = matcher.group();
                int start = matcher.start();
                int end = matcher.end();
                int groupCount = matcher.groupCount();
                for (int i = 0; i <= groupCount; i++) {
                    String timeStr = matcher.group(i);
                    if (i == 1) {
                        currentTime = strToLong(timeStr);
                    }
                }
                String[] content = pattern.split(str);
                for (int i = 0; i < content.length; i++) {
                    if (i == content.length - 1) {
                        currentContent = content[i];
                    }
                }
                maps.put(currentTime, currentContent);
                System.out.println("put---currentTime--->" + currentTime
                        + "----currentContent---->" + currentContent);

            }
        }
    }

    private long strToLong(String timeStr) {
        String[] s = timeStr.split(":");
        int min = Integer.parseInt(s[0]);
        String[] ss = s[1].split("\\.");
        int sec = Integer.parseInt(ss[0]);
        int mill = Integer.parseInt(ss[1]);
        return min * 60 * 1000 + sec * 1000 + mill * 10;
    }

    public void run(){
        LrcParser lp = new LrcParser();
        Scanner in = new Scanner(System.in);
        String filesAdress = in.nextLine();
        try {
            lp.parser(filesAdress);
        } catch (Exception e) {
            System.out.println("parser error");
            e.printStackTrace();
        }
    }
}
