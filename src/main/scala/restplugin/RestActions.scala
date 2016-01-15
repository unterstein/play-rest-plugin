package restplugin

import play.api.mvc._

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
trait RestActions extends Controller {


  def RESTfulAction()(f: Request[AnyContent] => Result) = Action {
    implicit request =>
      f(request)
  }
}
