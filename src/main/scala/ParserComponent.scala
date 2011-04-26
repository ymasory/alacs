package com.github.alacs.patterns

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.PluginComponent

import com.github.alacs.Bug

class ParserComponent(val global: Global) extends PluginComponent {
  import global._
  override val runsAfter = List[String]("parser");
  override val runsRightAfter = Some("parser");
  override val phaseName = "alacs parser phase"
  override def newPhase(_prev: Phase) =
    new ParserPhase(_prev)

  class ParserPhase(prev: Phase) extends StdPhase(prev) {
    override def name = "alacs praser phase"
    override def apply(unit: CompilationUnit) {
      val pat1 = new AlacsPattern001(global)
      val pat2 = new AlacsPattern002(global)
      for (tree <- unit.body) {
        try {
          pat1 analyzeTree tree
          pat2 analyzeTree tree
        }
        catch {
          case _ =>
        }
      }
    }
  }
}

