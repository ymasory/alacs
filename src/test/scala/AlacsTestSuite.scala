package com.github.alacs

import org.scalatest.FunSuite

class AlacsFunSuite extends FunSuite {

  def genRun(dirName: String): String => List[Bug] = {
    (fileName: String) => RunPlugin.runPlugin(dirName + fileName + ".scala")
  }

  def commonRun(fileName: String): List[Bug] =  genRun("common/")(fileName)
}
