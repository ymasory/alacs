package com.github.alacs.patterns

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.PluginComponent

import scala.collection.{mutable => m}

import com.github.alacs.{Bug, BugPatterns}

class AlacsPattern002(val global: Global) extends PluginComponent {
  import global._
  override val runsAfter = List[String]("parser");
  // override val runsRightAfter = Some("parser");
  override val phaseName = "alacs component"
  override def newPhase(_prev: Phase) =
    new AlacsPattern002Phase(_prev)

  class AlacsPattern002Phase(prev: Phase) extends StdPhase(prev) {
    override def name = "alacs phase"
    override def apply(unit: CompilationUnit) {

      val bugs = new m.ListBuffer[Bug]
      for (tree <- unit.body) {
        analyzeTree(tree) match {
          case Some(bug) => bugs += bug
          case None =>
        }
      }
    }
  }

  def analyzeTree(tree: Tree): Option[Bug] = {
    def report(bug: Bug): Option[Bug] = {
      global.reporter.info(bug.pos, bug.pat.toString, false)
      Some(bug)
    }
    None
  }
}
