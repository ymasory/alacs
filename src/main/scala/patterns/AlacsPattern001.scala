package com.github.alacs.patterns

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.PluginComponent

import scala.collection.{mutable => m}

import com.github.alacs.{Bug, BugPatterns}

class AlacsPattern001(val global: Global) extends PluginComponent {
  import global._
  override val runsAfter = List[String]("parser");
  override val runsRightAfter = Some("parser");
  override val phaseName = "alacs component"
  override def newPhase(_prev: Phase) =
    new AlacsPattern001Phase(_prev)

  class AlacsPattern001Phase(prev: Phase) extends StdPhase(prev) {
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

    tree match {
      case tree@DefDef(_, _, _, _, _, _) => {

        val body = tree.children.last
        val selector = tree.children(tree.children.length - 2)
        val selectorMatches = selector match {
          case Select(q, n) => {
            if (n.toString == "Unit") true
            else false
          }
          case _ => false
        }
        val bug = Bug(BugPatterns.AlacsPattern001, tree.pos)
        if (selectorMatches) {
          body match {
            case Literal(Constant(())) => None //empty procedures
            case Literal(_)  => report(bug)
            case Block(_, lastExpr) => {
              lastExpr match {
                case Literal(_) => report(bug)
                case _ => None
              }
            }
            case _ => None
          }
        }
        else None
      }
      case _ => None
    }
  }
}
