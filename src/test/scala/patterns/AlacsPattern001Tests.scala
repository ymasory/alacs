package com.github.alacs

import org.scalatest.FunSuite

class AlacsPattern001Tests extends AlacsFunSuite with AlacsPatternSuite {

  override val id = 1

  test("missing equals, string literal") {
    val bugs: List[Bug] = run("MissingEqualsStringLiteral")
    checkBug(bugs)
  }

  test("not missing equals, string literal") {
    expect(Nil) {
      run("NotMissingEqualsStringLiteral")
    }
  }

  test("empty procedures are fine") {
    expect(Nil) {
      commonRunBugs("EmptyProcedure")
    }
  }

  test("missing equals, function body is block") {
    val bugs: List[Bug] = run("MissingEqualsBlock")
    checkBug(bugs)
  }
}
