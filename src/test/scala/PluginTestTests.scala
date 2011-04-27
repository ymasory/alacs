package com.github.alacs.test

import com.github.alacs._

class PluginTestTests extends AlacsFunSuite {

  val run = {runAll("test-tests", _: String)}

  test("testing framework gets warnings") {
    val messages = run("Warning")
    assert(messages.length === 1)
    assert(messages(0).toString contains "match is not exhaustive!")
  }

  test("testing framework gets errors") {
    val messages = run("Error")
    assert(messages.length === 1)
    assert(messages(0).toString contains "expected but eof found")
  }

  test("implicit conversion to Bug only picks up on Alacs") {
    val bugs: List[Bug] = run("Error")
    assert(bugs.length === 0)
  }
}
