package restplugin

import play.api.mvc._

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
trait RestActions extends Controller {

  // reference to RestModule
  private val module = RestModule

  def RESTAction[I, O](inputClass: Option[Class[I]] = Some(classOf[NoClassGiven]), inputObjectRequired: Boolean = false)(f: RESTRequest[AnyContent, _ >: I] => Result) = Action {
    implicit request =>
      val givenObject = if (inputClass.isDefined && inputClass.get != classOf[NoClassGiven]) {
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

  case class NoClassGiven()

}
