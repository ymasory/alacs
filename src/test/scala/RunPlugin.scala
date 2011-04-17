package com.github.alacs

import scala.tools.nsc.{ Global, Settings }
import scala.tools.nsc.reporters.{ Reporter, StoreReporter }
import java.io.{ File, StringWriter, PrintWriter }

object RunPlugin {

  val curDir = (new java.io.File(".")).getCanonicalPath
  val testPrefix = curDir + "/src/test/resources/"
  val scalaVersion = getRunningScalaVersion()

  def runPlugin(fileName: String): List[PluginMessage] = {
    val settings = new Settings 
    settings.outputDirs setSingleOutput (curDir + "/target")
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

  def getRunningScalaVersion() = {
    try {
      val props = new java.util.Properties
      props.load(getClass.getResourceAsStream("/library.properties"))
      val line = props.getProperty("version.number")
      val Version = """(\d\.\d\.\d).*""".r
      val Version(versionStr) = line
      versionStr
    }
    catch {
      case e => {
        e.printStackTrace
        "2.8.0"
      }
    }
  }
}
