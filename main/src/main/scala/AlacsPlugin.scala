package com.github.alacs

import scala.tools.nsc
import nsc.Global
import nsc.plugins.Plugin

class AlacsPlugin(val global: Global) extends Plugin {
  import global._

  override val name = "alacs"
  override val description = "finds bugs, hopefully"
  override val components =
    List(new BPUnintentionalProcedure(global))
}
