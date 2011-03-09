package com.github.alacscala.alacs

import org.scalatest.FunSuite

class AccidentalProcedure extends FunSuite {

  test("missing equals, string literal") {
    val expected = BugReport(List(Bug(1)))

    expect(expected) {
      RunPlugin.runPlugin(
        "001-accidental-procedure/MissingEqualsStringLiteral.scala")
    }
  }

  test("not missing equals, string literal") {
    val expected = BugReport.EmptyReport

    expect(expected) {
      RunPlugin.runPlugin(
        "001-accidental-procedure/NotMissingEqualsStringLiteral.scala")
    }
  }

  test("empty procedures are fine") {
    val expected = BugReport.EmptyReport

    expect(expected) {
      RunPlugin.runPlugin(
        "common/EmptyDef2.scala")
    }
  }
}
