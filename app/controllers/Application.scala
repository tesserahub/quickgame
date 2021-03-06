package controllers

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc.{Controller, WebSocket, Action}
import play.api.libs.json.JsValue

import models.GameManager
import common._

object Application extends Controller {
  /*
   * List of the avalailable games.
   * Add new games to this list in order to make them available to QuickGame.
   */
  private[this] val _games: Set[GameAdapter] = Set(
    games.tictactoe.TicTacToe,
    games.connectfour.ConnectFour
  )

  /*
   * Convenience mappings from the game type to the model or view.
   */
  val gameViews: Map[GameType, GameView] = _games.map(ctrl => ctrl -> ctrl.view).toMap
  val gameModels: Map[GameType, GameModel] = _games.map(ctrl => ctrl -> ctrl.model).toMap
  val gameTypes: Set[GameType] = _games.map(ctrl => ctrl)

  /** The manager actor. */
  val gameManager = GameManager(gameModels)
  
  def index = Action {
    Ok(views.html.index())
  }

  def gameIndex(g: GameType) = Action {
    Ok(views.html.gameIndex(g))
  }

  def newGame(g: GameType) = Action {
    Async {
      gameManager.create(g).map { id =>
        Redirect(routes.Application.game(g, id))
      }
    }
  }

  def game(g: GameType, id: String) = Action { implicit request =>
    Async {
      gameManager.contains(g, id) map { gameFound =>
        if (gameFound) {
          Ok(views.html.game(g, id)(gameViews(g)))
        } else {
          NotFound(s"Could not find $g game #$id")
        }
      }
    }
  }

  def socket(g: GameType, id: String, username: Option[String]) = WebSocket.async[JsValue] { request =>
    gameManager.join(g, id, username)
  }

  def setCookie(name: String) = Action {
    Ok("Set the username").withSession(
      "name" -> name
    )
  }

}
