package com.github.alacs

import org.scalatest.FunSuite

class AlacsPattern002Tests extends AlacsPatternSuite {

  override val id = 2

  test("div by 0") {
    val bugs: List[Bug] = run("DivBy0")
    checkBug(bugs)
  }

  test("div by hex 0") {
    val bugs: List[Bug] = run("DivBy0Hex")
    checkBug(bugs)
  }

  test("div by 1") {
    expect(Nil) { run("DivByOne") }
  }

  test("mod 0") {
    val bugs: List[Bug] = run("Mod0")
    checkBug(bugs)
  }
}
