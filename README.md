# Alacs - Bug finder for Scala #

## Try it ## 
Alacs is not ready for serious use, but feel free to try it out.

### sbt ###
Modify your `Project.scala` thusly:

    import sbt._
    class Project(info: ProjectInfo) extends DefaultProject(info) with AutoCompilerPlugins {
      val scalaToolsSnapshots = "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"
      val alacs = compilerPlugin("com.github.alacs" %% "alacs" % "0.0.0-SNAPSHOT")
    }

### CLI ###
Build the project with `sbt package` on the command line.

    scalac -Xplugin:/path/to/alacs/target/scala_2.8.1/alacs_2.8.1-0.0.0-SNAPSHOT.jar -Xplugin-require:alacs foo.scala

### IDEs ###
There is no support for IDEs (Eclipse, IntelliJ, Netbeans) yet. For all I know it's really trivial to use compiler plugins with them.

## Goals ##
The aim is to provide a tool for finding defects in Scala tools by analyzing ASTs in compilation. Think [FindBugs](http://findbugs.sourceforge.net/) for Scala.

## Resources 
The [scala-corpus](https://github.com/alacscala/scala-corpus) is a large collection of Scala code intended for testing Alacs.

## Contributing ##
There are two ways to contribute to Alacs:

* Submitting suggestions for bug reports. You can do this on [the wiki](https://github.com/alacscala/alacs/wiki).
* Writing code (e.g., submitting patches, joining the team, etc.).

Thank you for helping improve Alacs!

