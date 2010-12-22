import sbt._

class AlacsProject(info: ProjectInfo) extends DefaultProject(info) with Exec {

  val findBugs = "com.google.code.findbugs" % "findbugs" % "1.3.9"

  override def artifactID = "Alacs"
  override def mainResources = super.mainResources +++ "README.markdown" +++ "LICENSE"
  override def compileOptions = super.compileOptions ++ Seq(Deprecation, Unchecked)
  override def documentOptions = super.documentOptions ++ Seq(LinkSource)
}
