import sbt._

class AlacsTestProject(info: ProjectInfo) extends DefaultProject(info) {
  
  //bugpattern subprojects
  lazy val unintentionalProcedure = project("001-unintentional-procedure",
                                            "001-unintentional-procedure",
                                            new AlacsPatternTestProject(_))
  
  //turn down logging a little
  log.setLevel(Level.Warn)
  log.setTrace(2)

  class AlacsPatternTestProject(info: ProjectInfo) extends DefaultProject(info)  with AutoCompilerPlugins {
    val scalatools = ("Scala Tools Nexus" at
                      "http://nexus.scala-tools.org/" +
                      "content/repositories/snapshots")
    //managed dependencies from built-in repositories
    val scalatest = "org.scalatest" % "scalatest" % "1.2"

    //compiler options
    override def compileOptions = super.compileOptions ++
      compileOptions("-deprecation", "-unchecked")    
  }
}
