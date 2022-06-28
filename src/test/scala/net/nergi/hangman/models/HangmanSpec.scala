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
}
