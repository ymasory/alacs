package com.github.alacscala.alacs

import org.scalatest.FunSuite

class UnintentionalProcedure extends FunSuite {

  test("missing equals, string literal") {
    val expected = BugReport(List(Bug(1)))

    expect(expected) {
      RunPlugin.runPlugin(
        "001-unintentional-procedure/MissingEqualsStringLiteral.scala")
    }
  }

  test("not missing equals, string literal") {
    val expected = BugReport.EmptyReport

    expect(expected) {
      RunPlugin.runPlugin(
        "001-unintentional-procedure/NotMissingEqualsStringLiteral.scala")
    }
  }

  test("empty procedures are fine") {
    val expected = BugReport.EmptyReport

    expect(expected) {
      RunPlugin.runPlugin(
        "common/EmptyProcedure.scala")
    }
  }
}
