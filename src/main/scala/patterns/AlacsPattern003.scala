package com.github.alacs.patterns

import scala.tools.nsc.Global

import com.github.alacs.{Bug, BugInfo, BugPattern}

class AlacsPattern003(global: Global) extends PatternDetector(global) {
  import global._

  override val pattern = BugPattern(3, BugInfo("calculating mod 1"))
  
  override def analyzeTree(tree: GTree) = {
    val bug = Bug(pattern, tree.pos)
    tree match {
      case Apply(Select(_, nme.MOD), List(Literal(Constant(1)))) => report(bug)
      case _ => None
    }
  }
}
