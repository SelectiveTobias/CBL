import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Scoreboard {
    static PrintWriter pw;
    static String fileNameSource = "scoreboard.json";

    public static void writeScore(int newHighScore) {
        String jsonString = String.format("{\"highscore\": %d}", newHighScore);
        try {
            pw = new PrintWriter(fileNameSource);
            pw.print(jsonString);
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file due to");
            System.out.println(e);
        }
    }

    public static int readScore(){
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(fileNameSource)));
            String key = "\"highscore\":";
            int keyIndex = content.indexOf(key);
            if (keyIndex == -1) {
                System.err.println("JSON structure invalid: High score key not found.");
                return 0;
            }

            int startIndex = keyIndex + key.length();
            String numberString = content.substring(startIndex).trim().replace("}", "");

            return Integer.parseInt(numberString);

        } catch (FileNotFoundException e) {
            System.out.println("High score file not found. Starting with a score of 0.");
            return 0;
        } catch (IOException e) {
            System.err.println("Error reading high score file: " + e.getMessage());
            return 0;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing high score value: " + e.getMessage());
            return 0;
        }
    }