// Name of the project
name := "Drawing program"

// Project version
version := "0.1"

// Version of Scala used in the project
scalaVersion := "2.13.8"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m =>
  "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)