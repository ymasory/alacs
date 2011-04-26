package com.github.alacs.patterns

import scala.tools.nsc
import nsc.Global

import com.github.alacs.{Bug, BugPattern}

abstract class PatternDetector(global: Global) {
  import global._

  val pattern: BugPattern

  def report(bug: Bug): Option[Bug] = {
    global.reporter.info(bug.pos, bug.pat.toString, false)
    Some(bug)
  }

  def analyzeTree(tree: Global#Tree): Option[Bug]
}
