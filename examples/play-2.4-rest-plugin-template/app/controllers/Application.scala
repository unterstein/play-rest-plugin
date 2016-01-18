package controllers

import play.api.mvc._
import restplugin.RestActions

class Application extends RestActions {

  def index = Action {
    implicit request =>
      Ok("ok")
  }

}
