package org.tsc.extractors

import com.sun.javadoc._
import org.tsc.descriptors._

/**
 *
 * @author Dmitry Grudzinskiy
 *
 */
object WebServicesExtractor {

  val RequestMapping = "RequestMapping"
  val PathVariable = "PathVariable"
  val RequestParam = "RequestParam"

  val AnnotationValue = "value"
  val AnnotationMethod = "method"
  val AnnotationDefaultValue = "defaultValue"
  val AnnotationRequired = "required"

  val ClassMethodAnnotations = List(AnnotationValue, AnnotationMethod)
  val ParamAnnotations = Map((PathVariable, List()), (RequestParam, List(AnnotationValue, AnnotationDefaultValue, AnnotationRequired)))

  /**
   * Returns structure tree of all classes annotated with {@link RequestMapping} annotation.
   * The resulting list consists of {@link Node} where each node contains class name, l value and list of children annotated methods.
   * Each method is also a node containing name, annotation information and children parameters.
   *
   * @param root document generated by JavaDoc
   * @return list of annotated classes
   */
  def extractWebServices(root: RootDoc) = {

    def extractClass(classDoc: ClassDoc, annotation: AnnotationDesc): Class =
      Class(
        classDoc.name(),
        classDoc.commentText(),
        extractAnnotation(annotation, ClassMethodAnnotations),
        (for (md <- classDoc.methods; an <- md.annotations if matchesType(an, RequestMapping)) yield extractMethod(md, an)).toList
      )

    def extractMethod(methodDoc: MethodDoc, annotation: AnnotationDesc): Method =
      Method(
        methodDoc.name(),
        methodDoc.commentText(),
        extractAnnotation(annotation, ClassMethodAnnotations),
        methodDoc.parameters.flatMap(
          pd => pd.annotations.filter(
            an => matchesType(an, PathVariable) || matchesType(an, RequestParam)).map(
            fa => extractParameter(pd, methodDoc.tags.find((tag) => tag.kind == "@param" && tag.asInstanceOf[ParamTag].parameterName == pd.name).map(_.asInstanceOf[ParamTag].parameterComment) getOrElse "", fa))
        ).toList
      )

    def extractParameter(parameter: Parameter, comment: String, annotation: AnnotationDesc): MethodParameter =
      MethodParameter(
        parameter.name(),
        comment,
        extractAnnotation(annotation, ParamAnnotations.get(annotation.annotationType.name).get)
      )

    def extractAnnotation(annotation: AnnotationDesc, members: List[String]): Annotation =
      Annotation(
        annotation.annotationType.name,
        (for (member <- members; el = getElementValue(annotation, member) if el != None) yield (el.get.element.name, el.get.value.toString)).toMap
      )

    def getElementValue(annotation: AnnotationDesc, key: String) = annotation.elementValues() find (_.element.name == key)

    root.classes.flatMap(cd => cd.annotations.foldLeft(List[Class]()) {
      (cls, an) => if (matchesType(an, RequestMapping)) extractClass(cd, an) :: cls else cls
    }).toList
  }
}

