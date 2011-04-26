package com.github.alacs

import org.scalatest.FunSuite

import java.io.File

class AlacsFunSuite extends FunSuite {

  val commonDir = "common"

  def runAll(dirName: String, fileName: String): List[PluginMessage] = {
    val srcFilePath = dirName + "/" + fileName + ".scala"
    RunPlugin.runPlugin(srcFilePath)
  }

  def runBugs(dirName: String, fileName: String): List[Bug] =
    runAll(dirName, fileName)

  def commonRunAll(fileName: String): List[PluginMessage] =
    runAll(commonDir, fileName)

  def commonRunBugs(fileName: String): List[Bug] =
    runBugs(commonDir, fileName)
}

trait AlacsPatternSuite extends AlacsFunSuite {

  val id: Int

  val run =
    runBugs("pattern" + BugPattern.bugNumFormatter.format(id), _: String)

  def checkBug(bugs: List[Bug]) {
    assert(
      bugs.length === 1,
      "[expected exactly 1 bug but found " + bugs.length + "]")
    val bugId = bugs(0).pat.bugId
    assert(
      bugId === id,
      "[expected to get a bug with id " + id + " but got one with id " +
      bugId + "]")
  }

  def checkNil(bugs: List[Bug]) {
    assert (
      bugs.length === 0,
      "[expected 0 bugs but found " + bugs.length + "]")
  }
}
