package drawing

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

import scala.math._

class Circle(x_s: Double, y_s: Double, x_e: Double, y_e: Double, w: Double, h: Double, color: Color) extends Shape {

  def draw(gc: GraphicsContext) = {
    gc.setStroke(color)

    var width = abs(x_s - x_e)
    var height = abs(y_s - y_e)

    val radius = sqrt(width * width + height * height)

    val x = x_s - radius
    val y = y_s - radius

    gc.strokeOval(x, y, radius * 2, radius * 2)
  }

  override def toString = {
    val colorStr = colorToString(color)
    s"C, $colorStr, $x_s, $y_s, $x_e, $y_e"
  }
}
