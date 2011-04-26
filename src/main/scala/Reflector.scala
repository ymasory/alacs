package com.github.alacs

import java.io.File
import java.net.URL

import scala.tools.nsc.Global

import com.github.alacs.patterns.PatternDetector

import java.lang.reflect.Constructor

object Reflector {

  def discoverPatterns(): List[Constructor[PatternDetector]] = {
    val classNames = discoverPackageClasses("com.github.alacs.patterns").
                     filter(_ contains ".AlacsPattern").
                     sorted
    classNames map { name =>
      val clazz = Class.forName(name)
      val constructors = clazz.getConstructors().toList
      val con: Constructor[PatternDetector] =
        clazz.getConstructor(classOf[Global])
        .asInstanceOf[Constructor[PatternDetector]]
      con
    }
  }

  def discoverPackageClasses(packageName: String): List[String] = {
    val name = {
      val name =
        if (packageName.startsWith("/"))
          packageName
        else
          "/" + packageName
      name replace ('.', '/')
    }

    val urlOpt = Option(Reflector.getClass.getResource(name))
    urlOpt match {
      case Some(url) => {
        val directory = new File(url.getFile)
        if (directory.exists()) {
          val files = directory.list().toList.
                      filter(_.endsWith(".class")).
                      filter(s => (s contains "$") == false)
          val classes: List[String] = files flatMap { file =>
            val className = (
              packageName + "." +
              file.substring(0, file.length - 6)
            )
            try {
              Class.forName(className)
              Some(className)
            }
            catch {
              case _: ClassNotFoundException => None
            }
          }
          classes
        }
        else Nil
      }
      case None => Nil
    }
  }

  // def main(args: Array[String]) {
  //   println("constructors: " + discoverPatterns())
  // }
}
