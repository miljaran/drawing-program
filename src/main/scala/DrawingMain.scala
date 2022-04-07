import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{DialogEvent, Menu, MenuBar, MenuItem, Tab, TabPane, TextInputDialog, ToggleButton, ToggleGroup}
import scalafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, RowConstraints}
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Cyan, DarkBlue, DarkViolet, FireBrick, Green, GreenYellow, Grey, Lime, Orange, Pink, Red, SaddleBrown, White, Yellow}

import drawing._

object DrawingMain extends JFXApp {

  private var canvasMap: Map[Tab, Canvas] = Map()
  private var drawingMap: Map[Tab, Drawing] = Map()

  private var currentColor = Red
  private var currentShape = "line"

  // Function to add new tabs and canvases
  private def makeDrawingTab(name: String): (Drawing, Tab, Canvas) = {
    val canvas = new Canvas(600, 580)
    val gc = canvas.graphicsContext2D
    val drawing = new Drawing(gc, canvas.getWidth, canvas.getHeight)

    val tab = new Tab
    tab.text = name
    tab.content = canvas
    (drawing, tab, canvas)
  }

  // Function to convert color rgb strings to hex strings
  private def toHexString(c: Color) = {
    val r = Math.round(c.getRed * 255).asInstanceOf[Int] << 24
    val g = Math.round(c.getGreen * 255).asInstanceOf[Int] << 16
    val b = Math.round(c.getBlue * 255).asInstanceOf[Int] << 8
    val a = Math.round(c.getOpacity * 255).asInstanceOf[Int]
    String.format("#%08X", r + g + b + a)
  }

  def updateColor() = {
    try {
      val button = colors.selectedToggle().asInstanceOf[javafx.scene.control.ToggleButton]
      currentColor = colorMap(button)
    } catch {
      case e: NoSuchElementException => println("no change")
    }
  }

  // Create file menu
  val newItem = new MenuItem("New")
  val openItem = new MenuItem("Open")
  val saveItem = new MenuItem("Save")
  val fileMenu = new Menu("File")
  fileMenu.items = List(newItem, openItem, saveItem)

  // Create edit menu
  val undo = new MenuItem("Undo")
  val clear = new MenuItem("Clear")
  val editMenu = new Menu("Edit")
  editMenu.items = List(undo, clear)

  // Add menus to the menu bar
  val menuBar = new MenuBar
  menuBar.menus = List(fileMenu, editMenu)

  // Create toolbox and divide it into columns and rows
  val toolbox = new GridPane
  toolbox.setVgap(5)
  toolbox.setHgap(5)
  toolbox.setPadding(Insets(10, 10, 10, 10))
  toolbox.setStyle("-fx-background-color: #cecace; -fx-grid-lines-visible: false")

  val columnConstraints = Array.ofDim[ColumnConstraints](4)
  for (i <- 0 to 3) {
    columnConstraints(i) = new ColumnConstraints(50)
  }
  toolbox.columnConstraints = columnConstraints

  val rowConstraints = Array.ofDim[RowConstraints](6)
  for (i <- 0 to 5) {
    rowConstraints(i) = new RowConstraints(50)
  }
  toolbox.rowConstraints = rowConstraints

  // Create buttons for colors
  var colors = new ToggleGroup
  val colorButtons = Array.ofDim[ToggleButton](16)
  for (i <- 0 to 15) {
    colorButtons(i) = new ToggleButton
  }
  val colorArr = Array[Color](Red, Blue, Yellow, Orange, Lime, DarkViolet, White, Black, Grey, SaddleBrown, FireBrick, Pink, DarkBlue, Cyan, Green, GreenYellow)
  val colorMap = colorButtons.zip(colorArr).toMap

  // Set color button colors, sizes and toggle group
  for (i <- 0 to 15) {
    val hex = toHexString(colorArr(i))
    colorButtons(i).setStyle(s"-fx-background-color: $hex")
    colorButtons(i).setPrefSize(40, 40)
    colorButtons(i).setToggleGroup(colors)
  }

  // Add color buttons to the toolbox
  var m = 0
  for (i <- 0 to 3; j <- 0 to 3) {
    toolbox.add(colorButtons(m), i, j)
    m += 1
  }

  colorButtons(0).setSelected(true)

