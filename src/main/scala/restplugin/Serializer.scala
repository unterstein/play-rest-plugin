package restplugin

import java.io.ByteArrayInputStream
import java.lang.reflect.Type
import javax.xml.bind.JAXBContext

import com.google.gson.{JsonElement, JsonSerializationContext, JsonSerializer, GsonBuilder}
import play.api.Logger
import play.api.mvc.{AnyContent, Request}
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */
object Serializer {
  private val gson = new GsonBuilder().registerTypeAdapter(classOf[List[Any]], new ListSerializer()).create()

  def toJson(obj: Object): String = gson.toJson(obj)

  def fromJson[T](json: String, requiredClass: Class[T]): T = gson.fromJson(json, requiredClass)

  def fromRequest[T](request: Request[AnyContent], requiredClass: Class[T]): Option[T] = {
    val body = request.body
    if (body.asJson.isDefined) {
      // json
      Some(gson.fromJson(body.asJson.get.toString(), requiredClass))
    } else if (body.asXml.isDefined) {
      try {
        // xml
        val xmlAsString = body.asXml.get.toString()
        val context = JAXBContext.newInstance(requiredClass)
        val unmarshaller = context.createUnmarshaller()
        // TODO maybe jaxb is not the best option...
        Some(unmarshaller.unmarshal(new ByteArrayInputStream(xmlAsString.getBytes)).asInstanceOf[T])
      } catch {
        case o_O: Exception =>
          Logger.warn(s"Unable to unmarshal given xml to entity of class $requiredClass", o_O)
          None
      }
    } else if (body.asFormUrlEncoded.isDefined) {
      // form url
      None // TODO
    } else if (body.asText.isDefined) {
      // text
      Some(gson.fromJson(body.asText.get, requiredClass))
    } else {
      None
    }
  }

  /**
   * Serializes a scala list to correct json
   */
  class ListSerializer extends JsonSerializer[List[Any]] {

    override def serialize(src: List[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      gson.toJsonTree(src.asJava)
    }
  }
}
