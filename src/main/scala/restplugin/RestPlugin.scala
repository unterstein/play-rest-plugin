package restplugin

import javax.inject.{Singleton, Inject}

import play.api.inject.ApplicationLifecycle
import play.api.routing.Router

//import scala.concurrent.Future


/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
@Singleton
class RestPlugin @Inject()(lifecycle: ApplicationLifecycle, router: Router) {

  println(router.routes)

  // TODO maybe register stop hook
  //  lifecycle.addStopHook(() => Future.successful())
}

