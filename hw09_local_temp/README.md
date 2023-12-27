===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. File I/O: This feature implements the choosing of a random word and the saving of the game.
  It's important because I need it to store all possible random words that the app can read from before the
  start of each new game. Additionally, it's important because it allows me to save all important game information
  so that when the user reopens the game, the saved game file is read and each of the important game variables it stores
  are read and put back into their respective variables in the game so that the user can continue where they stopped.

  2. 2D Array: This feature implements the virtual keyboard where the user can click on a button associated with a letter
  to make a guess. It's important because keyboards are normally arranged in a 2D array format, and it makes it easy to
  iterate through the 2D array to find the correct letter button based on the function that is called.

  3. JUnit Tests: This feature implements the testing of my game logic. It's important because it ensure that my hangman
  game is properly following the rules of the game.

  4. Collections: This feature implements my undo feature. It's important because I need to be able to easily add to certain
  lists and access the element that was last added while deleting it at the same time so that I can fully reverse the action
  the user took.


=========================
=: My Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  GameBoard: This is the main portion of the GUI. It holds the keyboard, hangman images, and display of the
  word representation ("___ ____", "_H_ _O__", "THE WORD", etc.). It also serves as a link between the users interactions
  with the GUI and the backend logic of the game. So, whenever the user clicks any button, it calls the corresponding
  functions so that the effect(s) of the button click is reflected in the GUI.

  Hangman: This class stores the logic of the game. Based on the button the user clicks, this class updates and returns
  variables so that the game can be played as expected.

  RandomWordGenerator: This class reads the words.csv file and picks a random word whenever the hangman game is started or
  restarted.

  RunHangman: This class holds all the components of the GUI and displays them to the user.

  SaveGame: This class handles the saving of the game. It updates the saved_game.txt file with the current game state
  after each user interaction.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I had trouble with my undo feature because I had to find a way to know the last action the user took. I also had to
  make sure the undo feature didn't work at the beginning or end of the game. Another big issue I had was with the saving
  of the game. Nothing was being written to the saved_game.txt file at first. Lastly, I had trouble connecting all of the
  classes together.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  There is good separation of functionality, but I feel like there could definitely be more with more time. I think my
  private state is encapsulated pretty well, as shown through my JUnit tests. If given the chance, I would've added more
  comments and made my code look more readable and efficient with more helper functions.

