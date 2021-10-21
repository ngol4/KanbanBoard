package Kanban

import Kanban.GUI._
import io.circe.generic.auto._
import io.circe.parser.decode
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import java.time.LocalDate
import scala.collection.mutable.Buffer

class FileManager {

  val kanbanBoard = KanbanBoard

  case class Board(boardname: String, boardID:String, cardList: List[Card])

  case class Card(name: String, tag: String, text: String, taskStatus: String,cardID:String, inBoardID:String,dateDeadLine:LocalDate)

  /** Reads in a JSON formatted string as a List if possible */
  def readJson(text: String) = decode[List[Board]](text).toTry

  /** Reads all the contents of a given Source as a String */
  def readText(source: scala.io.Source) = source.getLines().mkString("\n")

  val fileSource = util.Try(scala.io.Source.fromFile("src/main/scala/Data/TestFile"))

  val archiveFileSource = util.Try(scala.io.Source.fromFile("src/main/scala/Data/ArchivePile"))

  val boards = {
    val temp = Buffer[Board]()
    for {
      source <- fileSource // Get successful source result
      text = readText(source) //Get text from source
      list <- readJson(text) //Parse JSON into list
      board <- list
    } temp += board
    temp
  }

  val archivePile = {
    val temp = Buffer[Board]()
    for {
      source <- archiveFileSource // Get successful source result
      text = readText(source) //Get text from source
      list <- readJson(text) //Parse JSON into list
      board <- list
    } temp += board
    temp
  }


  def translateStatus (status:String):TaskStatus={
  if (status.trim == "toDo") toDo
  else if (status.trim == "inProgress") inProgress
  else done
}

  def parseFile()= {
    val boardList = this.boards
    for (boardParsing <- boardList) {
      var board = new Kanban.Board(boardParsing.boardname)
      kanbanBoard.addBoard(board)
      board.setID(boardParsing.boardID)
      for (card_ <- boardParsing.cardList) {
        var card = new Kanban.Card(card_.name, card_.tag, card_.text, translateStatus(card_.taskStatus))
        card.setId(card_.cardID)
        card.dateDeadLine = card_.dateDeadLine
        board.addCard(card)
      }
    }
  }

  def loadFile()={
    for (board <- kanbanBoard.boardList){
      var boardUI = new UIBoard(board)
      MainWindow.addBoard(board)
      for ( card <- board.cardList){
        MainWindow.boardUI(card.inBoard) match {
          case Some(boardUI: UIBoard) => boardUI.addCard(card)
          case _ => new Alert(AlertType.Information, "Error in loading file !").showAndWait()
          }
        }
    }
  }

  def loadArchiveCard ()= {
    if (archivePile.size > 0) {
      var archiveP = this.archivePile.head
      for (card_ <- archiveP.cardList) {
        var card = new Kanban.Card(card_.name, card_.tag, card_.text, translateStatus(card_.taskStatus))
        card.dateDeadLine = card_.dateDeadLine
        card.setId(card_.cardID)
        var inBoard = kanbanBoard.getBoard(card_.inBoardID)
        card.inBoard = inBoard.get
        if (inBoard.isDefined){
          var boardUi = MainWindow.boardUI(inBoard.get)
          inBoard.get.addCard(card)
          boardUi.get.addCard(card)
          inBoard.get.archiveCard(card)
          MainWindow.archiveCard(MainWindow.cardUI(card).get)
        }
      }
    } else {
      new Alert(AlertType.Information, "Error in loading file !").showAndWait()
    }
  }

}
