package restplugin

import play.api.mvc._

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
trait RestActions extends Controller {

  def RESTAction[T](inputClass: Option[Class[T]] = Some(classOf[NoEntityNeeded]), inputObjectRequired: Boolean = false)(f: RESTRequest[AnyContent, _ >: T] => Result) = Action {
    implicit request =>
      val givenObject = if (inputClass.isDefined && inputClass.get != classOf[NoEntityNeeded]) {
        Serializer.fromRequest(request, inputClass.get)
      } else {
        None
      }
      if (inputObjectRequired && givenObject.isEmpty) {
        BadRequest("Entity missing")
      } else {
        f(RESTRequest(givenObject, request))
      }
  }

  case class RESTRequest[A, C](givenObject: Option[C], request: Request[A]) extends WrappedRequest[A](request)

  case class NoEntityNeeded()

}
