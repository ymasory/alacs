package com.github.alacs

import org.scalatest.FunSuite

class PluginTestTests extends AlacsFunSuite {

  val run = genRun("000-test-tests/")

  test("testing framework gets info messages") {
    pending
    val bugs: List[Bug] = run("Info")
  }

  test("testing framework gets warnings") {
    val messages = run("Warning")
    assert(messages.length === 1)
  }

  test("testing framework gets errors") {
    val messages = run("Error")
    assert(messages.length === 1)
  }

  test("implicit conversion to Bug doesn't only picks up on Alacs") {
    val bugs: List[Bug] = run("Error")
    assert(bugs.length === 0)
  }
}
