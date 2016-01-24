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

  def examplePost(id: Long) = RESTAction(Some(classOf[ExampleUser]), inputObjectRequired = true) {
    implicit request =>
      Ok(s"ok: $id ${request.givenObject}")
  }

  case class ExampleUser(id: Long, name: String)

}
