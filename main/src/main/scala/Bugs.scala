package com.github.alacs

import java.text.DecimalFormat

import scala.tools.nsc.util.Position

case class BugPattern(bugId: Int, info: BugInfo) {

  override val toString = {
    val formatter = new DecimalFormat("000")
    "Alacs-" + formatter.format(bugId) + " " + info
  }
}

case class BugInfo(desc: String) {
  override val toString = desc
}

case class Bug(pat: BugPattern, pos: Position)

object BugPatterns {
  val BPUnintentionalProcedure =
    BugPattern(1, BugInfo("unintentional procedure"))
}

