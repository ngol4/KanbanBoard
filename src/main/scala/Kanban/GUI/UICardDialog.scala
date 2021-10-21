package Kanban.GUI

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout._

class CardDialog extends TextInputDialog{
   title = "Card Creation Dialog"
   headerText = "Let's create a new card! "

  // Set the button types.
  val okButtonType = new ButtonType("OK", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(okButtonType, ButtonType.Cancel)

  // Card Info
  val cardname = new TextField() {
    promptText = "Card name "
  }

  val cardDescription = new TextField() {
    promptText = "Card description"
  }

  val cardTag = new TextField(){
    promptText = "Card tag"
  }

  // Card Status

  val group = new ToggleGroup()
  val toDoBtn= new RadioButton("To do")
  val inProgressBtn = new RadioButton("In progress")
  val doneBtn = new RadioButton("Done")

  toDoBtn.setToggleGroup(group)
  inProgressBtn.setToggleGroup(group)
  doneBtn.setToggleGroup(group)
  toDoBtn.setSelected(true)


  // Grid
  val grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 100, 10, 20)

    add(new Label("Card name:"), 0, 0)
    add(cardname, 1, 0)
    add(new Label("Card tag:"), 0, 1,1,4)
    add(cardTag, 1, 1,1,4)
    add(new Label ("Card description:"),0,5)
    add(cardDescription,1,5)
    add (new Label ("Card status"),0,6)
    add(toDoBtn,1,6)
    add(inProgressBtn,1,7)
    add(doneBtn,1,8)
  }

  // Enable/Disable login button depending on whether a card desription was entered.
  val okButton = this.dialogPane().lookupButton(okButtonType)
  okButton.disable = true

  cardDescription.text.onChange { (_, _, newValue) =>
    okButton.disable = newValue.trim().isEmpty
  }
  this.dialogPane().content = grid
  Platform.runLater(cardDescription.requestFocus())

}
