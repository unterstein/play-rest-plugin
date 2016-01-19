package restplugin

import scala.annotation.StaticAnnotation

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
object Annotations {

  class RESTRequired(requiredClass: Option[Class]) extends StaticAnnotation

  class RESTProduces(producesClass: Option[Class]) extends StaticAnnotation

}
