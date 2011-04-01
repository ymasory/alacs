package com.github.alacs

import org.scalatest.FunSuite

class BPUnintentionalProcedureTests extends FunSuite {

  def run(fileName: String): List[Bug] = {
    RunPlugin.runPlugin(
      "001-unintentional-procedure/" + fileName + ".scala")
  }

  def commonRun(fileName: String): List[Bug] = {
    RunPlugin.runPlugin("common/" + fileName + ".scala")
  }

  // test("missing equals, string literal") {
  //   val expected = BugReport(List(Bug(1)))

  //   expect(expected) {
  //     run("MissingEqualsStringLiteral")
  //   }
  // }

  test("not missing equals, string literal") {
    expect(Nil) {
      run("NotMissingEqualsStringLiteral")
    }
  }

  test("empty procedures are fine") {
    expect(Nil) {
      commonRun("EmptyProcedure")
    }
  }

  // test("missing equals, function body is block") {
  //   val expected = BugReport(List(Bug(1)))

  //   expect(expected) {
  //     run("MissingEqualsBlock")
  //   }
  // }
}
