import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Class responsible for reading and writing the game's high score
 * to a JSON file.
 * This class provides two static operations: writing a new high score and
 * reading the current high score from the file specified by the fileNameSource variable.
 */
public class Scoreboard {

    static PrintWriter pw;
    static String fileNameSource = "scoreboard.json";

    /**
     * Overwrite the scoreboard file with a new high score value.
     *
     * @param newHighScore the high score to write to disk
     */
    public static void writeScore(int newHighScore) {
        String jsonString = String.format("{\"highscore\": %d}", newHighScore); //Format the new high score as a JSON string (adding the necessary JSON syntax)
        try {
            pw = new PrintWriter(fileNameSource); //Create a PrintWriter object to write to the specified file
            pw.print(jsonString); //Write the JSON string to the file
            pw.close();
        } catch (FileNotFoundException e) { //If the file cannot be found or created, print an error message
            System.err.println("Error writing high score to file: " + e.getMessage());
        }
    }

    /**
     * Read the high score from the scoreboard file.
     * This method performs a simple, tolerant parse of a JSON object that
     * contains a top-level numeric property named "highscore". If the file
     * is missing, malformed, or the value cannot be parsed, this method
     * returns 0.
     *
     * @return the parsed high score, or 0 on error or when the value is
     *     missing/unparseable
     */
    public static int readScore() {
        String content = ""; //Set the variable content to an empty string
        try {
            content = new String(Files.readAllBytes(Paths.get(fileNameSource))); //Read all the file contents and store it in content
            String key = "\"highscore\":"; //Set the key variable to the string "highscore": this is used to find where the score value starts
            int keyIndex = content.indexOf(key); //Find the index of the key in the content string (the position where the key starts in the string)
            if (keyIndex == -1) { //If the key is not found, return an error message and set the score to 0.
                System.err.println("JSON structure invalid: High score key not found.");
                return 0;
            }

            int startIndex = keyIndex + key.length(); //Calculate the starting index of the score value by adding the key's length to its index
            String numberString = content.substring(startIndex).trim().replace("}", ""); //Sets numberstring to the string of content starting and the startIndex (deletes everything in front of the startIndex) and removes the closing curly brace and whitespace.

            return Integer.parseInt(numberString); //Parse and return the high score value

        } catch (FileNotFoundException e) { //If the file is not found, return an error message and set the score to 0.
            System.err.println("Score file not found. Returning default score of 0.");
            return 0;
        } catch (IOException e) { //If there is an IO error, return an error message and set the score to 0.
            System.err.println("Error reading score file: " + e.getMessage());
            return 0;
        } catch (NumberFormatException e) { //If there is an error parsing the number, return an error message and set the score to 0.
            System.err.println("Error parsing high score from file: " + e.getMessage());
            return 0;
        }
    }
}