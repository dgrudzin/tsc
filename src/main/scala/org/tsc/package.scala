package org

import java.io.InputStream
import io.Source

/**
 * @author Dmitry Grudzinskiy
 *
 */
package object tsc {

  def readFile(fileIs: InputStream): String = {
    val file = Source.fromInputStream(fileIs)
    val str = file.mkString
    file.close
    str
  }

  def readFile(filePath: String): String = {
    val file = Source.fromFile(filePath)
    val str = file.mkString
    file.close
    str
  }
}
