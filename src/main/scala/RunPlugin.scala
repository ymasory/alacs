package com.github.alacscala.alacs

import scala.tools.nsc.{Global, Settings}
import scala.tools.nsc.reporters.ConsoleReporter
import java.io.{File, StringWriter, PrintWriter}

object RunPlugin {

  val curDir = (new java.io.File(".")).getCanonicalPath
  val pluginLoc = curDir + "/target/scala_2.8.1/alacs-alpha.jar"
  val testPrefix = curDir + "/src/test/resources/"

  def runPlugin(path: String): BugReport = runPluginWithExec(path)

  def runPluginInProcess(path: String): BugReport = {
    val settings = new Settings
    val writer = new PrintWriter(new StringWriter())
    val reporter = new ConsoleReporter(settings, null, writer)
    val compiler = new Global(settings, reporter)
    val run = new compiler.Run
    val source = (new File(curDir, "SelfCompile.scala")).getCanonicalPath
    run.compile(List(source))
    BugReport.EmptyReport
  }

  def runPluginWithExec(fileName: String): BugReport = {
    val cmd = Array("scalac",
                    "-d",  "target",
                    "-Xplugin:" + pluginLoc,
                    testPrefix + fileName)
    val (stdout, stderr, ret) = call(cmd)
    if (ret != 0) throw ScalacExitCodeException(ret, stderr.mkString(" "))
    BugReport.parse(stdout)
  }

  /** borrows from scala-utilities, LGPL
    * http://code.google.com/p/scala-utilities/
    */
  def call (cmd : Array[String]) = {
    import java.io._
    val runTime = Runtime.getRuntime
    val process = runTime.exec(cmd)
    val stdoutBuffer = new BufferedReader(
      new InputStreamReader(process.getInputStream))
    val stderrBuffer = new BufferedReader(
      new InputStreamReader(process.getErrorStream))
    
    def drainBuffer(buff: BufferedReader) = {
      var line : String = null
      var lineList : List[String] = Nil
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
