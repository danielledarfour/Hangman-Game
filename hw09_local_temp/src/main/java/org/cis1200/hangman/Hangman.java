package org.cis1200.hangman;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Constructor sets up game state.
 */
public class Hangman {
    private final String savedgamefilepath = "files/saved_game.txt";
    private final String nothing = "files/nothing.png";
    private final String head = "files/head.png";
    private final String body = "files/body.png";
    private final String arm1 = "files/arm1.png";
    private final String arm2 = "files/arm2.png";
    private final String leg1 = "files/leg1.png";
    private final String leg2 = "files/leg2.png";
    private final String face = "files/face.png";
    private static LinkedList<String> wrongLetters;
    private static LinkedList<String> correctLetters;
    private static LinkedList<String> userGuesses;
    private static List<String> bodyParts;
    private static List<String> guessWordList;
    private static String guessWord;
    private static Integer lCounter;
    private static boolean isRight;
    private static SaveGame file;

    public Hangman() {
        file = new SaveGame(savedgamefilepath);
        loadSavedGame();

        bodyParts = Arrays.asList(
                nothing, head, body, arm1,
                arm2, leg1, leg2, face
        );
    }

    //functions for testing
    public static void setGuessWord(String word) {
        file.resetFile();
        guessWord = word;
        guessWordList = new LinkedList<String>();

        for (int i = 0; i <= (guessWord.length() - 1); i++) {
            if (guessWord.charAt(i) == ' ') {
                guessWordList.add(" ");
            } else {
                guessWordList.add("_ ");
            }
        }
        updateGameState();
    }

    public static Integer getLCounter() {
        return lCounter;
    }

    public static boolean isRight() {
        return isRight;
    }

    public static void writeFile(
            String guessWord, String correctGuesses,
            String wrongGuesses, String allGuesses) {
        file.writeToFile(Arrays.asList(guessWord, correctGuesses, wrongGuesses, allGuesses));
        updateGameState();
    }
    //test functions concluded


    public static void reset() {
        RandomWordGenerator randomWord = new RandomWordGenerator();
        guessWord = randomWord.pickRandom();
        guessWordList = new LinkedList<>();
        wrongLetters = new LinkedList<>();
        correctLetters = new LinkedList<>();
        userGuesses = new LinkedList<>();
        lCounter = 0;
        isRight = false;

        for (int i = 0; i <= (guessWord.length() - 1); i++) {
            if (guessWord.charAt(i) == ' ') {
                guessWordList.add(" ");
            } else {
                guessWordList.add("_ ");
            }
        }


    }

    public static void loadSavedGame() {
        String[] gameData = file.restoreData();
        guessWord = gameData[0];
        String[] guessWordArray = guessWord.split("");

        String correctLettersString = gameData[1];
        String[] correctLettersArray = correctLettersString.split(" ");
        correctLetters = new LinkedList<>();
        guessWordList = new LinkedList<>();

        for (int i = 0; i < guessWord.length(); i++) {
            if (guessWord.charAt(i) == ' ') {
                guessWordList.add(" ");
            } else {
                guessWordList.add("_ ");
            }
        }

        for (int i = 0; i < guessWordArray.length; i++) {
            for (String letter : correctLettersArray) {
                if (letter.equals(guessWordArray[i])
                        && !correctLetters.contains(guessWordArray[i])) {
                    correctLetters.add(guessWordArray[i]);
                }
                if (letter.equals(guessWordArray[i])) {
                    guessWordList.set(i, guessWordArray[i]);
                }
            }
        }

        String wrongLettersString = gameData[2];
        String[] wrongLettersArray = wrongLettersString.split(" ");
        wrongLetters = new LinkedList<>();
        wrongLetters.addAll(Arrays.asList(wrongLettersArray));
        lCounter = wrongLetters.size();

        String userGuessesString = gameData[3];
        String[] userGuessesArray = userGuessesString.split(" ");
        userGuesses = new LinkedList<>();
        userGuesses.addAll(Arrays.asList(userGuessesArray));
        updateGameState();
    }

