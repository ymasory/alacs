package com.github.alacscala.alacs

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.Plugin
import nsc.plugins.PluginComponent

class AlacsPlugin(val global: Global) extends Plugin {
  import global._

  override val name = "alacs"
  override val description = "finds bugs, hopefully"
  override val components = List(AlacsComponent)
  
  object AlacsComponent extends PluginComponent {
    override val global: AlacsPlugin.this.global.type = AlacsPlugin.this.global
    override val runsAfter = List[String]("parser");
    override val runsRightAfter = Some("parser");
    override val phaseName = "alacs component"
    override def newPhase(_prev: Phase) = new AlacsPhase(_prev)
    
    class AlacsPhase(prev: Phase) extends StdPhase(prev) {
      override def name = "alacs phase"
      override def apply(unit: CompilationUnit) {

        var curReport = BugReport.EmptyReport
        for (tree <- unit.body) {
          curReport = analyzeTree(tree, curReport)
        }
        println("\n" + curReport.report)
      }
    }
  }

  def analyzeTree(tree: Tree, report: BugReport): BugReport = {
    def info(msg: String) = global.reporter.info(tree.pos, msg, true)
    
    tree match {
      
      case _ => report
    }
  }
}
