package org.tsc

import com.sun.javadoc.{RootDoc, Doclet}
import extractors.WebServicesExtractor
import writers.HtmlClientWriter
import xml.XML
import java.lang.String
import java.io.File

/**
 * @author Dmitry Grudzinskiy
 *
 */
object ScalaSpringDoclet extends Doclet {

  private val CSS_NAME: String = "tsc.css"

  def start(root: RootDoc) = {

    // read css
    val cssFileName = System.getProperty("css")
    val css = cssFileName match {
      case "" => readFile(getClass.getResourceAsStream("/" + CSS_NAME))
      case _ => readFile(cssFileName)
    }

    // create out dir
    val destDirName = System.getProperty("destDir")
    new File(destDirName).mkdirs();

    // generate and save html
    XML.save(destDirName + "/" + System.getProperty("fileName"), HtmlClientWriter.html(WebServicesExtractor.extractWebServices(root), css))

    true
  }
}
