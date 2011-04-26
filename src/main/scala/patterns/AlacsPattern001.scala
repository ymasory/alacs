package com.github.alacs.patterns

import scala.tools.nsc.Global

import com.github.alacs.{Bug, BugInfo, BugPattern}

class AlacsPattern001(global: Global) extends PatternDetector(global) {
  import global._

  override val pattern = BugPattern(1, BugInfo("unintentional procedure"))

  override def analyzeTree(tree: Global#Tree) = {
    val bug = Bug(pattern, tree.pos)
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
