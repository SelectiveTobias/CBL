import java.util.*;
import java.io.*;

public class Scoreboard {
    Scanner scanner;
    File scoreboard;
    PrintWriter pw;
    String fileNameSource = "scoreboard.json";

    public void writeScore(){
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

    public void readScore(){
        try {
            content = new String(Files.readAllBytes(Paths.get(fileNameSource)), 
            standardCharsets.UTF_8);
            key = "\"highscore\":";
            int keyIndex = content.indexOf(key);
            if (keyIndex == -1) {
                System.err.println("JSON structure invalid: High score key not found.");
                return 0;
            }

            in startIndex = keyIndex + key.length();
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

}

