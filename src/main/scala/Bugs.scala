package com.github.alacs

import java.text.DecimalFormat

import scala.tools.nsc.util.Position

case class BugPattern(bugId: Int, info: BugInfo) {

  override val toString = {
    "Alacs-" + BugPattern.bugNumFormatter.format(bugId) + " " + info
  }
}
case object BugPattern {
  val bugNumFormatter = new DecimalFormat("000")
}

case class BugInfo(desc: String) {
  override val toString = desc
}

case class Bug(pat: BugPattern, pos: Position)
