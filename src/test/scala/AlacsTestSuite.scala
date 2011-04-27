package com.github.alacs

import org.scalatest.FunSuite

trait AlacsFunSuite extends FunSuite {

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

  val id = {
    val TestNum = """.*AlacsPattern(\d+).*""".r
    this.getClass.getName match {
      case TestNum(num) => num.toInt
    }
  }

  val run =
    runBugs("pattern" + BugPattern.bugNumFormatter.format(id), _: String)

  def positive(desc: String, file: String): Unit =
    positive(desc, file, false)
  
  def positiveCommon(desc: String, file: String): Unit =
    positive(desc, file, false, commonRunBugs)

  def positive(desc: String, file: String, pend: Boolean): Unit =
    positive(desc, file, pend, run)

  def atest(desc: String)(thunk: => Any) =
    test("Alacs" + BugPattern.bugNumFormatter.format(id) + ": " + desc)(thunk)
    
  def positive(desc: String, file: String, pend: Boolean,
               runFunc: String => List[Bug]) {
    atest(desc) {
      if (pend) pending
      val bugs: List[Bug] = runFunc(file)
      val numBugs = bugs.length
      if (numBugs > 0) {
      val bugId = bugs(0).pat.bugId
        if (bugId != id)
          fail("[WRONG BUG: expected to get a bug with id " + id +
               " but got one with id " + bugId + "]")
      }
      val explanation = "expected exactly 1 bug but found " + bugs.length + "]"
      if (numBugs == 0)
        fail("[FALSE NEGATIVE: " + explanation)
      else if (numBugs > 1)
        fail("[FALSE POSITIVES: " + explanation)
    }
  }
  
  def negative(desc: String, file: String): Unit =
    negative(desc, file, false)

  def negativeCommon(desc: String, file: String) =
    negative(desc, file, false, commonRunBugs)
    
  def negative(desc: String, file: String, pend: Boolean): Unit =
    negative(desc, file, pend, run)
  
  def negative(desc: String, file: String, pend: Boolean,
               runFunc: String => List[Bug]) {
    atest(desc) {
      if (pend) pending
      val bugs: List[Bug] = runFunc(file)
      assert (
        bugs.length === 0,
        "[FALSE POSITIVE: expected 0 bugs but found " + bugs.length + "]")
    }
  }
}
