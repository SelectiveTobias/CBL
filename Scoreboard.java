import java.util.*;
import java.io.*;

public class Scoreboard {
    Scanner scanner;
    File scoreboard;
    PrintWriter pw;
    String fileNameSource = "scoreboard.txt";

    public void writeScore(){
        try {
            pw = new PrintWriter(fileNameSource);
            pw.println("test");
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file due to");
            System.out.println(e);
        }
    }

    public void readScore(){
        try {
            scoreboard = new File(fileNameSource);
            scanner = new Scanner(scoreboard);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file due to");
            System.out.println(e);
        }
    }

}

