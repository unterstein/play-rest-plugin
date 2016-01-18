package controllers

import restplugin.RestActions

class Application extends RestActions {

  def index = RESTAction() {
    implicit request => // : RESTRequest[NoEntityNeeded]
      Ok("ok")
  }

}
