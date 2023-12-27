package org.cis1200.hangman;

import java.io.*;
import java.util.List;

public class SaveGame {
    private BufferedReader br;
    private final String savedgamefilepath = "files/saved_game.txt";

    public SaveGame(String filePath) {
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error with br initialization");
        }
    }

    public void writeToFile(List<String> stringsToWrite) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(savedgamefilepath, false));
            for (String data : stringsToWrite) {
                bw.write(data);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    public void resetFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(savedgamefilepath, false));
            for (int i = 0; i < 4; i++) {
                bw.write("");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error emptying file");
        }
    }

    public boolean isEmpty() {
        File file = new File(savedgamefilepath);
        return file.length() == 0;
    }

    public String[] restoreData() {
        String[] data = new String[4];
        int i = 0;
        try {
            while (i < 4) {
                data[i] = br.readLine();
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error reading lines");
        }
        return data;
    }

    public String getFilePath() {
        return savedgamefilepath;
    }

}
