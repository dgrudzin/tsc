package org.tsc

import com.sun.javadoc.AnnotationDesc

/**
 * @author Dmitry Grudzinskiy
 *
 */
package object extractors {

  def matchesType(annotation: AnnotationDesc, mappingType: String): Boolean =
    try {
      annotation.annotationType.name == mappingType
    } catch {
      case e: ClassCastException => {
        println(e.getMessage)
        false
      }
    }
}
