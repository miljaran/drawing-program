package drawing

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Cyan, DarkBlue, DarkViolet, FireBrick, Green, GreenYellow, Grey, Lime, Orange, Pink, Red, SaddleBrown, White, Yellow}

import java.io._
import scala.collection.mutable.Buffer

class Drawing(gc: GraphicsContext, width: Double, height: Double) {

  private val shapes = Buffer[Shape]()

  def strToColor(str: String): Color = {
    str match {
      case "red" => Red
      case "blu" => Blue
      case "yel" => Yellow
      case "ora" => Orange
      case "grn" => Lime
      case "pur" => DarkViolet
      case "whi" => White
      case "bla" => Black
      case "gre" => Grey
      case "bro" => SaddleBrown
      case "dre" => FireBrick
      case "lre" => Pink
      case "dbl" => DarkBlue
      case "lbl" => Cyan
      case "dgr" => Green
      case "lgr" => GreenYellow
      case _ => Red
    }
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

  def alert(name: String, header: String, content: String) = {
    new Alert(AlertType.Error) {
      title = name
      headerText = header
      contentText = content
    }.showAndWait()
  }

  def saveFile(name: String) = {
    val file = new File(s"$name.txt")
    val writer = new BufferedWriter(new FileWriter(file))
    val text = this.toString
    writer.write(text)
    writer.close()
  }

  // Method to read data from files
  def readFile(name: String): Boolean = {
    var str = ""
    val myFileReader = try {
      new FileReader(s"$name.txt")
    } catch {
      case e: FileNotFoundException => {
        alert("Error", s"Could not find the file ${name}.txt", "Make sure you wrote the file name correctly")
        return false
      }
    }

     val lineReader = new BufferedReader(myFileReader)
     try {
        var inputLine = lineReader.readLine()
        while (inputLine != null) {
          str += s"$inputLine\n"
          inputLine = lineReader.readLine()
        }
        load(str, name)
    } catch {
      case e: IOException => println("Reading finished with error"); false
    }
  }

  // Method to convert data read from a file to a drawing
  def load(str: String, name: String): Boolean = {
    try {
      val arr = str.split("\n")
      for (a <- arr) {
        val splitted = a.split(", ")
        splitted(0) match {
          case "L" => shapes += new Line(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, strToColor(splitted(1)))
          case "R" => shapes += new Rectangle(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, strToColor(splitted(1)))
          case "E" => shapes += new Ellipse(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, strToColor(splitted(1)))
          case "C" => shapes += new Circle(splitted(2).toDouble, splitted(3).toDouble, splitted(4).toDouble, splitted(5).toDouble, strToColor(splitted(1)))
        }
      }
      draw()
      true
    } catch {
      case e: MatchError => alert("Error", s"Could not read the file ${name}.txt", "The file format was not correct"); false
      case e: IndexOutOfBoundsException => alert("Error", s"Could not read the file ${name}.txt", "The file format was not correct"); false
      case e: java.lang.NumberFormatException => alert("Error", s"Could not read the file ${name}.txt", "The file format was not correct"); false
    }
  }

  def startNewShape(x_start: Double, y_start: Double, color: Color, shape: String): Unit = {
    shape match {
      case "Line" => shapes += new Line(x_start, y_start, x_start, y_start, color)
      case "Rectangle" => shapes += new Rectangle(x_start, y_start, x_start, y_start, color)
      case "Ellipse" => shapes += new Ellipse(x_start, y_start, x_start, y_start, color)
      case "Circle" => shapes += new Circle(x_start, y_start, x_start, y_start, color)
    }
  }

  def updateShape(x_start: Double, y_start: Double, x_end: Double, y_end: Double, color: Color, shape: String): Unit = {
    if (shapes.nonEmpty) {
      shape match {
        case "Line" => shapes(shapes.length - 1) = new Line(x_start, y_start, x_end, y_end, color)
        case "Rectangle" => shapes(shapes.length - 1) = new Rectangle(x_start, y_start, x_end, y_end, color)
        case "Ellipse" => shapes(shapes.length - 1) = new Ellipse(x_start, y_start, x_end, y_end, color)
        case "Circle" => shapes(shapes.length - 1) = new Circle(x_start, y_start, x_end, y_end, color)
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
