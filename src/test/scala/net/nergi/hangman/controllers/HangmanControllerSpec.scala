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
}
