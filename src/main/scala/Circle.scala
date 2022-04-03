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
  // Make sure no points are outside of the canvas
    val x_start = max(min(x_s, x_e), 0)
    val y_start = max(min(y_s, y_e), 0)
    val x_end = min(max(x_s, x_e), w)
    val y_end = min(max(y_s, y_e), h)
    val colorStr = colorToString(color)
    s"C, $colorStr, $x_start, $y_start, $x_end, $y_end\n"
  }
}
