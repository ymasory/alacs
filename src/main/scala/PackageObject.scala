import scala.tools.nsc.reporters.StoreReporter
import scala.tools.nsc.util.Position

package com.github {
  package object alacs {
    type PluginMessage = StoreReporter#Info

    implicit def messages2Bugs(messages: List[PluginMessage]): List[Bug] = {   
      messages map {parseBug(_)}
    }

    def parseBug(msg: PluginMessage): Bug = {
      null
    }
  }
}
