package restplugin

/**
 * @author Johannes Unterstein (unterstein@me.com)
 */

import play.api.{Plugin, Application}

class RestPlugin(application: Application) extends Plugin {

  override def onStart() = {
  }

  override def onStop() = {
  }

  override def enabled = true
}

