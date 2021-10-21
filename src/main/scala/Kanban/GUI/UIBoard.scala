package Kanban.GUI

import Kanban._
import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control._
import scalafx.scene.input.{DragEvent, _}
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.text.{Font, FontWeight}

import scala.collection.mutable.Buffer

class UIBoard (board_ : Board) extends GridPane {
  var UIBoard = this

  val root = this

  var board = this.board_

  var cardList = board.cardList

  var cardListUI = Buffer[UICard]()

  var boardBox = new HBox()

  // 3 columns
  val toDoBox = new VBox()
  val inProgressBox = new VBox()
  val doneBox = new VBox()

  toDoBox.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))
  inProgressBox.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))
  doneBox.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))

  toDoBox.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)))
  inProgressBox.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)))
  doneBox.setBorder(new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, CornerRadii.Empty, BorderWidths.Default)));

  root.add(toDoBox, 0, 0, 1, 2)
  root.add(inProgressBox, 1, 0, 1, 2)
  root.add(doneBox, 2, 0)
  root.setHgap(20)

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

  root.columnConstraints = Array[ColumnConstraints](column0, column1, column2) //Add constraints in order
  root.rowConstraints = Array[RowConstraints](row0, row1)

  // Label
  val toDoLabel = new Label(" To-do")
  val inProgressLabel = new Label("In Progress")
  val doneLabel = new Label("Done")

  toDoLabel.setFont(Font.font("System", FontWeight.Bold,18))
  inProgressLabel.setFont(Font.font("System", FontWeight.Bold,18))
  doneLabel.setFont(Font.font("System", FontWeight.Bold,18))

  var buttonAdd1 = new Button("+")
  var buttonAdd2 = new Button("+")
  var buttonAdd3 = new Button("+")

  buttonAdd1.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
  buttonAdd2.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
  buttonAdd3.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");

  buttonAdd1.setFont(Font.font("Arial", FontWeight.Bold,18))
  buttonAdd2.setFont(Font.font("Arial", FontWeight.Bold,18))
  buttonAdd3.setFont(Font.font("Arial", FontWeight.Bold,18))

  buttonAdd1.onAction = (event) => createCard()
  buttonAdd2.onAction = (event) => createCard()
  buttonAdd3.onAction = (event) => createCard()

  val toDoTitle = new HBox(buttonAdd1, toDoLabel)
  val progressTitle = new HBox(buttonAdd2,inProgressLabel)
  val doneTitle = new HBox(buttonAdd3,doneLabel)

  toDoTitle.setAlignment(Pos.CenterLeft)
  progressTitle.setAlignment(Pos.CenterLeft)
  doneTitle.setAlignment(Pos.CenterLeft)

  toDoTitle.background = new Background(Array(new BackgroundFill((MediumSpringGreen), CornerRadii.Empty, Insets.Empty)))
  progressTitle.background = new Background(Array(new BackgroundFill((MediumSpringGreen), CornerRadii.Empty, Insets.Empty)))
  doneTitle.background = new Background(Array(new BackgroundFill((MediumSpringGreen), CornerRadii.Empty, Insets.Empty)))

  toDoTitle.setSpacing(5)
  progressTitle.setSpacing(5)
  doneTitle.setSpacing(5)

  toDoBox.children = toDoTitle
  inProgressBox.children = progressTitle
  doneBox.children = doneTitle

  // FUnction

  def createCard() = {

    var cardDialog = new CardDialog()
    cardDialog.show()

    cardDialog.okButton match {

      case bt: javafx.scene.control.Button => bt.onAction = (event) => {
        val name = cardDialog.cardname.getText()
        val tag = cardDialog.cardTag.getText()
        val text = cardDialog.cardDescription.getText()
        var status = if (cardDialog.toDoBtn.isSelected) toDoBox
        else if (cardDialog.inProgressBtn.isSelected) inProgressBox else doneBox

        var taskStatus = translateToTaskStatus(status)
        var card = new Card(name, tag, text, taskStatus)
        this.board.addCard(card)
        this.addCard(card)
      }

      case _ => cardDialog.close()
    }
  }

  def addCard (card: Card):Unit={
    var cardUI = new UICard(card)
    cardUI.inBoardUI = this
    cardUI.status = cardUI.translateTaskStatus(card.taskStatus)
    this.cardListUI += cardUI
    cardUI.setTextCard(card.name, card.tag, card.text)
    cardUI.status.getChildren().add(cardUI)
    cardUI.datePickerCard.dateDeadLine = card.dateDeadLine
    cardUI.datePickerCard.setDeadline()
  }

  def deleteCard(cardUI:UICard) : Unit= {
    if (!cardUI.card.isArchived) {
      this.cardListUI -= cardUI
      this.board.removeCard(cardUI.card)
      cardUI.status.getChildren().remove(cardUI)
    } else MainWindow.deleteArchiveCard(cardUI)
  }




  def moveColumn (newStatus:TaskStatus, cardUI:UICard) :Unit={
    cardUI.card.taskStatus = newStatus
    cardUI.status = cardUI.translateTaskStatus(cardUI.card.taskStatus)
    cardUI.status.getChildren().add(cardUI)
  }

  def translateToTaskStatus (status: VBox):TaskStatus={
    if (status == toDoBox) toDo
    else if (status == inProgressBox) inProgress
    else done
  }


  def handleDragOver (event: DragEvent)={
    if ( event.getGestureSource() != this && event.getDragboard().hasString() )
    { event.acceptTransferModes(TransferMode.Move) }
    event.consume()
  }

  toDoBox.onDragOver = (event) => handleDragOver(event)
  inProgressBox.onDragOver = (event) => handleDragOver(event)
  doneBox.onDragOver = (event) => handleDragOver(event)


  def searchByID (id : String) = {
    MainWindow.UICardListALl.find(cardUI => cardUI.card.getId.toLowerCase.trim == id.toLowerCase.trim)
  }


  toDoBox.onDragDropped = (event)=>{
  var success = false
  if (event.getDragboard.hasString) {
    val cardId = event.getDragboard.getString
    var newTaskStatus = toDo
    if (searchByID(cardId).isDefined) {
      val droppedCard = searchByID(cardId).get
      if (droppedCard.card.taskStatus != toDo) {
        moveColumn(newTaskStatus, droppedCard)
      }
    }
  }
  event.setDropCompleted(true)
  event.consume()
}

  inProgressBox.onDragDropped = (event)=>{
    var success = false
    if (event.getDragboard.hasString) {
      val cardId = event.getDragboard.getString
      var newTaskStatus = inProgress
      if (searchByID(cardId).isDefined) {
        val droppedCard = searchByID(cardId).get
        if (droppedCard.card.taskStatus != inProgress) {
          moveColumn(newTaskStatus, droppedCard)
        }
      }
    }
    event.setDropCompleted(true)
    event.consume()
  }

  doneBox.onDragDropped = (event)=>{
    var success = false
    if (event.getDragboard.hasString) {
      val cardId = event.getDragboard.getString
      var newTaskStatus = done
      if (searchByID(cardId).isDefined) {
        val droppedCard = searchByID(cardId).get
        if (droppedCard.card.taskStatus != done) {
          moveColumn(newTaskStatus, droppedCard)
        }
      }
    }
    event.setDropCompleted(true)
    event.consume()
  }



}