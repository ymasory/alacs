package com.github.alacs.patterns

import scala.tools.nsc.Global

import com.github.alacs.{Bug, BugPatterns}

class AlacsPattern001(global: Global) extends BugPattern(global) {
  import global._

  override def analyzeTree(tree: Global#Tree) = {
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
