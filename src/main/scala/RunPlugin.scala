package com.github.alacs

import scala.tools.nsc.{ Global, Settings }
import scala.tools.nsc.reporters.ConsoleReporter
import java.io.{ File, StringWriter, PrintWriter }

object RunPlugin {

  //------------------------------------------------------------------
  //WARNING
  //Keep this string in-sync with the one in /project/build.properties
  val scalaVersion = "2.8.1"
  //------------------------------------------------------------------

  val curDir = (new java.io.File(".")).getCanonicalPath
  val pluginLoc = curDir + "/target/scala_" + scalaVersion + "/alacs-alpha.jar"
  val testPrefix = curDir + "/src/test/resources/"

  def runPlugin(path: String): BugReport = runPluginWithExec(path)

  def runPluginInProcess(fileName: String): BugReport = {
    val settings = new Settings
    settings.classpath.tryToSet(List(
      "project/boot/scala-" + scalaVersion + "/lib/scala-compiler.jar" +
      ":project/boot/scala-" + scalaVersion + "/lib/scala-library.jar"))
    val stringBuf = new StringWriter()
    val writer = new PrintWriter(stringBuf)
    val reporter = new ConsoleReporter(settings, null, writer)
    val compiler = new Global(settings, reporter)
    (new compiler.Run).compile(List(testPrefix + fileName))
    val output = stringBuf.toString
    Console.err.println("OUTPUT: <" + output + ">")
    BugReport.parse(output.split("\n").toList)
  }

  def runPluginWithExec(fileName: String): BugReport = {
    val cmd = Array("scalac",
      "-d", "target",
      "-Xplugin:" + pluginLoc,
      testPrefix + fileName)
    val (stdout, stderr, ret) = call(cmd)
    if (ret != 0) throw ScalacExitCodeException(ret, stderr.mkString(" "))
    BugReport.parse(stdout)
  }

  /** borrows from scala-utilities, LGPL
   * http://code.google.com/p/scala-utilities/
   */
  def call(cmd: Array[String]) = {
    import java.io._
    val runTime = Runtime.getRuntime
    val process = runTime.exec(cmd)
    val stdoutBuffer = new BufferedReader(
      new InputStreamReader(process.getInputStream))
    val stderrBuffer = new BufferedReader(
      new InputStreamReader(process.getErrorStream))

    def drainBuffer(buff: BufferedReader) = {
      var line: String = null
      var lineList: List[String] = Nil
      do {
        line = buff.readLine
        if (line != null) {
          lineList = line :: lineList
        }
      } while (line != null)
      lineList.reverse
    }

    val stdout = drainBuffer(stdoutBuffer)
    val stderr = drainBuffer(stderrBuffer)

    process.waitFor
    stdoutBuffer.close()
    stderrBuffer.close()

    val ret = process.exitValue
    (stdout, stderr, ret)
  }
}

case class ScalacExitCodeException(code: Int, stderr: String)
  extends RuntimeException(code.toString + ": " + stderr)
