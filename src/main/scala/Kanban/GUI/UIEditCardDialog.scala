package Kanban.GUI

import Kanban.Card
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, Label, TextField, TextInputDialog}
import scalafx.scene.layout.GridPane

class UIEditCardDialog (card:Card) extends TextInputDialog{
  title = "Card Creation Dialog"
  headerText = "Let's edit a card! "

  // Set the button types.
  val okButtonType = new ButtonType("OK", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(okButtonType, ButtonType.Cancel)

  val cardname = new TextField() {
    promptText = "Card name "
  }

  val cardDescription = new TextField() {
    promptText = "Card description"
  }

  val cardTag = new TextField(){
    promptText = "Card tag"
  }

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

  }

  val okButton = this.dialogPane().lookupButton(okButtonType)
  okButton.disable = true

  cardDescription.text.onChange { (_, _, newValue) =>
    okButton.disable = newValue.trim().isEmpty
  }

  this.dialogPane().content = grid
}