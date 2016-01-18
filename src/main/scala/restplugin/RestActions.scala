package restplugin

import play.api.mvc._

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
trait RestActions extends Controller {

  def RESTAction[T](requiredClass: Option[Class[T]] = Some(classOf[NoEntityNeeded]), objectRequired: Boolean = false)(f: RESTRequest[AnyContent, _ >: T] => Result) = Action {
    implicit request =>
      val givenObject = if (requiredClass.isDefined && requiredClass.get != classOf[NoEntityNeeded]) {
        Serializer.fromRequest(request, requiredClass.get)
      } else {
        None
      }
      if (objectRequired && givenObject.isEmpty) {
        BadRequest("Entity missing")
      } else {
        f(RESTRequest(givenObject, request))
      }
  }

  case class RESTRequest[A, C](givenObject: Option[C], request: Request[A]) extends WrappedRequest[A](request)

  case class NoEntityNeeded()

}
