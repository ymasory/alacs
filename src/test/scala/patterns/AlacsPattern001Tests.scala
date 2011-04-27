package com.github.alacs

class AlacsPattern001Tests extends AlacsPatternSuite {

  positive("missing equals, string literal", "MissingEqualsStringLiteral")
  positive("missing equals, function body is block", "MissingEqualsBlock")

  negative("not missing equals, string literal", "NotMissingEqualsStringLiteral")
  negativeCommon("empty procedures are fine", "EmptyProcedure")
}
