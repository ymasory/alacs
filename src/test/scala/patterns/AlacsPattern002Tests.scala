package com.github.alacs

import org.scalatest.FunSuite

class AlacsPattern002Tests extends AlacsPatternSuite {

  override val id = 2

  test("div by 0") {
    val bugs: List[Bug] = run("DivByZero")
    check(bugs)
  }

  test("div by 1") {
    expect(Nil) { run("DivByOne") }
  }
}
