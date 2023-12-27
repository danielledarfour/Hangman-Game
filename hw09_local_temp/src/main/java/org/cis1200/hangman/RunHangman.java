package org.cis1200.hangman;



import javax.swing.*;
import java.awt.*;


public class RunHangman implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Hangman");
        frame.setLocation(400, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // control panel
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);



        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton loadNew = new JButton("Load New");
        loadNew.addActionListener(e -> board.reset());
        control_panel.add(loadNew);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> {
            String undoButton = board.undo();
            if (!undoButton.isEmpty()) {
                for (JButton[] letterButtonRow : board.getJButtonArray()) {
                    for (JButton button : letterButtonRow) {
                        if (!(button == null)) {
                            if (button.getText().equals(undoButton)) {
                                button.setEnabled(true);
                                button.setOpaque(true);
                                button.setContentAreaFilled(true);
                                break;
                            }
                        }
                    }
                }
            }
        });
        control_panel.add(undo);




        // Put the frame on the screen
        showInstructionsDialog(frame);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    private void showInstructionsDialog(JFrame frame) {
        // Create and display the instructions dialog using JOptionPane
        String instructions = "Welcome to Hangman!\n" +
                "This is a game where you guess letters to reveal a secret word.\n\n" +
                "Instructions:\n" +
                "1. Guess letters by clicking on the keyboard buttons.\n" +
                "2. You have 7 chances to guess the word.\n" +
                "3. There is an undo button in case you can use to your advantage.\n" +
                "   Or choose to restart.\n" +
                "4. Good luck and have fun!\n" +
                "**Hint: All of the words are the names of famous people!**";

        JOptionPane.showMessageDialog(frame, instructions,
                "Hangman Instructions", JOptionPane.INFORMATION_MESSAGE);
    }
}