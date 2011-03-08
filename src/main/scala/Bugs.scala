package com.github.alacscala.alacs

case class Bug(bugId: Int)

case class BugReport(bugs: List[Bug]) {

  val LF = "\n"
  val report = {
    LF +
      "REPORT" + LF +
      "------" + LF + bugs.map(_.bugId).mkString(LF) + LF
  }
}

case object BugReport {

  val EmptyReport = BugReport(List.empty[Bug])

  def parse(in: List[String]): BugReport = {
    var curReport = EmptyReport
    for (line <- in) {
      try {
        val bugId = line.toInt
        curReport = curReport.copy(Bug(bugId) :: curReport.bugs)
      } catch {
        case _ =>
      }
    }
    curReport
  }
}
