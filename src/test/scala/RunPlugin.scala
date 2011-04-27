package com.github.alacs.test

import scala.tools.nsc.{ Global, Settings }
import scala.tools.nsc.reporters.{ Reporter, StoreReporter }
import java.io.{ File, StringWriter, PrintWriter }

import com.github.alacs._

object RunPlugin {

  val curDir = (new java.io.File(".")).getCanonicalPath
  val testPrefix = curDir + "/src/test/resources/"

  lazy val scalaVersion = {
    val matcher = """version (\d+\.\d+\.\d+).*""".r
    util.Properties.versionString match {
      case matcher(versionString) => Some(versionString)
      case _ => {
        Console.err println ("could not detect scala version")
        None
      }
    }
  } getOrElse "2.8.0"

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
}
