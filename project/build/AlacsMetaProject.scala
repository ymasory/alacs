import sbt._

class AlacsMetaProject(info: ProjectInfo) extends DefaultProject(info) {
  
  //project name
  override val artifactID = "alacs"

  //deployment
  override def managedStyle = ManagedStyle.Maven
  val publishTo = ("Scala Tools Nexus" at 
                   "http://nexus.scala-tools.org/" +
                   "content/repositories/snapshots")
  Credentials(Path.userHome / ".ivy2"/ ".credentials", log)
  override def packageSrcJar= defaultJarPath("-sources.jar")
  val sourceArtifact = Artifact.sources(artifactID)
  override def packageToPublishActions = super.packageToPublishActions ++
    Seq(packageSrc)


  //files to go in packaged jars
  val extraResources = "README.md" +++ "LICENSE"
  override val mainResources = super.mainResources +++ extraResources

  //turn down logging a little
  log.setLevel(Level.Warn)
  log.setTrace(2)

  //the compiler plugin project
  lazy val alacsProject = project("alacs", "alacs", new AlacsProject(_))

  //testing subprojects, one for each bug pattern
  lazy val alacs001 = project("patterns" / "001-unintentional-procedure",
                              "001 Unintentional Procedure",
                              new AlacsPatternTestProject(_))

  class AlacsProject(info: ProjectInfo) extends DefaultProject(info) {

    //compiler options
    override def compileOptions = super.compileOptions ++
      compileOptions("-deprecation", "-unchecked")

    //scaladoc options
    override def documentOptions =
      LinkSource ::
      documentTitle(name + " " + version + " API") ::
      windowTitle(name + " " + version + " API") ::
      Nil
  }


  class AlacsPatternTestProject(info: ProjectInfo) 
    extends DefaultProject(info)  with AutoCompilerPlugins {

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
