package Kanban.GUI

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType, MenuBar}

object Main extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Kanban"
    width = 1020
    height = 700
  }

  val scene = new Scene ( MainWindow)
  stage.setScene(scene)

  stage.onCloseRequest = (event) => {
    val alert = new Alert(AlertType.Confirmation) {
      title = "Confirmation Dialog"
      headerText = "Look, a Confirmation Dialog."
      contentText = "Do you want to save this KanbanBoard?"
    }

    val result = alert.showAndWait()
    result match {
      case Some(ButtonType.OK) =>
        { MainWindow.saveKanban("src/main/scala/Data/TestFile")
          MainWindow.saveArchivePile("src/main/scala/Data/ArchivePile")
        }
      case _                   => stage.close()
    }
  }

}
