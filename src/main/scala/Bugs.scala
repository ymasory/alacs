package com.github.alacscala.alacs

case class Bug(bugId: Int)

case class BugReport (bugs: List[Bug]) {

  val LF = "\n"
  val report  = {
    LF +
    "REPORT" + LF +
    "------" + LF + bugs.map(_.bugId).mkString(LF) + LF
  }
}

case object BugReport {

  val EmptyReport = BugReport(List.empty[Bug])

  val curDir = (new java.io.File(".")).getCanonicalPath
  val pluginLoc = curDir + "/target/scala_2.8.1/alacs-alpha.jar"
  val testPrefix = curDir + "/src/test/resources/"

  def parse(in: List[String]): BugReport = {
    var curReport = EmptyReport
    for (line <- in) {
      println("LINE: " + line)
      try {
        println(line.toInt)
        println("SUCCESS")
      }
      catch {
        case _ => println("FAIL")
      }
      
      val els = line.split("\\s")
      curReport = els.head match {
        
        case _ => curReport
      }
    }
    curReport
  }

  def runPlugin(fileName: String): BugReport = {
    val cmd = Array("scalac",
                    "-d",  "target",
                    "-Xplugin:" + pluginLoc,
                    testPrefix + fileName)
    val (stdout, stderr, ret) = call(cmd)
    println("STDOUT: " + stdout)
    println("STDERR: " + stdout)
    if (ret != 0) throw ScalacExitCodeException(ret, stderr.mkString(" "))
    parse(stdout)
  }

  case class ScalacExitCodeException(code: Int, stderr: String)
       extends RuntimeException(code.toString + ": " + stderr)

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
