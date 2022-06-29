package net.nergi.hangman.controllers

import net.nergi.hangman.models.Hangman
import Hangman._
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HangmanControllerSpec extends AnyFlatSpec with Matchers with MockFactory {
  behavior of "the Hangman controller"

  it should "display the correct representation of hidden words" in {
    // Get our mocks...
    val noLetterHm   = stub[Hangman]
    val someLetterHm = stub[Hangman]
    val allLetterHm  = stub[Hangman]

    // Set all of them to have the word "word".
    val theWord = "word"
    (() => noLetterHm.getWord).when().returns(theWord)
    (() => someLetterHm.getWord).when().returns(theWord)
    (() => allLetterHm.getWord).when().returns(theWord)

    // Set their respective guessed states...
    (() => noLetterHm.getSoFar)
      .when()
      .returns(Map('w' -> false, 'o' -> false, 'r' -> false, 'd' -> false))

    (() => someLetterHm.getSoFar)
      .when()
      .returns(Map('w' -> true, 'o' -> false, 'r' -> true, 'd' -> false))

    (() => allLetterHm.getSoFar)
      .when()
      .returns(Map('w' -> true, 'o' -> true, 'r' -> true, 'd' -> true))

    // Check that the hidden words look right.
    HangmanController(noLetterHm).hiddenWord shouldBe "____"
    HangmanController(someLetterHm).hiddenWord shouldBe "w_r_"
    HangmanController(allLetterHm).hiddenWord shouldBe "word"
  }

  it should "display the correct user-friendly game status when guessing" in {
    // Get our mocks...
    val correctGuessHm   = stub[Hangman]
    val incorrectGuessHm = stub[Hangman]
    val guessedHm        = stub[Hangman]

    // Set them to return different things when guessing 'w'.
    (correctGuessHm.guess _).when('w').returns(correctGuessHm, Correct)
    (incorrectGuessHm.guess _).when('w').returns(incorrectGuessHm, Incorrect)
    (guessedHm.guess _).when('w').returns(guessedHm, AlreadyGuessed)

    // Test our guess code...
    HangmanController(correctGuessHm).makeGuess('w') shouldBe "W is in the word!"
    HangmanController(incorrectGuessHm).makeGuess('w') shouldBe "W is not in the word!"
    HangmanController(guessedHm).makeGuess('w') shouldBe "You have already guessed W!"

    // Verify that guess is called.
    (correctGuessHm.guess _).verify('w')
    (incorrectGuessHm.guess _).verify('w')
    (guessedHm.guess _).verify('w')
  }
}
