package controllers

import restplugin.RestActions

class Application extends RestActions {

  def index = RESTAction() {
    implicit request =>
      Ok(s"ok: ${request.givenObject}")
  }

  def examplePut(id: Long) = RESTAction() {
    implicit request =>
      Ok(s"ok: $id ${request.givenObject}")
  }

}
