package com.github.alacs

class AlacsPattern002Tests extends AlacsPatternSuite {

  override val id = 2

  positive("div by 0", "DivBy0")
  positive("div by hex 0", "DivBy0Hex")
  positive("mod 0", "Mod0")

  negative("div by 1", "DivByOne")
}
