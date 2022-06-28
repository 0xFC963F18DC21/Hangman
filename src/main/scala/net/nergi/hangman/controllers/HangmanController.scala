package net.nergi.hangman.controllers

import net.nergi.hangman.models.Hangman
import Hangman._

class HangmanController {
  private var game: Hangman = Hangman("")

  def startGame(word: String): Unit =
    game = Hangman(word.toLowerCase)

  // Get the game representation of the string.
  def hiddenWord: String = game.word.map {
    case c: Char if game.soFar(c) => c
    case _                        => '_'
  }

  // Make a guess and get a string representation of the guess result and current message.
  def makeGuess(letter: Char): String = {
    val (newGame, res) = game.guess(letter.toLower)

    // Update the game.
    game = newGame

    // Send back the user-friendly state.
    res match {
      case Correct        => s"${letter.toUpper} is in the word!"
      case Incorrect      => s"${letter.toUpper} is not in the word!"
      case AlreadyGuessed => s"You have already guessed ${letter.toUpper}!"
    }
  }

  // Tells if the game should continue, and the message associated with it.
  def shouldContinue: (Boolean, String) =
    game.gameStatus match {
      case Win        => (false, "You've guessed the word!")
      case Loss       => (false, "You didn't guess the word!")
      case InProgress => (true, "...")
    }
}
