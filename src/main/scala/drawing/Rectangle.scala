package drawing

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

import scala.math._

class Rectangle(x_s: Double, y_s: Double, x_e: Double, y_e: Double, w: Double, h: Double, color: Color) extends Shape {

  def draw(gc: GraphicsContext) = {
    gc.setStroke(color)
    var x = x_s.min(x_e)
    var y = y_s.min(y_e)

    var width = abs(x_s - x_e)
    var height = abs(y_s - y_e)

    gc.strokeRect(x, y, width, height)
  }

  override def toString = {
    val colorStr = colorToString(color)
    s"R, $colorStr, $x_s, $y_s, $x_e, $y_e"
  }
}
