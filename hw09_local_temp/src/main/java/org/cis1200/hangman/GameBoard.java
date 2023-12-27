package org.cis1200.hangman;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private Hangman currentGame; // model for the game
    private JLabel imageLabel;
    private JLabel hangmanWordLabel;
    private JLabel status;
    private JButton[][] letterButtons;

    // Game constant
    private final String initialimage = "files/nothing.png";

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        this.currentGame = new Hangman();// initializes model for the game
        this.status = status;

        JPanel imagePanel = new JPanel();
        ImageIcon initialImage = new ImageIcon(initialimage);
        imageLabel = new JLabel(initialImage);
        add(imagePanel);

        JPanel hangmanWordPanel = new JPanel();
        hangmanWordLabel = new JLabel(currentGame.getGuessWordList());
        hangmanWordPanel.add(hangmanWordLabel);
        add(hangmanWordPanel);


        JPanel lettersPanel = new JPanel(new GridLayout(3, 9));
        letterButtons = new JButton[3][9];
        for (char c = 'A'; c <= 'Z'; c++) {
            int row = (c - 'A') / 9;
            int col = (c - 'A') % 9;


            letterButtons[row][col] = new JButton(String.valueOf(c));
            letterButtons[row][col].addActionListener(e -> {
                currentGame.makeGuess(letterButtons[row][col], letterButtons[row][col].getText());
                updateImage();
                updateStatus();
                updateHangmanWord();
            });
            lettersPanel.add(letterButtons[row][col]);
        }
        add(lettersPanel);

        String allClickedLettersString = currentGame.getUserGuesses();
        String[] allClickedLetters = allClickedLettersString.split(" ");
        for (JButton[] letterButton : letterButtons) {
            for (int j = 0; j < letterButtons[0].length; j++) {
                for (String letter : allClickedLetters) {
                    if (!(letterButton[j] == null)) {
                        if (letterButton[j].getText().equals(letter)) {
                            currentGame.disableLetter(letterButton[j]);
                        }
                    }
                }
            }
        }
        updateImage();
        updateStatus();
        updateHangmanWord();
    }


    /**
     * (Re-)sets the game to its initial state.
     */
    public String undo() {
        String letter = currentGame.undo();
        updateImage();
        updateStatus();
        updateHangmanWord();
        return letter;
    }

    public void reset() {
        currentGame.reset();
        for (JButton[] letterButtonRow : letterButtons) {
            for (JButton button : letterButtonRow) {
                if (button != null) {
                    currentGame.enableLetter(button);
                }
            }
        }

        updateImage();
        updateStatus();
        updateHangmanWord();



        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    public void updateStatus() {
        if (currentGame.isWinner()) {
            status.setText("You Win!");
            endGame();
        } else if (currentGame.isLoser()) {
            status.setText("You lose!");
            currentGame.revealSecretWord();
            updateHangmanWord();
            endGame();
        } else {
            status.setText("Playing...");
        }
    }

    public void updateImage() {
        ImageIcon currentImage = new ImageIcon(currentGame.getImage());
        status.setIcon(currentImage);
    }

    public void updateHangmanWord() {
        hangmanWordLabel.setText(currentGame.getGuessWordList());
    }

    public JButton[][] getJButtonArray() {
        return letterButtons;
    }

    private void endGame() {
        for (JButton[] letterButtonRow : letterButtons) {
            for (JButton button : letterButtonRow) {
                if (button != null) {
                    currentGame.disableLetter(button);
                }
            }
        }
    }
}


