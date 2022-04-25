package drawing

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Cyan, DarkBlue, DarkViolet, FireBrick, Green, GreenYellow, Grey, Lime, Orange, Pink, Red, SaddleBrown, White, Yellow}

trait Shape {

  def draw(gc: GraphicsContext): Unit

  def colorToStr(c: Color) = {
    c match {
      case Red => "red"
      case Blue => "blu"
      case Yellow => "yel"
      case Orange => "ora"
      case Lime => "grn"
      case DarkViolet => "pur"
      case White => "whi"
      case Black => "bla"
      case Grey => "gre"
      case SaddleBrown => "bro"
      case FireBrick => "dre"
      case Pink => "lre"
      case DarkBlue => "dbl"
      case Cyan => "lbl"
      case Green => "dgr"
      case GreenYellow => "lgr"
    }
  }
}
