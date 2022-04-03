import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

import scala.math.{abs, max, min}

class Ellipse(x_s: Double, y_s: Double, x_e: Double, y_e: Double, w: Double, h: Double, color: Color) extends Shape {

  def draw(gc: GraphicsContext) = {
    gc.setStroke(color)
    var x = x_s.min(x_e)
    var y = y_s.min(y_e)

    var width = abs(x_s - x_e)
    var height = abs(y_s - y_e)

    gc.strokeOval(x, y, width, height)
  }

  override def toString = {
  // Make sure no points are outside of the canvas
    val x_start = max(min(x_s, x_e), 0)
    val y_start = max(min(y_s, y_e), 0)
    val x_end = min(max(x_s, x_e), w)
    val y_end = min(max(y_s, y_e), h)
    val colorStr = colorToString(color)
    s"E, $colorStr, $x_start, $y_start, $x_end, $y_end\n"
  }
}
