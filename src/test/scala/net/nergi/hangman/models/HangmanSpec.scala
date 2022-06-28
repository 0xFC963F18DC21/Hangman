package net.nergi.hangman.models

import Hangman._

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.Assertion

class HangmanSpec extends AnyFlatSpec with Matchers {
  behavior of "the Hangman model"

  it should "be a winning game upon start with a blank word" in {
    Hangman("").gameStatus shouldBe Win
  }

  it should "not be a winning game upon start with a non-blank word" in {
    Hangman("word").gameStatus shouldBe InProgress
  }

  // Game state checker.
  def assertGameState(
    postGuess: (Hangman, GuessResult),
    expRes: GuessResult,
    expSt: GameStatus,
    expGs: Set[Char],
    expIs: Set[Char]
  ): Assertion = {
    val (game, res) = postGuess

    res shouldBe expRes

    game.gameStatus shouldBe expSt

    game.getGuesses() shouldBe expGs
    game.getIncorrect() shouldBe expIs
  }

  it should "identify correct guesses and display the new hidden word" in {
    assertGameState(
      Hangman("word").guess('o'),
      Correct,
      InProgress,
      Set('o'),
      Set.empty
    )
  }

  it should "identify incorrect guesses and update the incorrect set" in {
    assertGameState(
      Hangman("word").guess('f'),
      Incorrect,
      InProgress,
      Set('f'),
      Set('f')
    )
  }

  it should "signify a loss when the maximum number of incorrect guesses (10) is reached" in {
    assertGameState(
      "efghijklm".foldLeft(Hangman("word")) { case (hm, l) => hm.guess(l)._1 }.guess('n'),
      Incorrect,
      Loss,
      "efghijklmn".toSet,
      "efghijklmn".toSet
    )
  }
}
