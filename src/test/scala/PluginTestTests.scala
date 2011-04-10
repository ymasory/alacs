package com.github.alacs

import org.scalatest.FunSuite

class PluginTestTests extends FunSuite {

  def run(fileName: String) = {
    RunPlugin.runPlugin("000-test-tests/" + fileName + ".scala")
  }

  test("testing framework gets info messages") {
    val messages = run("Info")
    assert(messages.length === 1)
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
