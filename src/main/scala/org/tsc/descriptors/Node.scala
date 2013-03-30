package org.tsc.descriptors

import NodeType._;

/**
 * @author Dmitry Grudzinskiy
 *
 */
class Node(val nodeType: NodeType, val comment: String, val name: String, val annotation: Annotation, val children: List[Node]) {
  override def toString: String = name + "(" + annotation + ")[" + children + "]"
}

case class Class(override val name: String, override val comment: String, override val annotation: Annotation, override val children: List[Method]) extends Node(NodeType.Class, name, comment, annotation, children)

case class Method(override val name: String, override val comment: String, override val annotation: Annotation, override val children: List[MethodParameter]) extends Node(NodeType.Method, name, comment, annotation, children)

case class MethodParameter(override val name: String, override val comment : String, override val annotation: Annotation) extends Node(NodeType.Parameter, name, comment, annotation, List())

case class Annotation(name: String, values: Map[String, String])


