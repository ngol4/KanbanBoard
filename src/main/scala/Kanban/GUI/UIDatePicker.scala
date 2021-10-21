package Kanban.GUI

import Kanban.Card
import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout._

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class UIDatePicker (card: Card) extends TextInputDialog {
  title = " Date Picker"
  headerText = "Let's set a date for deadline!"

  var tilePane = new TilePane()

  var label = new Label("No date selected.")
  var dayRemainingLabel = new Label (" No days remaining.")

  val okButtonType = new ButtonType("OK", ButtonData.OKDone)
  this.dialogPane().buttonTypes = Seq(okButtonType, ButtonType.Cancel)

  this.dialogPane().content = tilePane

  var datePicker = new DatePicker()

  var dateDeadLine = card.dateDeadLine

  datePicker.setOnAction((event) => {
    dateDeadLine = datePicker.getValue
    setDeadline()
    }
  )

  def setDeadline ()={
    label.setText("Date deadline: " + dateDeadLine)
    var dayLeft = ChronoUnit.DAYS.between( LocalDate.now(), dateDeadLine)
    if (dayLeft < 0) dayRemainingLabel.setText(" The task is overdue. ")
    else if (dayLeft == 0 ) dayRemainingLabel.setText(" The deadline is today !")
    else dayRemainingLabel.setText( "Days remaining: "  + dayLeft + " days.")
  }

  datePicker.setShowWeekNumbers(true)
  tilePane.getChildren().addAll(datePicker,label,dayRemainingLabel)

}





