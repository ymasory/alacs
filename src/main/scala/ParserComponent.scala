package com.github.alacs

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.PluginComponent

import com.github.alacs.patterns.PatternDetector

class ParserComponent(val global: Global) extends PluginComponent {
  import global._
  override val runsAfter = List[String]("parser");
  override val runsRightAfter = Some("parser");
  override val phaseName = "alacs parser phase"
  override def newPhase(_prev: Phase) =
    new ParserPhase(_prev)

  class ParserPhase(prev: Phase) extends StdPhase(prev) {
    import java.lang.reflect.Constructor

    override def name = "alacs parser phase"
    override def apply(unit: CompilationUnit) {

      val patterns: List[Constructor[PatternDetector]] =
        Reflector.discoverPatterns()
      for (tree <- unit.body) {
        try {
          val bugs: List[Bug] = patterns flatMap { con =>
            val pat: PatternDetector = con.newInstance(global)
            pat analyzeTree tree 
          }
        }
        catch {
          case e => e.printStackTrace
        }
      }
    }
  }
}

