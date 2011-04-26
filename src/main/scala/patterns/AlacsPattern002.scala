package com.github.alacs.patterns

import scala.tools.nsc.Global

import com.github.alacs.{Bug, BugInfo, BugPattern}

class AlacsPattern002(global: Global) extends PatternDetector(global) {
  import global._

  override val pattern = BugPattern(2, BugInfo("divide by literal 0"))
  
  override def analyzeTree(tree: Global#Tree) = {
    val bug = Bug(pattern, tree.pos)
    None
  }
}
