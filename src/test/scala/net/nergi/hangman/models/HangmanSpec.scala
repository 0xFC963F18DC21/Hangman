package net.nergi.hangman.models

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HangmanSpec extends AnyFlatSpec with MockFactory with Matchers {
  behavior of "the Hangman model"

  it should "be a winning game upon start with a blank word" in {
    Hangman("").gameStatus shouldBe Hangman.Win
  }

  it should "not be a winning game upon start with a non-blank word" in {
    Hangman("word").gameStatus shouldBe Hangman.InProgress
  }

  it should "show all _s when starting a game" in {
    Hangman("word").hiddenWord shouldBe "____"
  }

  it should "identify correct guesses and display the new hidden word" in {
    val (game, res) = Hangman("word").guess('o')

    res shouldBe Hangman.Correct

    game.gameStatus shouldBe Hangman.InProgress
    game.hiddenWord shouldBe "_o__"

    game.guesses shouldBe Set('o')
    game.incorrect shouldBe Set.empty
  }

  it should "identify incorrect guesses and update the incorrect set" in {
    val (game, res) = Hangman("word").guess('f')

    res shouldBe Hangman.Incorrect

    game.gameStatus shouldBe Hangman.InProgress
    game.hiddenWord shouldBe "____"

    game.guesses shouldBe Set('f')
    game.incorrect shouldBe Set('f')
  }

  it should "signify a loss when the maximum number of incorrect guesses (10) is reached" in {
    val (game, res) =
      "efghijklm".foldLeft(Hangman("word")) { case (hm, l) => hm.guess(l)._1 }.guess('n')

    res shouldBe Hangman.Incorrect

    game.gameStatus shouldBe Hangman.Loss
    game.hiddenWord shouldBe "____"

    game.guesses shouldBe "efghijklmn".toSet
    game.incorrect shouldBe "efghijklmn".toSet
  }
}