  // Create buttons for shapes
  var shapes = new ToggleGroup // TODO: change to work the same way as color buttons
  val line = new ToggleButton("Line")
  val rectangle = new ToggleButton("Rectangle")
  val ellipse = new ToggleButton("Ellipse")
  val circle = new ToggleButton("Circle")

  // Set shape buttons to the same toggle group
  line.setToggleGroup(shapes)
  rectangle.setToggleGroup(shapes)
  ellipse.setToggleGroup(shapes)
  circle.setToggleGroup(shapes)

  line.setSelected(true)

  // Add shape buttons to the toolbox
  toolbox.add(line, 0, 4, 2, 1)
  toolbox.add(rectangle, 2, 4, 2, 1)
  toolbox.add(ellipse, 0, 5, 2, 1)
  toolbox.add(circle, 2, 5, 2, 1)

  // Create first canvas
  val tabPane = new TabPane
  val (firstDrawing, firstTab, firstCanvas) = makeDrawingTab("Untitled")
  canvasMap += firstTab -> firstCanvas
  drawingMap += firstTab -> firstDrawing
  tabPane += firstTab
  var currentCanvas = firstCanvas
  var currentDrawing = firstDrawing

  // Create the window
  stage = new PrimaryStage {
    title = "Drawing program"
    scene = new Scene(800, 600) {
      root = new BorderPane {
        top = menuBar
        center = toolbox
        right = tabPane
      }
    }
  }

  // Event to add new tabs and canvases
  newItem.onAction = (ae: ActionEvent) => {
    val (newDrawing, newTab, newCanvas) = makeDrawingTab("Untitled")
    canvasMap += newTab -> newCanvas
    drawingMap += newTab -> newDrawing
    tabPane += newTab
  }

  // Event to change the canvas and drawing when the tab is changed
  tabPane.getSelectionModel.selectedItemProperty.onChange {
    val tab = tabPane.getSelectionModel.getSelectedItem
    currentCanvas = canvasMap(tab)
    currentDrawing = drawingMap(tab)
    updateColor()
  }

  // Event to save drawings into a file
  saveItem.onAction = (ae: ActionEvent) => {
    val field = new TextInputDialog("Untitled")
    field.setHeaderText("Give the file name:")
    field.show()

    field.onCloseRequest = (de: DialogEvent) => {
      val input: Option[String] = Option(field.result.value)
      input match {
        case Some(name) => currentDrawing.saveFile(name)
        case None => field.close()
      }
    }
  }

  // Event to load drawing from a file
  openItem.onAction = (ae: ActionEvent) => { // TODO: when the file is faulty dont open new tab
    val field = new TextInputDialog("")
    field.setHeaderText("Give the file name you want to read from:")
    field.show()

    field.onCloseRequest = (de: DialogEvent) => {
      val input: Option[String] = Option(field.result.value)
      input match {
        case Some(name) => {
          val (newDrawing, newTab, newCanvas) = makeDrawingTab(name)
          canvasMap += newTab -> newCanvas
          drawingMap += newTab -> newDrawing
          tabPane += newTab
          newDrawing.readFile(name)
        }
        case None => field.close()
      }
    }
  }

  clear.onAction = (ae: ActionEvent) => {
    currentDrawing.empty()
  }

  undo.onAction = (ae: ActionEvent) => {
    currentDrawing.undo()
  }

  // Event to change color
  colors.selectedToggle.onChange {
    updateColor()
  }

  // Event to change shape
  /*shapes.selectedToggle.onChange {
    if (line.isSelected) {
      currentDrawing.changeShape("line")
    } else if (rectangle.isSelected) {
      currentDrawing.changeShape("rectangle")
    } else if (ellipse.isSelected) {
      currentDrawing.changeShape("ellipse")
    } else if (circle.isSelected) {
      currentDrawing.changeShape("circle")
    }
  }*/

  // Events to draw when dragging the mouse
  private var x_start = 0.0
  private var y_start = 0.0

  tabPane.onMousePressed = (me: MouseEvent) => {
    currentCanvas.onMousePressed = (event: MouseEvent) => {
      x_start = event.x
      y_start = event.y
      currentDrawing.startNewShape(x_start, y_start, currentColor, currentShape)
    }
  }

  tabPane.onMouseDragged = (me: MouseEvent) => {
    currentCanvas.onMouseDragged = (event: MouseEvent) => {
      currentDrawing.updateShape(x_start, y_start, event.x, event.y, currentColor, currentShape)
    }
  }
}