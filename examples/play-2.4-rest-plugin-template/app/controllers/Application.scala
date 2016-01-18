package controllers

import restplugin.RestActions

class Application extends RestActions {

  def index = RESTAction() {
    implicit request =>
      Ok("ok: " + request.givenObject)
  }

}
