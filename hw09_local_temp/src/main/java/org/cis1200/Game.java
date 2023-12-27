package org.cis1200;

import org.cis1200.hangman.RunHangman;


import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        // Set the game you want to run here
        Runnable game = new RunHangman();

        SwingUtilities.invokeLater(game);
    }
}
