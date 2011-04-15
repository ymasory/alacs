import sbt._

class AlacsMetaProject(info: ProjectInfo) extends ParentProject(info) {
  
  //compile subprojects in parallel
  override def parallelExecution = true
  
  //turn down logging a little
  log.setLevel(Level.Warn)
  log.setTrace(2)

  //the compiler plugin project
  lazy val plugin = project("plugin", "plugin", new AlacsProject(_))

  //testing subprojects, one for each bug pattern
  lazy val alacs001 = project("patterns" / "001-unintentional-procedure",
                              "001 Unintentional Procedure",
                              new AlacsPatternTestProject(_),
                              plugin)
}

protected class AlacsProject(info: ProjectInfo) extends DefaultProject(info) {

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
  override def deliverProjectDependencies = Nil


  //files to go in packaged jars
  val extraResources = "README.md" +++ "LICENSE"
  override val mainResources = super.mainResources +++ extraResources

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

protected class AlacsPatternTestProject(info: ProjectInfo) 
          extends DefaultProject(info)  with AutoCompilerPlugins {

  val scalatools = ("Scala Tools Nexus" at
                    "http://nexus.scala-tools.org/" +
                    "content/repositories/snapshots")
  //managed dependencies from built-in repositories
  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test->default"

  //compiler options
  override def compileOptions = super.compileOptions ++
    compileOptions("-deprecation", "-unchecked")

  //prevent deployment
  def doNothing = task { None }
  override def publishLocalAction = doNothing
  override def deliverLocalAction = doNothing
  override def publishAction = doNothing
  override def deliverAction = doNothing
}
