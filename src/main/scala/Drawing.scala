import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.Red

import scala.collection.mutable.Buffer

class Drawing(gc: GraphicsContext, width: Double, height: Double) {

  var currentColor: Color = Red
  var currentShape = "line"
  val shapes = Buffer[Shape]()

  def changeColor(c: Color) = {
    currentColor = c
  }

  def changeShape(s: String) = {
    currentShape = s
  }

  def undo() = {
    if (shapes.nonEmpty) {
      shapes.remove(shapes.length - 1)
      draw()
    }
  }

  def empty() = {
    shapes.clear()
    draw()
  }

  def draw() = {
    gc.clearRect(0, 0, width, height)
    for (shape <- shapes) {
      shape.draw(gc)
    }
  }

  def load(str: String) = { // TODO: notice colors and catch errors and fix circle
    val arr = str.split("\n\n")
    for (a <- arr) {
      val splitted = a.split(",")
      splitted(0) match {
        case "L" => shapes += new Line(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, width, height, currentColor)
        case "R" => shapes += new Rectangle(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, width, height, currentColor)
        case "E" => shapes += new Ellipse(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, width, height, currentColor)
        case "C" => shapes += new Circle(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, width, height, currentColor)
      }
    }
    draw()
  }

  def startNewShape(x_start: Double, y_start: Double): Unit = {
    currentShape match {
      case "line" => shapes += new Line(x_start, y_start, x_start, y_start, width, height, currentColor)
      case "rectangle" => shapes += new Rectangle(x_start, y_start, x_start, y_start, width, height, currentColor)
      case "ellipse" => shapes += new Ellipse(x_start, y_start, x_start, y_start, width, height, currentColor)
      case "circle" => shapes += new Circle(x_start, y_start, x_start, y_start, width, height, currentColor)
      case _ => println("todo")
    }
  }

  def updateShape(x_start: Double, y_start: Double, x_end: Double, y_end: Double): Unit = {
    if (shapes.nonEmpty) {
      currentShape match {
        case "line" => shapes(shapes.length - 1) = new Line(x_start, y_start, x_end, y_end, width, height, currentColor)
        case "rectangle" => shapes(shapes.length - 1) = new Rectangle(x_start, y_start, x_end, y_end, width, height, currentColor)
        case "ellipse" => shapes(shapes.length - 1) = new Ellipse(x_start, y_start, x_end, y_end, width, height, currentColor)
        case "circle" => shapes(shapes.length - 1) = new Circle(x_start, y_start, x_end, y_end, width, height, currentColor)
      }
    }
    draw()
  }

  override def toString = {
    var text = ""
    for (shape <- shapes) {
      text += s"$shape\n"
    }
    text
  }
}
