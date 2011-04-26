import java.io.File
import java.net.URL

object Reflector {

  def find(packageName: String): List[String] = {
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
          val files = directory.list().toList.filter(_.endsWith(".class"))
          val classes: List[String] = files flatMap { file =>
            val className = file.substring(0, file.length - 6)
            Option(Class.forName(className)) match {
              case Some(clazz) => Some(className)
              case None        => Nil
            }
          }
          classes
        }
        else Nil
      }
      case None => Nil
    }
  }
}
