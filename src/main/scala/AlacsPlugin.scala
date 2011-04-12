package com.github.alacs

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.Plugin
import nsc.plugins.PluginComponent

import scala.collection.{mutable => m}

class AlacsPlugin(val global: Global) extends Plugin {
  import global._

  override val name = "alacs"
  override val description = "finds bugs, hopefully"
  override val components =
    List(new BPUnintentionalProcedure(global))
}
