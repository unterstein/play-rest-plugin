package restplugin

import javax.inject.{Inject, Singleton}

import akka.actor.{Actor, ActorLogging, Props}
import org.apache.commons.lang3.StringUtils
import play.api.Play
import play.api.inject.ApplicationLifecycle
import play.api.routing.Router
import play.libs.Akka

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

//import scala.concurrent.Future


/**
 * @author Johannes Unterstein (unterstein@me.com)
 */

object RestModule {

  tryInit(Play.current.routes)


  private def tryInit(router: Router) = Akka.system.scheduler.scheduleOnce(25.milliseconds, Akka.system.actorOf(props(router)), "init")

  private def props(router: Router) = Props(classOf[RestModule], router)
}

class RestModule(private val router: Router) extends Actor with ActorLogging {

  override def receive: Receive = {
    case "init" =>
      try {
        val documentation = router.documentation
        documentation.foreach {
          case (method: String, path: String, action: String) =>
            val className = StringUtils.substringBeforeLast(action, ".")
            val methodName = StringUtils.substringBefore(StringUtils.remove(action, s"$className."), "(")
            println(s"class: $className, method: $methodName")
        }
      } catch {
        case o_O: IllegalStateException =>
          if (StringUtils.contains(o_O.getMessage, "Please wait until")) {
            RestModule.tryInit(router)
          } else {
            throw o_O
          }
        case o_O: Exception =>
          throw o_O
      }

    case msg =>
      unhandled(msg)
  }
}