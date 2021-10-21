package Kanban.GUI

import Kanban._
import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.SnapshotParameters
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input._
import scalafx.scene.layout._
import scalafx.scene.paint.Color.{Beige, Black, DarkGreen, DarkOliveGreen, Gray, LightCoral, LightCyan, LightGoldrenrodYellow, LightYellow, Wheat, WhiteSmoke}
import scalafx.scene.text.{Font, FontWeight}

import java.io.FileInputStream

 class UICard (card_ : Kanban.Card) extends VBox {

  // Initialize
   var card = this.card_

   var cardBox= this

   var datePickerCard = new UIDatePicker(this.card)

   var editCardDialog = new UIEditCardDialog(this.card)

  var inBoard = card.inBoard

  var inBoardUI = new UIBoard(inBoard)

  var status = new VBox() // A to-do , in-progress or done box

  def translateTaskStatus (taskStatus: TaskStatus):VBox={
     if ( card.taskStatus == toDo) this.inBoardUI.toDoBox
     else if (card.taskStatus == inProgress) this.inBoardUI.inProgressBox
     else this.inBoardUI.doneBox
   }

   // Components
    var cardNameLabel = new Label()
    var cardDescriptionLabel = new Label()
    var cardTagLabel = new Label()

   cardNameLabel.setFont(Font.font("Times New ROman", FontWeight.SemiBold, 14))
   cardDescriptionLabel.setFont(Font.font("Times New ROman", FontWeight.SemiBold, 14))
   cardTagLabel.setFont(Font.font("Times New ROman", FontWeight.SemiBold, 14))

   cardNameLabel.setTextFill(DarkOliveGreen)
   cardDescriptionLabel.setTextFill(DarkOliveGreen)
   cardTagLabel.setTextFill(DarkOliveGreen)


   val cardNameBox = new HBox()
    cardNameBox.getChildren.add(cardNameLabel)
    cardNameBox.background = new Background(Array(new BackgroundFill((LightYellow), CornerRadii.Empty, Insets.Empty)))
    cardNameBox.maxWidth = Double.MaxValue
    cardNameBox.setPrefHeight(30)

    val cardTagBox = new HBox()
    cardTagBox.getChildren.add(cardTagLabel)
    cardTagBox.background = new Background(Array(new BackgroundFill((LightGoldrenrodYellow), CornerRadii.Empty, Insets.Empty)))
    cardTagBox.maxWidth = Double.MaxValue
    cardTagBox.setPrefHeight(30)

    val cardDescripBox = new HBox()
    cardDescripBox.getChildren().add(cardDescriptionLabel)
    cardDescripBox.background = new Background(Array(new BackgroundFill((Beige), CornerRadii.Empty, Insets.Empty)))
    cardDescripBox.maxWidth = Double.MaxValue
    cardDescripBox.setPrefHeight(40)

    cardBox.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)))
    cardBox.maxWidth = Double.MaxValue

   // Edit Card
    val editIcon =new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconEdit.png")))
    val editButton = new Button()
    editButton.setGraphic(editIcon)
    editIcon.setFitHeight(10)
    editIcon.setFitWidth(10)
    cardNameBox.getChildren().add(editButton)
    editButton.setOnMouseEntered( (event) => editButton.setStyle("-fx-background-color: #CCFF99"))
    editButton.setOnMouseExited( (event => editButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")))
    editButton.onAction = (event) => editCard()

   // DatePicker
    val timeIcon =new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconTime.png")))
    val timeButton = new Button()
    timeButton.setGraphic(timeIcon)
    timeIcon.setFitHeight(10)
    timeIcon.setFitWidth(10)
    timeButton.setPrefSize(10,10)
    timeButton.setOnMouseEntered( (event) => timeButton.setStyle("-fx-background-color: #CCFF99"))
    timeButton.setOnMouseExited( (event => timeButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")))
    timeButton.onAction = (event) => datePickerCard.show()

  // Delete Card
    val deleteIcon = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconDelete.png")))
    val deleteButton = new Button()
    deleteIcon.setFitHeight(10)
    deleteIcon.setFitWidth(10)
    deleteButton.setGraphic(deleteIcon)
    deleteButton.setOnMouseEntered( (event) => deleteButton.setStyle("-fx-background-color: #CCFF99"))
    deleteButton.setOnMouseExited( (event => deleteButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")))
    deleteButton.onAction = (event)=> {
      val alert = new Alert(AlertType.Confirmation) {
        title = "Confirmation Dialog"
        headerText = "Look, a Confirmation Dialog."
        contentText = "Do you want to delete this card?"
      }

      val result = alert.showAndWait()
      result match {
        case Some(ButtonType.OK) => this.inBoardUI.deleteCard(this)

        case _ => alert.close()
      }
    }

   // Return card
   val returnIcon =new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconReturn.png")))
   val returnButton = new Button()
   returnButton.setGraphic(returnIcon)
   returnIcon.setFitHeight(10)
   returnIcon.setFitWidth(10)
   returnButton.setOnMouseEntered( (event) => returnButton.setStyle("-fx-background-color: #CCFF99"))
   returnButton.setOnMouseExited( (event => returnButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")))
   returnButton.onAction = (event) => MainWindow.returnCard(this)
   returnButton.setVisible(false)

   // Archive card
   val archiveIcon =new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconArchive.png")))
   archiveIcon.setFitHeight(10)
   archiveIcon.setFitWidth(10)
   val archiveButton = new Button ()
   archiveButton.setGraphic(archiveIcon)
   archiveButton.setOnMouseEntered( (event) => archiveButton.setStyle("-fx-background-color: #CCFF99"))
   archiveButton.setOnMouseExited( (event => archiveButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")))
   archiveButton.onAction = (event) => {
     GUI.MainWindow.archiveCard(this)
     this.inBoard.archiveCard(this.card)
   }

   // Icon box
   var iconBox = new HBox()
   iconBox.getChildren().addAll(editButton, deleteButton,timeButton,archiveButton,returnButton)
   iconBox.background = new Background(Array(new BackgroundFill((Wheat), CornerRadii.Empty, Insets.Empty)))
   cardBox.getChildren().addAll(iconBox,cardNameBox,cardTagBox,cardDescripBox)

   editButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
   deleteButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
   timeButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
   returnButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
   archiveButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")

   // Functions
   def editCard ()= {
     editCardDialog.showAndWait()
     val nameEdit = editCardDialog.cardname.getText()
     val tagEdit = editCardDialog.cardTag.getText()
     val descriptionEdit = editCardDialog.cardDescription.getText()
     editCardDialog.okButton match {
       case bt: javafx.scene.control.Button =>  {
         if (editCardDialog.cardDescription.getText.isEmpty && editCardDialog.cardname.getText.isEmpty && editCardDialog.cardTag.getText.isEmpty)
           { editCardDialog.close()
           } else {
           this.setTextCard(nameEdit, tagEdit, descriptionEdit)
         }
       }
      case _ => editCardDialog.close()
     }
   }


   def setTextCard ( name:String, tag:String, description: String) ={
     this.card.setTextCard(name,tag,description)
     this.cardNameLabel.setText(name)
     this.cardTagLabel.setText(tag)
     this.cardDescriptionLabel.setText(description)
  }


   def handleDragDetected (event:MouseEvent) ={
     var dragBoard = startDragAndDrop(TransferMode.Any:_*)
     var clip = new ClipboardContent()
     clip.putString(this.card.getId)
     dragBoard.setContent(clip)
     this.setScaleX(0.9)
     this.setScaleY(0.9)
     var snapShotParameters = new SnapshotParameters()
     dragBoard.setDragView(this.snapshot(snapShotParameters, null), event.getX(), event.getY())
     event.consume()
  }
   this.onDragDetected =  (event:MouseEvent) => handleDragDetected(event)

  def handleDragDone ( event: DragEvent)={
      this.setScaleX(1.0)
      this.setScaleY(1.0)
  }
  this.onDragDone = (event) => handleDragDone(event)


 }
