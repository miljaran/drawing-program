package drawing

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Line(x_s: Double, y_s: Double, x_e: Double, y_e: Double, color: Color) extends Shape {

  def draw(gc: GraphicsContext) = {
    gc.setStroke(color)
    gc.strokeLine(x_s, y_s, x_e, y_e)
  }

  override def toString = {
    val colorStr = colorToStr(color)
    s"L, $colorStr, $x_s, $y_s, $x_e, $y_e"
  }

}
