package com.github.alacs

class AlacsPattern001Tests extends AlacsPatternSuite {

  override val id = 1

  positive("missing equals, string literal", "MissingEqualsStringLiteral")
  positive("missing equals, function body is block", "MissingEqualsBlock")

  negative("not missing equals, string literal", "NotMissingEqualsStringLiteral")

  test("empty procedures are fine") {
    expect(Nil) {
      commonRunBugs("EmptyProcedure")
    }
  }
}
