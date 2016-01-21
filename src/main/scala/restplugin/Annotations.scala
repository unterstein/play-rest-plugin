package restplugin

import scala.annotation.StaticAnnotation

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
object Annotations {

  class RESTRequired[T](requiredClass: Option[Class[T]]) extends StaticAnnotation

  class RESTProduces[T](producesClass: Option[Class[T]]) extends StaticAnnotation

}
