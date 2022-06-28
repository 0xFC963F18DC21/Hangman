package net.nergi.hangman.models

class Hangman(
  word: String,
  soFar: Map[Char, Boolean],
  guesses: Set[Char],
  incorrectGuesses: Set[Char]
) {
  // Get the current status of the game.
  def gameStatus: GameStatus =
    if (incorrectGuesses.size >= 10) Loss
    else if (soFar.values.reduce(_ && _)) Win
    else InProgress

  // Guess a letter.
  def guess(letter: Char): (Hangman, GuessResult) =
    if (guesses.contains(letter))
      (this, AlreadyGuessed)
    else if (!soFar.contains(letter))
      (new Hangman(soFar, guesses + letter, incorrectGuesses + letter), Incorrect)
    else
      (new Hangman(soFar.updated(letter, true), guesses + letter, incorrectGuesses), Correct)

  // Game status enumeration:
  sealed trait GameStatus
  case object Win extends GameStatus
  case object Loss extends GameStatus
  case object InProgress extends GameStatus

  // Guess result enumeration:
  sealed trait GuessResult
  case object Correct extends GuessResult
  case object Incorrect extends GuessResult
  case object AlreadyGuessed extends GuessResult
}

object Hangman {
  def apply(word: String): Hangman =
    new Hangman(word, word.map(c => (c, false)).toMap, Set.empty, Set.empty)
}
