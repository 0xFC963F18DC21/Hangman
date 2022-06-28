package net.nergi.hangman.models

import Hangman._

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.Assertion

class HangmanSpec extends AnyFlatSpec with MockFactory with Matchers {
  behavior of "the Hangman model"

  it should "be a winning game upon start with a blank word" in {
    Hangman("").gameStatus shouldBe Win
  }

  it should "not be a winning game upon start with a non-blank word" in {
    Hangman("word").gameStatus shouldBe InProgress
  }

  it should "show all _s when starting a game" in {
    Hangman("word").hiddenWord shouldBe "____"
  }

  // Game state checker.
  def assertGameState(
    postGuess: (Hangman, GuessResult),
    expRes: GuessResult,
    expSt: GameStatus,
    expHw: String,
    expGs: Set[Char],
    expIs: Set[Char]
  ): Assertion = {
    val (game, res) = postGuess

    res shouldBe expRes

    game.gameStatus shouldBe expSt
    game.hiddenWord shouldBe expHw

    game.guesses shouldBe expGs
    game.incorrect shouldBe expIs
  }

  it should "identify correct guesses and display the new hidden word" in {
    assertGameState(
      Hangman("word").guess('o'),
      Correct,
      InProgress,
      "_o__",
      Set('o'),
      Set.empty
    )
  }

  it should "identify incorrect guesses and update the incorrect set" in {
    assertGameState(
      Hangman("word").guess('f'),
      Incorrect,
      InProgress,
      "____",
      Set('f'),
      Set('f')
    )
  }

  it should "signify a loss when the maximum number of incorrect guesses (10) is reached" in {
    assertGameState(
      "efghijklm".foldLeft(Hangman("word")) { case (hm, l) => hm.guess(l)._1 }.guess('n'),
      Incorrect,
      Loss,
      "____",
      "efghijklmn".toSet,
      "efghijklmn".toSet
    )
  }
}
