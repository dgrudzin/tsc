package org.tsc.descriptors

/**
 * @author Dmitry Grudzinskiy
 *
 */
object NodeType extends Enumeration {
  type NodeType = Value
  val Class, Method, Parameter = Value
}
