package com.github.alacs

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.PluginComponent

import scala.collection.{mutable => m}

class BPUnintentionalProcedure(val global: Global) extends PluginComponent {
  import global._
  override val runsAfter = List[String]("parser");
  override val runsRightAfter = Some("parser");
  override val phaseName = "alacs component"
  override def newPhase(_prev: Phase) =
    new BPUnintentionalComponentPhase(_prev)

  class BPUnintentionalComponentPhase(prev: Phase) extends StdPhase(prev) {
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

        val headMatches = tree.children.head match {
          case Select(q, n) => {
            if (n.toString == "Unit") true
            else false
          }
          case _ => false
        }
        val bug = Bug(BugPatterns.BPUnintentionalProcedure, tree.pos)
        if (headMatches) {
          tree.children.last match {
            case Literal(Constant(())) => None
            case Literal(l)  => report(bug)
            case Block(lst, _) => {
              lst match {
                case Nil => None
                case _ :: _ => {
                  lst.last match {
                    case Literal(_) => report(bug)
                    case _ => None
                  }
                }
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
