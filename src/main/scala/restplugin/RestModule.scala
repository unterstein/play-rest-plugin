package restplugin

import javax.inject.{Inject, Singleton}

import akka.actor.{Actor, ActorLogging, Props}
import org.apache.commons.lang3.StringUtils
import play.api.inject.ApplicationLifecycle
import play.api.routing.Router
import play.libs.Akka

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

//import scala.concurrent.Future


/**
 * @author Johannes Unterstein (unterstein@me.com)
 */

@Singleton
class RestPlugin @Inject()(lifecycle: ApplicationLifecycle, router: Router) {
  // TODO maybe register stop hook
  //  lifecycle.addStopHook(() => Future.successful())


  // in order to avoid following exception:
  // -> Caused by: java.lang.IllegalStateException: This is a proxy used to support circular references. The object we're proxying is not constructed yet. Please wait until after injection has completed to use this object.
  // we do this a little bit lazy here

  RestPluginInitializer.tryInit(router)

}

object RestPluginInitializer {

  def tryInit(router: Router) = Akka.system.scheduler.scheduleOnce(25.milliseconds, Akka.system.actorOf(props(router)), "init")

  def props(router: Router) = Props(classOf[RestPluginInitializer], router)
}

class RestPluginInitializer(private val router: Router) extends Actor with ActorLogging {

  override def receive: Receive = {
    case "init" =>
      try {
        val documentation = router.documentation
        // TODO do introspection
      } catch {
        case o_O: IllegalStateException =>
          if (StringUtils.contains(o_O.getMessage, "Please wait until")) {
            RestPluginInitializer.tryInit(router)
          } else {
            throw o_O
          }
        case o_O: _ =>
          throw o_O
      }

    case msg =>
      unhandled(msg)
  }
}