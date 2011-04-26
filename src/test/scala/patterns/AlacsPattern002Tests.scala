package com.github.alacs

import org.scalatest.FunSuite

class AlacsPattern002Tests extends AlacsPatternSuite {

  override val id = 2

  test("simple case") {
    val bugs: List[Bug] = run("DivByZero")
    check(bugs)
  }
}
