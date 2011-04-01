package com.github.alacs

import scala.tools.nsc.{ Global, Settings }
import scala.tools.nsc.reporters.{ Reporter, StoreReporter }
import java.io.{ File, StringWriter, PrintWriter }

object RunPlugin {

  //------------------------------------------------------------------
  //WARNING
  //Keep this string in-sync with the one in /project/build.properties
  val scalaVersion = "2.8.1"
  //------------------------------------------------------------------

  val curDir = (new java.io.File(".")).getCanonicalPath
  val testPrefix = curDir + "/src/test/resources/"

  def runPlugin(fileName: String): List[PluginMessage] = {
    val settings = new Settings
    settings.classpath.tryToSet(List(
      "project/boot/scala-" + scalaVersion + "/lib/scala-compiler.jar" +
      ":project/boot/scala-" + scalaVersion + "/lib/scala-library.jar"))
    val reporter = new StoreReporter
    val compiler = new Global(settings, reporter) {
      override protected def computeInternalPhases() {
        super.computeInternalPhases
        for (phase <- new AlacsPlugin(this).components)
          phasesSet += phase
      }
    }
    (new compiler.Run).compile(List(testPrefix + fileName))
    reporter.infos.toList
  }
}
