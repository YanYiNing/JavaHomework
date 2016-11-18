package FileSearch;
import java.io.*;
import java.util.Scanner;

public class FileSearch implements Runnable {
    public static void main(String[] args) {
        FileSearch fileSearch = new FileSearch();
        Thread thread = new Thread(fileSearch);
        thread.start();
    }
    public void run(){
        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Please input the file's adress :");
            String fileAdress = in.nextLine();
            File myFile = new File(fileAdress);
            FileReader fileReader = new FileReader(myFile);
            BufferedReader reader = new BufferedReader(fileReader);
            System.out.println("Please input the keywords :");
            String keyWords = in.nextLine();
            String line = null;
            for (int i = 1; (line = reader.readLine()) != null; i++){
                if (line.contains(keyWords))
                    System.out.println(keyWords + " is in the "+ i + " line and from the left to right the " + line.indexOf(keyWords));
            }
            reader.close();
        }catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
