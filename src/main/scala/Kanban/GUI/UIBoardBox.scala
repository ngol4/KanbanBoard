package Kanban.GUI

import javafx.scene.image.ImageView
import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.image.Image
import scalafx.scene.layout.{Background, _}
import scalafx.scene.paint.Color._
import scalafx.scene.text.{Font, FontWeight}

import java.io.FileInputStream

class UIBoardBox (boardUI: UIBoard) extends HBox {

  val boardBox = this

  var UIBoard = this.boardUI

  boardUI.boardBox = this

  // Delete
  val deleteIcon = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconDelete.png")))
  val deleteButton = new Button()
  deleteButton.setGraphic(deleteIcon)
  deleteButton.setPrefSize(10, 10)
  deleteButton.setStyle(("-fx-background-color: rgba(255, 255, 255, 0);"))
  deleteButton.onAction = (event) => {
    // Create and show confirmation alert whether a person wants to delete the card.
    val alert = new Alert(AlertType.Confirmation) {
      title = "Confirmation Dialog"
      headerText = "Look, a Confirmation Dialog."
      contentText = "Do you want to delete this board?"
    }

    val result = alert.showAndWait()
    result match {
      case Some(ButtonType.OK) => {
        MainWindow.deleteBoard(this.boardUI)
        MainWindow.mainRoot.boardDisplay = MainWindow.mainRoot.blankDisplay
        MainWindow.updateBoard()
      }
      case _ => alert.close()
    }
  }

  // Edit name icon
  val editIcon = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconEdit.png")))
  val editButton = new Button()
  editButton.setGraphic(editIcon)
  editButton.setPrefSize(10, 10)
  editButton.setStyle(("-fx-background-color: rgba(255, 255, 255, 0);"))
  editButton.onAction = (event) => {
    var boardDialog = new BoardDialog()
    boardDialog.title = ("Edit Board Dialog")
    boardDialog.headerText = "Let's edit a board"
    boardDialog.showAndWait()
    boardDialog.okButton match {
      case bt: javafx.scene.control.Button => {
        if (boardDialog.boardname.getText.isEmpty) boardDialog.close()
        else {
          var boardName = boardDialog.boardname.getText()
          boardUI.board.nameBoard = boardName
          this.boardButton.setText(boardName)
        }
      }
      case _ => boardDialog.close()
      }
  }


  // Board Button
  val boardButton = new Button()
  boardButton.setStyle(("-fx-background-color: rgba(255, 255, 255, 0);"))
  boardButton.setFont(Font.font("Times New Roman", FontWeight.Bold,14))
  boardButton.setTextFill(RosyBrown)
  boardButton.setOnMouseClicked( (event) => this.background = new Background(Array(new BackgroundFill((LightGray), CornerRadii.Empty, Insets.Empty))))
  boardButton.setOnMouseEntered((event => this.background = new Background(Array(new BackgroundFill((Gray), CornerRadii.Empty, Insets.Empty)))))
  boardButton.setOnMouseExited( (event) => this.background = new Background(Array(new BackgroundFill((LightCoral), CornerRadii.Empty, Insets.Empty))))
  boardButton.onAction = (event) => {
    MainWindow.mainRoot.getChildren().remove(MainWindow.mainRoot.boardDisplay)
    MainWindow.mainRoot.boardDisplay = UIBoard
    MainWindow.updateBoard()
  }

  boardBox.getChildren().addAll(deleteButton, editButton, boardButton)
  boardBox.background = new Background(Array(new BackgroundFill((LemonChiffon), CornerRadii.Empty, Insets.Empty)))
  boardButton.setOnMouseEntered((event => this.background = new Background(Array(new BackgroundFill((LightCoral), CornerRadii.Empty, Insets.Empty)))))
  boardButton.setOnMouseExited( (event) => this.background = new Background(Array(new BackgroundFill((LemonChiffon), CornerRadii.Empty, Insets.Empty))))
  boardBox.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)));

}
