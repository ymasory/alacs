package com.github.alacs

import org.scalatest.FunSuite

class BPUnintentionalProcedureTests extends AlacsFunSuite {

  def run = genRun("001-unintentional-procedure/")

  test("missing equals, string literal") {
    val bugs: List[Bug] = run("MissingEqualsStringLiteral")
    assert(bugs.length === 1)
  }

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

  test("missing equals, function body is block") {
    pending
    val bugs: List[Bug] = run("MissingEqualsBlock")
    assert(bugs.length === 1)
  }
}
