package restplugin

import play.api.mvc._

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
trait RestActions extends Controller {

  def RESTfulAction[T](requiredClass: Class[T], objectRequired: Boolean = false)(f: RESTRequest[AnyContent, _ >: T] => Result) = Action {
    implicit request =>
      val givenObject = Serializer.fromRequest(request, requiredClass)
      if (objectRequired && givenObject.isEmpty) {
        BadRequest("Entity missing")
      } else {
        f(RESTRequest(givenObject, request))
      }
  }

  case class RESTRequest[A, C](givenObject: Option[C], request: Request[A]) extends WrappedRequest[A](request)

}
