package net.nergi.hangman.models

case class Hangman(
  word: String,
  soFar: Map[Char, Boolean],
  guesses: Set[Char],
  incorrect: Set[Char]
) {
  import Hangman._

  // Get the current status of the game.
  def gameStatus: GameStatus =
    if (incorrect.size >= MaxIncorrect) Loss
    else if (soFar.values.forall(identity[Boolean])) Win
    else InProgress

  // Guess a letter.
  def guess(letter: Char): (Hangman, GuessResult) =
    if (guesses.contains(letter))
      (this, AlreadyGuessed)
    else if (!soFar.contains(letter))
      (Hangman(word, soFar, guesses + letter, incorrect + letter), Incorrect)
    else
      (Hangman(word, soFar.updated(letter, true), guesses + letter, incorrect), Correct)

  // Get the word displayed in play (with hidden letters, etc.).
  @Deprecated("Use HangmanController.hiddenWord instead!")
  lazy val hiddenWord: String = word.map { c => if (soFar(c)) c else '_' }

  // The number of incorrect guesses that constitute a loss.
  val MaxIncorrect: Int = 10
}

object Hangman {
  def apply(word: String): Hangman =
    Hangman(word, word.map(c => (c, false)).toMap, Set.empty, Set.empty)

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
