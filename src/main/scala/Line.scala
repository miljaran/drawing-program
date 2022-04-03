import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

import scala.math._

class Line(x_s: Double, y_s: Double, x_e: Double, y_e: Double, w: Double, h: Double, color: Color) extends Shape {

  // Make sure no points are outside of the canvas
  val x_start = max(0, x_s)
  val y_start = max(0, y_s)
  val x_end = min(w, x_e)
  val y_end = min(h, y_e)

  // Draw line
  def draw(gc: GraphicsContext) = {
    gc.setStroke(color)
    gc.strokeLine(x_start, y_start, x_end, y_end)
  }

}
