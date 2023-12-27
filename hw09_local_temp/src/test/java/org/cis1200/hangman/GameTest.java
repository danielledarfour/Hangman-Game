package org.cis1200.hangman;

import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {
    @Test
    public void testMakeRightGuess() {
        Hangman hangman = new Hangman();
        hangman.reset();
        hangman.setGuessWord("APPLE");
        JButton letter = new JButton("A");
        hangman.makeGuess(letter, "A");

        assertEquals(0, hangman.getLCounter());
        assertTrue(hangman.getWrongLetters().isEmpty());
        assertEquals("files/nothing.png", hangman.getImage());
        assertEquals("A ", hangman.getCorrectLetters());
        assertEquals("A_ _ _ _ ", hangman.getGuessWordList());
    }

    @Test
    public void testMakeWrongGuess() {
        Hangman hangman = new Hangman();
        hangman.reset();
        hangman.setGuessWord("APPLE");
        JButton letter = new JButton("B");
        hangman.makeGuess(letter, "B");

        assertEquals("B ", hangman.getWrongLetters());
        assertEquals(1, hangman.getLCounter());
        assertEquals("files/head.png", hangman.getImage());
        assertTrue(hangman.getCorrectLetters().isEmpty());
        assertEquals("_ _ _ _ _ ", hangman.getGuessWordList());
    }

    @Test
    public void testOneUndoAfterWrongThenRight() {
        Hangman hangman = new Hangman();
        hangman.reset();
        hangman.setGuessWord("APPLE");
        JButton letter = new JButton("B");
        hangman.makeGuess(letter, "B");
        hangman.setGuessWord("APPLE");
        JButton letter2 = new JButton("A");
        hangman.makeGuess(letter2, "A");
        hangman.undo();

        assertEquals("B ", hangman.getWrongLetters());
        assertEquals(1, hangman.getLCounter());
        assertEquals("files/head.png", hangman.getImage());
        assertTrue(hangman.getCorrectLetters().isEmpty());
        assertEquals("_ _ _ _ _ ", hangman.getGuessWordList());
        assertFalse(hangman.isRight());
    }

    @Test
    public void testOneUndoAfterRightThenWrong() {
        Hangman hangman = new Hangman();
        hangman.reset();
        hangman.setGuessWord("APPLE");
        JButton letter = new JButton("A");
        hangman.makeGuess(letter, "A");
        JButton letter2 = new JButton("B");
        hangman.makeGuess(letter2, "B");
        hangman.undo();

        assertTrue(hangman.getWrongLetters().isEmpty());
        assertEquals(0, hangman.getLCounter());
        assertEquals("files/nothing.png", hangman.getImage());
        assertEquals("A ", hangman.getCorrectLetters());
        assertEquals("A_ _ _ _ ", hangman.getGuessWordList());
        assertTrue(hangman.isRight());
    }

    @Test
    public void testUndoAtStart() {
        Hangman hangman = new Hangman();
        hangman.reset();
        hangman.setGuessWord("APPLE");
        JButton letter = new JButton("A");
        hangman.makeGuess(letter, "A");
        JButton letter2 = new JButton("B");
        hangman.makeGuess(letter2, "B");
        hangman.undo();
        hangman.undo();

        assertTrue(hangman.getWrongLetters().isEmpty());
        assertEquals(0, hangman.getLCounter());
        assertEquals("files/nothing.png", hangman.getImage());
        assertTrue(hangman.getCorrectLetters().isEmpty());
        assertEquals("_ _ _ _ _ ", hangman.getGuessWordList());
    }

    @Test
    public void testUndoAtEnd() {
        Hangman hangman = new Hangman();
        hangman.reset();
        hangman.setGuessWord("APPLE");
        JButton letter = new JButton("A");
        hangman.makeGuess(letter, "A");
        JButton letter2 = new JButton("P");
        hangman.makeGuess(letter2, "P");
        JButton letter3 = new JButton("L");
        hangman.makeGuess(letter3, "L");
        JButton letter4 = new JButton("E");
        hangman.makeGuess(letter4, "E");
        hangman.undo();
        hangman.undo();
        hangman.undo();
        hangman.undo();

        assertEquals("", hangman.undo());
        assertTrue(hangman.getWrongLetters().isEmpty());
        assertEquals(0, hangman.getLCounter());
        assertEquals("files/nothing.png", hangman.getImage());
        assertEquals("A P L E ", hangman.getCorrectLetters());
        assertEquals("APPLE", hangman.getGuessWordList());
    }

    @Test
    public void testUndoChain() {
        Hangman hangman = new Hangman();
        hangman.reset();
        hangman.setGuessWord("APPLE");
        JButton letter = new JButton("A");
        hangman.makeGuess(letter, "A");
        JButton letter2 = new JButton("B");
        hangman.makeGuess(letter2, "B");
        JButton letter3 = new JButton("P");
        hangman.makeGuess(letter3, "P");
        JButton letter4 = new JButton("L");
        hangman.makeGuess(letter4, "L");
        JButton letter5 = new JButton("C");
        hangman.makeGuess(letter5, "C");
        hangman.undo();
        hangman.undo();
        hangman.undo();

        assertEquals("B ", hangman.getWrongLetters());
        assertEquals(1, hangman.getLCounter());
        assertEquals("files/head.png", hangman.getImage());
        assertEquals("A ", hangman.getCorrectLetters());
        assertEquals("A_ _ _ _ ", hangman.getGuessWordList());
    }
}
