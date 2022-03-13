import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

object DrawingMain extends JFXApp {
  stage = new PrimaryStage {
    title = "Hello world"
    scene = new Scene {
      root = new Pane
    }
  }
}