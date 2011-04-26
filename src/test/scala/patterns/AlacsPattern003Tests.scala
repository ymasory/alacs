package com.github.alacs

import org.scalatest.FunSuite

class AlacsPattern003 extends AlacsPatternSuite {

  override val id = 3

  test("mod 1") {
    val bugs: List[Bug] = run("Mod1")
    checkBug(bugs)
  }

  test("mod 2") {
    expect(Nil) { run("Mod2") }
  }
}
