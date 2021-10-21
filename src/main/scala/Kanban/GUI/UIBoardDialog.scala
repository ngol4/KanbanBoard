package Kanban.GUI

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout._

  class BoardDialog extends TextInputDialog {
    title = "Board Creation Dialog"
    headerText = "Let's create a new board! "

    // Set the button types.
    val okButtonType = new ButtonType("OK", ButtonData.OKDone)
    this.dialogPane().buttonTypes = Seq(okButtonType, ButtonType.Cancel)

    val boardname = new TextField() {
      promptText = "Board name "
    }

    val grid = new GridPane() {
      hgap = 10
      vgap = 10
      padding = Insets(20, 100, 10, 20)
      add(new Label("Board name:"), 0, 0)
      add(boardname, 1, 0)
    }

    val okButton = this.dialogPane().lookupButton(okButtonType)
    okButton.disable = true

    // Do some validation (disable when boardname is empty).
    boardname.text.onChange { (_, _, newValue) =>
      okButton.disable = newValue.trim().isEmpty
    }

    this.dialogPane().content = grid

    // Request focus on the boardname field by default.
    Platform.runLater(boardname.requestFocus())

  }