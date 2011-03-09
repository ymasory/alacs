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
        // global.currentRun.cancel
      }
    }
  }

  def analyzeTree(tree: Tree, report: BugReport): BugReport = {
    def info(pos: Position, msg: String) = global.reporter.info(pos, msg, true)
    tree match {
      case tree@DefDef(_, _, _, _, _, _) => {

        val headMatches = tree.children.head match {
          case Select(q, n) => {
            if (n.toString == "Unit") true
            else false
          }
          case _ => false
        }

        if (headMatches) {
          tree.children.last match {
            case Literal(_) => {
              info(tree.pos, "unintentional procedure")
              report.copy(bugs = Bug(1) :: report.bugs)
            }
            case _ => report
          }
        } else report
      }

      case _ => report
    }
  }
}
