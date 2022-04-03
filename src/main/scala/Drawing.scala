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

  def startNewShape(x_start: Double, y_start: Double): Unit = {
    currentShape match {
      case "line" => shapes += new Line(x_start, y_start, x_start, y_start, width, height, currentColor)
      case _ => println("todo")
    }
  }

  def updateShape(x_start: Double, y_start: Double, x_end: Double, y_end: Double): Unit = {
    if (shapes.nonEmpty) {
      currentShape match {
        case "line" => shapes(shapes.length - 1) = new Line(x_start, y_start, x_end, y_end, width, height, currentColor)
        case _ => println("todo")
      }
    }
    draw()
  }

  def draw() = {
    gc.clearRect(0, 0, width, height)
    for (shape <- shapes) {
      shape.draw(gc)
    }
  }


}
