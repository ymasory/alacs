import scala.tools.nsc.reporters.StoreReporter

package com.github {
  package object alacs {
    type PluginMessage = StoreReporter#Info

    implicit def messages2Bugs(messages: List[PluginMessage]): List[Bug] = {
      Nil
    }
  }
}
