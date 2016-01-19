package controllers

import javax.inject.Inject

import restplugin.{RestPlugin, RestActions}

class Application @Inject()(plugin: RestPlugin) extends RestActions {

  def index = RESTAction() {
    implicit request =>
      Ok(s"ok: ${request.givenObject}")
  }

  def examplePut(id: Long) = RESTAction() {
    implicit request =>
      Ok(s"ok: $id ${request.givenObject}")
  }

}
