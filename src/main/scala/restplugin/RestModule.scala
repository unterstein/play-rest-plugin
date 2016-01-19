package restplugin

import javax.inject.{Inject, Singleton}

import akka.actor.{Props, ActorLogging, Actor}
import play.api.inject.ApplicationLifecycle
import play.api.routing.Router
import play.libs.Akka
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

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

  Akka.system.scheduler.scheduleOnce(10.seconds, Akka.system.actorOf(RestPluginInitializer.props(router)), "init")


}

object RestPluginInitializer {
  def props(router: Router) = Props(classOf[RestPluginInitializer], router)
}

class RestPluginInitializer(private val router: Router) extends Actor with ActorLogging {

  override def receive: Receive = {
    case "init" =>
      println(router.documentation)
    case msg =>
      unhandled(msg)
  }
}