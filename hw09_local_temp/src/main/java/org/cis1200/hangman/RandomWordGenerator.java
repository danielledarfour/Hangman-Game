package org.cis1200.hangman;

import java.io.*;
import java.util.*;

public class RandomWordGenerator implements Iterator<String> {
    private BufferedReader br;
    private String next;
    private String past;
    private final static String RANDOMWORD_FILEPATH = "files/words.csv";

    public RandomWordGenerator() {
        BufferedReader reader;
        try {
            reader = new BufferedReader((new FileReader(RANDOMWORD_FILEPATH)));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File DNE");
        }

        br = reader;

        try {
            past = null;
            next = br.readLine();
        } catch (IOException e) {
            next = null;
            System.out.println("IO error");
        }
    }

    public String pickRandom() {
        int randomNum = (int) (Math.random() * (32));
        int currentRow = 0;
        while (hasNext()) {
            String line = next();
            if (currentRow == randomNum) {
                return line;
            }
            currentRow++;
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public String next() {
        String temp;
        try {
            if (hasNext()) {
                temp = next;
                past = temp;
                next = br.readLine();
                if (next == null) {
                    br.close();
                }
                return past;
            } else {
                throw new NoSuchElementException();
            }
        } catch (IOException e) {
            past = null;
            next = null;
            return null;
        }
    }

}
