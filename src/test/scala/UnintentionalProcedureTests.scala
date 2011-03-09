package com.github.alacscala.alacs

import org.scalatest.FunSuite

class UnintentionalProcedure extends FunSuite {

  def run(fileName: String) = {
    RunPlugin.runPlugin("001-unintentional-procedure/" + fileName + ".scala")
  }

  test("missing equals, string literal") {
    val expected = BugReport(List(Bug(1)))

    expect(expected) {
      run("MissingEqualsStringLiteral")
    }
  }

  test("not missing equals, string literal") {
    val expected = BugReport.EmptyReport

    expect(expected) {
      run("NotMissingEqualsStringLiteral")
    }
  }

  test("empty procedures are fine") {
    val expected = BugReport.EmptyReport

    expect(expected) {
      RunPlugin.runPlugin(
        "common/EmptyProcedure.scala")
    }
  }

  test("missing equals, function body is block") {
    val expected = BugReport(List(Bug(1)))

    expect(expected) {
      run("MissingEqualsBlock")
    }
  }
}
