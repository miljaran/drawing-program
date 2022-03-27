import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Menu, MenuBar, MenuItem, Tab, TabPane}
import scalafx.scene.layout.{BorderPane, Pane}

object DrawingMain extends JFXApp {

  private var canvasMap: Map[Tab, Canvas] = Map()
  private var drawingMap: Map[Tab, Drawing] = Map()

  // Function to add new tabs and canvases
  private def makeDrawingTab(): (Drawing, Tab, Canvas) = {
    val canvas = new Canvas(600, 580)
    val gc = canvas.graphicsContext2D
    val drawing = new Drawing(gc)

    val tab = new Tab
    tab.text = "Untitled"
    tab.content = canvas
    (drawing, tab, canvas)
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

  // Create first canvas
  val tabPane = new TabPane
  val (firstDrawing, firstTab, firstCanvas) = makeDrawingTab()
  canvasMap += firstTab -> firstCanvas
  drawingMap += firstTab -> firstDrawing
  tabPane += firstTab

  // Create the window
  stage = new PrimaryStage {
    title = "Drawing program"
    scene = new Scene(800, 600) {
      root = new BorderPane {
        top = menuBar
        // center = toolbox
        right = tabPane
      }
    }
  }

}