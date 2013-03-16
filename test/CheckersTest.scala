package test

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers
import models.Checkers._

class CheckersSpec extends FlatSpec with ShouldMatchers {
  "Checkers" should "let move a piece forward to an empty space" in {
  }

  ignore should "let a piece move backwards only if King" in {
  }

  ignore should "turn a piece into a king upon reaching opposite end" in {
  }

  ignore should "not do anything when a king reaches the end" in {
  }

  ignore should "not let a piece move beyond the board boundaries" in {
  }

  ignore should "not let a piece jump more than one opponent" in {
  }

  ignore should "let a piece jump another piece" in {
  }

  ignore should "change the player when no piece is captured" in {
  }

  ignore should "remain on the same turn if a piece jumps another piece" in {
  }

  ignore should "force a player to take a capture" in {
  }

  ignore should "end the game in a win if opponent has no more pieces" in {
  }

  ignore should "end the game in a draw when no valid moves are available" in {
  }
}