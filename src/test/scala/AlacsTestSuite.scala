package com.github.alacs

import org.scalatest.FunSuite

class AlacsFunSuite extends FunSuite {

  def genRun(dirName: String): String => List[PluginMessage] = {
    (fileName: String) => RunPlugin.runPlugin(dirName + fileName + ".scala")
  }

  def commonRun(fileName: String): List[PluginMessage] =  genRun("common/")(fileName)
}
