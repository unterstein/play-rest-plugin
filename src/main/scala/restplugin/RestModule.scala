package restplugin


import akka.actor.{Actor, ActorLogging, Props}
import com.typesafe.config.ConfigFactory
import org.apache.commons.lang3.StringUtils
import play.api.Play
import play.api.routing.Router
import play.libs.Akka

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import play.api.Logger
import scala.collection.JavaConversions._

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
        val config = ConfigFactory.load
        val playClassLoader = Play.classloader(Play.current)
        val configMode = config.getString("restplugin.mode")

        documentation.foreach {
          case (method: String, path: String, action: String) =>
            val className = StringUtils.substringBeforeLast(action, ".")
            val methodName = StringUtils.substringBefore(StringUtils.remove(action, s"$className."), "(")


            Logger.info(s"Play-Rest-Plugin scans: class[$className], method[$methodName]")
            try {
              // oh yeah, play needs some extra sausages...
              val clazz = Class.forName(className, false, playClassLoader)
              if (clazz.isInstanceOf[RestActions] == false) {
                if (StringUtils.equals(configMode, "strict")) {
                  throw new IllegalArgumentException("Play-Rest-Plugin in mode=strict forbids to not use RESTActions")
                }
              }
              // play controller has the conversion, that method names must be unique
              val method = clazz.getMethods.filter(m => m.getName == methodName)(0)
              println(s"$method")
            } catch {
              case o_O: Exception =>
                Logger.error(s"Can not introspect $className#$methodName", o_O)
            }
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