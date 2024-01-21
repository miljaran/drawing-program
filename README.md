# Description
This is a drawing program where the user can draw on canvas when the mouse is pressed down. The user is able to choose a color and a shape they want to draw. The available shapes are line, rectangle, circle and ellipse. In addition to drawing, the user can either undo the last changes or clear the entire painting. It’s also possible to save drawings into a file and load drawings from files.

# Running the program
Running the program requires an installed IntelliJ or sbt. A new sbt project should be created and then the launch file DrawingMain can be run.

# How to use
When the program is running, the user can start drawing by clicking the canvas and then dragging the mouse around. The shape drawn is visible all the time and the user can finish the shape by releasing the mouse. The shape and its color can be selected from the buttons on the left side of the canvas. The default shape is line and default color is red.

The user can create new tabs from the “New drawing” button in the file menu. Then a new tab containing an empty canvas is added. The user can switch between tabs easily on the panel above the canvas. Other tabs than the first one can also be deleted. When a tab is selected it has a small cross which deletes the tab and drawing entirely.

The drawings can be saved into and read from files from the file menu. Button “Save into a file” opens a popup that asks a name for the file. The user can enter any name and the drawing is saved in a file called “{name}.txt”. If a file with the same name already exists, it is replaced by the new drawing. If the user does not give any name, the drawing is saved into a file called “Untitled.txt”.

Drawings can be read from files from the file menus “Open from a file” button. Then the user is asked to write the name of the file they want to read from (without the .txt suffix) and if it can be read correctly, a new drawing is opened. If the file is not found or it’s not in the correct format, an error message is shown.

Undoing the last edit and emptying the drawing are done from the “Undo last shape” and “Clear drawing” buttons in the edit menu. They undo or clear the changes in the currently shown drawing. 

# Format of the files
The program saves drawings into text-based files and it can read drawings from them. Every shape’s information is on its own line and the different parameters are separated with a comma. The line format is `{shapeCode}, {colorCode}, {x_coord_start}, {y_coord_start}, {x_coord_end}, {y_coord_end}`. The shapeCode describes which shape is in question and it’s L for lines, E for ellipses, R for rectangles and C for circles. The colorCode describes the color with the following codes: red, blu, yel, ora, grn, pur, whi, bla, gre, bro, dre, lre, dbl, lbl, dgr and lgr. The coordinates describe the shape’s start and end coordinates in Double.

# Screenshots of the program
<img width="799" alt="screenshot1" src="https://github.com/miljaran/drawing-program/assets/83217566/d0e2ff92-2ae9-49c5-aeb0-1043bf067fe7"><br>
*Drawing different shapes in different colors*<br><br>
<img width="801" alt="screenshot2" src="https://github.com/miljaran/drawing-program/assets/83217566/955623d7-4b19-4373-9eb3-49f3ad83eac2"><br>
*Saving the drawing into a file*<br><br>
<img width="798" alt="screenshot3" src="https://github.com/miljaran/drawing-program/assets/83217566/367104e8-d913-4876-8f71-b306b8bc7485"><br>
*Error message due to nonexistent file*
