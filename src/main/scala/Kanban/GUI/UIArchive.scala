package Kanban.GUI

import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color.{Black, FloralWhite, MediumSpringGreen}
import scalafx.scene.text.{Font, FontWeight}

object UIArchive extends GridPane {

  var root = this

  // Column + row constraints
  val column0 = new ColumnConstraints
  val column1 = new ColumnConstraints
  val column2 = new ColumnConstraints
  val row0 = new RowConstraints
  val row1 = new RowConstraints

  column0.percentWidth = 33.3
  column1.percentWidth = 33.3
  column2.percentWidth = 33.3
  row0.percentHeight = 100
  row1.percentHeight = 0

  root.columnConstraints = Array[ColumnConstraints](column0, column1, column2)
  root.rowConstraints = Array[RowConstraints](row0, row1)

  // 3 columns
  val archiveBox1 = new VBox()
  val archiveBox2 = new VBox()
  val archiveBox3 = new VBox()

  archiveBox1.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))
  archiveBox2.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))
  archiveBox3.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))

  archiveBox1.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)))
  archiveBox2.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)))
  archiveBox3.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)));

  root.add(archiveBox1, 0, 0, 1, 2)
  root.add(archiveBox2, 1, 0, 1, 2)
  root.add(archiveBox3, 2, 0)
  root.setHgap(20)

  // Label
  val archiveLabel1 = new Label(" Archive 1")
  val archiveLabel2 = new Label(" Archive 2")
  val archiveLabel3 = new Label(" Archive 3")

  archiveLabel1.setFont(Font.font("System", FontWeight.Bold, 20))
  archiveLabel2.setFont(Font.font("System", FontWeight.Bold, 20))
  archiveLabel3.setFont(Font.font("System", FontWeight.Bold, 20))

  val archiveTitle1 = new HBox(archiveLabel1)
  val archiveTitle2 = new HBox(archiveLabel2)
  val archiveTitle3 = new HBox(archiveLabel3)

  archiveTitle1.setAlignment(Pos.CenterLeft)
  archiveTitle2.setAlignment(Pos.CenterLeft)
  archiveTitle3.setAlignment(Pos.CenterLeft)

  archiveTitle1.background = new Background(Array(new BackgroundFill((MediumSpringGreen), CornerRadii.Empty, Insets.Empty)))
  archiveTitle2.background = new Background(Array(new BackgroundFill((MediumSpringGreen), CornerRadii.Empty, Insets.Empty)))
  archiveTitle3.background = new Background(Array(new BackgroundFill((MediumSpringGreen), CornerRadii.Empty, Insets.Empty)))

  archiveBox1.children = archiveTitle1
  archiveBox2.children = archiveTitle2
  archiveBox3.children = archiveTitle3

}
