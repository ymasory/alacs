package com.github.alacs

import scala.tools.nsc.util.Position

case class BugPattern(bugId: Int, info: BugInfo) {
  override val toString = "Alacs-" + bugId
}
case class BugInfo(desc: String)

case class Bug(pat: BugPattern, pos: Position)

object BugPatterns {
  val BPUnintentionalProcedure = BugPattern(0, BugInfo("unintentional procedure"))
}

