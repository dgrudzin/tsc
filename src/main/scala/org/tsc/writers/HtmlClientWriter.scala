package org.tsc.writers

import org.tsc.descriptors._
import xml.NodeSeq
import org.tsc.descriptors.Method
import org.tsc.descriptors.Class
import org.tsc.descriptors.MethodParameter
import org.tsc

object HtmlClientWriter {

  def html(classes: List[Class], css: String) =
    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
      <head>
        <title>Web services client</title>
        <script type="text/javascript">
          {xml.Unparsed(tsc.readFile(getClass.getResourceAsStream("/jquery-1.9.1.min.js")))}
        </script>
        <style>
          {css}
        </style>
        <script language="Javascript" type="text/javascript">
          {for (cl <- classes; m <- cl.children) yield formSenderScript(m, cl, tsc.readFile(getClass.getResourceAsStream("/get.js")), tsc.readFile(getClass.getResourceAsStream("/any.js")))}
        </script>
      </head>
      <body>
        <h1>Web Services Client</h1>{for (cl <- classes) yield nodeElement(cl, None)}
      </body>
    </html>

  def nodeElement(node: Node, parent: Option[Node]): NodeSeq =
    node match {
      case node: Class =>
        <h3>
          {node.name}
        </h3> <hr/>
          <div>
            {for (m <- node.children) yield nodeElement(m, Option(node))}
          </div>
      case node: Method =>
        <h4>
          {fullPath(node, parent.get)}
        </h4>
          <form id={serviceId(node, parent.get)} name={fullPath(node, parent.get)}
                method={method(node, parent.get)}>
            <p>
              {for (mp <- node.children) yield nodeElement(mp, Option(node))}
            </p> <input type="submit"/>
            <p>Response:
              <span id={serviceId(node, parent.get) + "_response"}>N/A</span>
            </p>
          </form> <hr/>
      case node: MethodParameter => {
        paramInput(node.name, node.annotation.values)
      }
    }

  def annotationValue(key: String, node: Node): String = annotationValue(key, node, "")

  def annotationValue(key: String, node: Node, default: String): String = annotationValue(key, node.annotation.values.get(key) getOrElse default)

  def annotationValue(key: String, value: String): String =
    (key, value) match {
      case ("method", value) => value drop (value.lastIndexOf('.') + 1)
      case (_, value) => value.replaceAll("\"", "")
      case _ => ""
    }

  def paramInput(paramName: String, values: Map[String, String]) =
    <p>
      {paramName + ":"}<input name={paramName} type="text" value={values.getOrElse("defaultValue", "").replaceAll("\"", "")}></input>
    </p>

  private def formSenderScript(md: Method, cl: Class, getScript: String, anyScript: String) = {
    val httpMethod = method(md, cl)
    httpMethod match {
      case "GET" => getScript.replaceAll("FORM_ID", serviceId(md, cl))
      case _ => anyScript.replaceAll("FORM_ID", serviceId(md, cl)).replaceAll("METHOD", httpMethod)
    }
  }

  private def serviceId(node: Node, parent: Node) = parent.name + "_" + node.name

  private def fullPath(node: Node, parent: Node) = annotationValue("value", parent) + annotationValue("value", node)

  private def method(method: Node, parent: Node) = annotationValue("method", method, annotationValue("method", parent, "GET"))
}