    public static void updateGameState() {
        if (userGuesses.size() == 0) {
            file.writeToFile(Arrays.asList(guessWord, " ", " ", " "));
        } else if (correctLetters.size() == 0) {
            file.writeToFile(Arrays.asList(guessWord, " ", getWrongLetters(), getUserGuesses()));
        } else if (wrongLetters.size() == 0) {
            file.writeToFile(Arrays.asList(guessWord, getCorrectLetters(), " ", getUserGuesses()));
        } else {
            file.writeToFile(Arrays.asList(guessWord, getCorrectLetters(),
                    getWrongLetters(), getUserGuesses()));
        }
    }


    public static void makeGuess(JButton clickedLetter, String c) {
        userGuesses.add(c);
        updateGameState();
        if (guessWord.contains(c)) {
            isRight = true;
            addRightGuess(clickedLetter, c);
        } else {
            isRight = false;
            addWrongGuess(clickedLetter, c);
        }
    }
    private static void addRightGuess(JButton clickedLetter, String c) {
        correctLetters.add(c);
        disableLetter(clickedLetter);
        for (int i = 0; i < guessWord.length(); i++) {
            if (guessWordList.get(i).equals(" ")) {
                continue;
            } else {
                if (Character.toString(guessWord.charAt(i)).equals(c)) {
                    guessWordList.set(i, c);
                }
            }
        }
        updateGameState();
    }

    private static void addWrongGuess(JButton clickedLetter, String c) {
        lCounter++;
        wrongLetters.add(c);
        disableLetter(clickedLetter);
        updateGameState();
    }

    public static String undo() {
        if (userGuesses.isEmpty()) {
            return "";
        }
        if (getGuessWordList().equals(guessWord)) {
            return "";
        }
        String removedLetter;
        if (isRight) {
            if (correctLetters.size() > 0) {
                removedLetter = correctLetters.removeLast();
            } else {
                removedLetter = "";
            }
            userGuesses.removeLast();
            if (userGuesses.isEmpty() ||
                    correctLetters.contains(userGuesses.getLast())) {
                isRight = true;
            } else {
                isRight = false;
            }
            for (int i = 0; i < guessWordList.size(); i++) {
                if (guessWordList.get(i).equals(removedLetter)) {
                    guessWordList.set(i, "_ ");
                }
            }
            updateGameState();
            return removedLetter;
        } else {
            if (wrongLetters.size() > 0) {
                lCounter--;
                removedLetter = wrongLetters.removeLast();
            } else {
                removedLetter = "";
            }
            userGuesses.removeLast();
            if (userGuesses.isEmpty() ||
                    wrongLetters.contains(userGuesses.getLast())) {
                isRight = false;
            } else {
                isRight = true;
            }
            updateGameState();
            return removedLetter;
        }
    }

    public static void disableLetter(JButton clickedLetter) {
        clickedLetter.setEnabled(false);
        clickedLetter.setOpaque(false);
        clickedLetter.setContentAreaFilled(false);
    }

    public static void enableLetter(JButton clickedLetter) {
        clickedLetter.setEnabled(true);
        clickedLetter.setOpaque(true);
        clickedLetter.setContentAreaFilled(true);
    }

    public static void revealSecretWord() {
        for (int i = 0; i < guessWordList.size(); i++) {
            guessWordList.set(i, Character.toString(guessWord.charAt(i)));
        }
        getGuessWordList();
    }

    public static String getGuessWordList() {
        StringBuilder result = new StringBuilder();
        for (String elt : guessWordList) {
            result.append(elt);
        }
        return result.toString();
    };

    public static String getCorrectLetters() {
        StringBuilder result = new StringBuilder();
        for (String elt : correctLetters) {
            result.append(elt).append(" ");
        }
        return result.toString();
    }

    public static String getWrongLetters() {
        StringBuilder result = new StringBuilder();
        for (String elt : wrongLetters) {
            result.append(elt).append(" ");
        }
        return result.toString();
    }

    public static String getUserGuesses() {
        StringBuilder result = new StringBuilder();
        for (String elt : userGuesses) {
            result.append(elt).append(" ");
        }
        return result.toString();
    }



    public static boolean isWinner() {
        return getGuessWordList().equals(guessWord);
    }

    public static boolean isLoser() {
        return (lCounter == 7);
    }
    public static String getImage() {
        return bodyParts.get(lCounter);
    }

}


