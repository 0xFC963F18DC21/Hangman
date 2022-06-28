package net.nergi.hangman.controllers

import net.nergi.hangman.models.Hangman
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
    (noLetterHm.getWord _).when().returns(theWord)
    (someLetterHm.getWord _).when().returns(theWord)
    (allLetterHm.getWord _).when().returns(theWord)

    // Set their respective guessed states...
    (noLetterHm.getSoFar _)
      .when()
      .returns(Map('w' -> false, 'o' -> false, 'r' -> false, 'd' -> false))

    (someLetterHm.getSoFar _)
      .when()
      .returns(Map('w' -> true, 'o' -> false, 'r' -> true, 'd' -> false))

    (allLetterHm.getSoFar _)
      .when()
      .returns(Map('w' -> true, 'o' -> true, 'r' -> true, 'd' -> true))

    // Check that the hidden words look right.
    HangmanController(noLetterHm).hiddenWord shouldBe "____"
    HangmanController(someLetterHm).hiddenWord shouldBe "w_r_"
    HangmanController(allLetterHm).hiddenWord shouldBe "word"
  }
}
