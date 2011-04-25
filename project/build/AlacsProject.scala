import sbt._

class AlacsProject(info: ProjectInfo) extends DefaultProject(info) with Exec {
  
  //deployment
  override def managedStyle = ManagedStyle.Maven

  override def defaultPublishRepository = {
    val nexusDirect = "http://nexus-direct.scala-tools.org/content/repositories/"
    if (version.toString.endsWith("SNAPSHOT"))
          Some("scala-tools snapshots" at nexusDirect + "snapshots/")
        else
          Some("scala-tools releases" at nexusDirect + "releases/")
  }

  Credentials(Path.userHome / ".ivy2"/ ".credentials", log)
  override def packageSrcJar= defaultJarPath("-sources.jar")
  val sourceArtifact = Artifact.sources(artifactID)
  override def packageToPublishActions = super.packageToPublishActions ++
    Seq(packageSrc)


  //make test depend on package since the testers
  //use the plugin jar generated by package
  override def testAction = super.testAction dependsOn(packageAction)

  //managed dependencies from built-in repositories
  val scalatest = "org.scalatest" % "scalatest" % "1.2"
  
  //files to go in packaged jars
  val extraResources = "README.md" +++ "LICENSE"
  override val mainResources = super.mainResources +++ extraResources

  //turn down logging a little
  log.setLevel(Level.Warn)
  log.setTrace(2)

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
