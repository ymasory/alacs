package com.github.alacs

import org.scalatest.FunSuite

class AlacsFunSuite extends FunSuite {

  val commonDir = "common/"

  def runAll(dirName: String, fileName: String): List[PluginMessage] = 
    RunPlugin.runPlugin(dirName + fileName + ".scala")

  def runBugs(dirName: String, fileName: String): List[Bug] = runAll(dirName, fileName)

  def commonRunAll(fileName: String): List[PluginMessage] =  runAll(commonDir, fileName)

  def commonRunBugs(fileName: String): List[Bug] =  runBugs(commonDir, fileName)
}
