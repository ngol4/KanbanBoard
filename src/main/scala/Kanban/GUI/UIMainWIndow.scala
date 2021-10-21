package Kanban.GUI

import Kanban._
import scalafx.Includes._
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.effect.DropShadow
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.text.{Font, FontWeight}

import java.io._
import scala.collection.mutable.Buffer

// Main root
object MainWindow extends GridPane {

  var mainRoot = this

  var kanbanBoard = KanbanBoard

  val searchPage = new UISearch

  val archivePage = UIArchive

  def boardList = kanbanBoard.boardList

  var boardListUI = Buffer[UIBoard]()

  var fileParsing =  new FileManager

  // Side Menu

  var sideMenu = new VBox()

  var blankDisplay = new GridPane()

  var boardDisplay = blankDisplay

  sideMenu.background = new Background(Array(new BackgroundFill((LightYellow), CornerRadii.Empty, Insets.Empty)))
  mainRoot.add(sideMenu, 0, 0, 1, 2)
  mainRoot.add(boardDisplay, 1, 0, 1, 2)

  // Constraints for gridpane
  val column0 = new ColumnConstraints
  val column1 = new ColumnConstraints
  val row0 = new RowConstraints
  val row1 = new RowConstraints

  column0.percentWidth = 15
  column1.percentWidth = 85
  row0.percentHeight = 100
  row1.percentHeight = 0

  mainRoot.columnConstraints = Array[ColumnConstraints](column0, column1) //Add constraints in order
  mainRoot.rowConstraints = Array[RowConstraints](row0, row1)
  mainRoot.setHgap(10)

  // Side Menu
  sideMenu.setSpacing(15)

  // Welcome
  var iconWelcome = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconWelcome.png")))
  iconWelcome.setFitHeight(80)
  iconWelcome.setFitWidth(120)
  val welcomeBox= new HBox(iconWelcome)
  welcomeBox.setAlignment(Pos.Center)


  // Search Card
  var iconSearch = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconSearch.png")))
  iconSearch.setFitHeight(10)
  iconSearch.setFitWidth(10)

  var searchButton = new Button("    Search Card", iconSearch)
  searchButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
  searchButton.setFont(Font.font("Lora", FontWeight.Bold,16))
  searchButton.setTextFill(RosyBrown)
  searchButton.onAction = (event) => {
    mainRoot.getChildren().remove(mainRoot.boardDisplay)
    mainRoot.boardDisplay = new UISearch
    mainRoot.add(boardDisplay, 1, 0, 1, 2)
  }
  var searchBox = new HBox(searchButton)
  searchBox.setOnMouseEntered( (event) => searchBox.setBackground(new Background(Array(new BackgroundFill((LightCoral), CornerRadii.Empty, Insets.Empty)))))
  searchBox.setOnMouseExited( (event) => searchBox.setBackground( new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))))
  searchBox.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))

  // Archive card
    val archiveIcon = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconArchive.png")))
    archiveIcon.setFitHeight(15)
    archiveIcon.setFitWidth(15)
    val archiveButton = new Button("   Archive Pile", archiveIcon)
    archiveButton.setFont(Font.font("Lora", FontWeight.Bold,16))
    archiveButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
    archiveButton.setTextFill(RosyBrown)

  archiveButton.onAction = (event) => {
      mainRoot.getChildren().remove(mainRoot.boardDisplay)
      mainRoot.boardDisplay = UIArchive
      mainRoot.add(boardDisplay, 1, 0, 1, 2)
    }
    var archiveBox = new HBox(archiveButton)
    archiveBox.setOnMouseEntered( (event) => archiveBox.setBackground(new Background(Array(new BackgroundFill((LightCoral), CornerRadii.Empty, Insets.Empty)))))
    archiveBox.setOnMouseExited( (event) => archiveBox.setBackground( new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))))
    archiveBox.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))

  // Add / Delete Board
  val boardIcon = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconBoard.png")))
  boardIcon.setFitHeight(15)
  boardIcon.setFitWidth(15)
  val addBoardBtn = new Button("   Add Board", boardIcon)
  addBoardBtn.setFont(Font.font("Lora", FontWeight.Bold,16))
  addBoardBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
  addBoardBtn.setTextFill(RosyBrown)
  addBoardBtn.onAction = (event) => createBoard()
  val boardBox = new HBox(addBoardBtn)
  boardBox.setOnMouseEntered( (event) => boardBox.setBackground(new Background(Array(new BackgroundFill((LightCoral), CornerRadii.Empty, Insets.Empty)))))
  boardBox.setOnMouseExited( (event) => boardBox.setBackground( new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))))
  boardBox.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))

  // TestButton
//  val testButton = new Button("Test")
//  sideMenu.getChildren().add(testButton)
//  testButton.onAction = (event) => {
//    println("Board size:" + boardList.size)
//    println("Board UI size:" + boardListUI.size)
//    println("Card size:" + kanbanBoard.returnAllCard.size)
//    println("Card UI size:" + UICardListALl.size)
//    println ("Archive size :" + kanbanBoard.archivedCardList.size)
//  }

   //Save + load file button
  val iconSave = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconSave.png")))
  iconSave.setFitHeight(15)
  iconSave.setFitWidth(15)
  val saveFileBtn = new Button("   Save Kanban",iconSave)
  saveFileBtn.setFont(Font.font("Lora", FontWeight.Bold,16))
  saveFileBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
  saveFileBtn.setTextFill(RosyBrown)
  saveFileBtn.onAction = (event) => {
    saveKanban("src/main/scala/Data/TestFile")
    saveArchivePile("src/main/scala/Data/ArchivePile")
  }
  var saveBox = new HBox(saveFileBtn)
  saveBox.setOnMouseEntered( (event) => saveBox.setBackground(new Background(Array(new BackgroundFill((LightCoral), CornerRadii.Empty, Insets.Empty)))))
  saveBox.setOnMouseExited( (event) => saveBox.setBackground( new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))))
  saveBox.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))


  val iconLoad = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconFolder.png")))
  iconLoad.setFitHeight(15)
  iconLoad.setFitWidth(15)
  val loadFileBtn = new Button("   Load file",iconLoad)
  loadFileBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
  loadFileBtn.setFont(Font.font("Lora", FontWeight.Bold,16))
  loadFileBtn.setTextFill(RosyBrown)
  val loadBox = new HBox(loadFileBtn)
  loadBox.setOnMouseEntered( (event) => loadBox.setBackground(new Background(Array(new BackgroundFill((LightCoral), CornerRadii.Empty, Insets.Empty)))))
  loadBox.setOnMouseExited( (event) => loadBox.setBackground( new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))))
  loadBox.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))

  loadFileBtn.onAction = (event) => {
    fileParsing.parseFile()
    fileParsing.loadFile()
    fileParsing.loadArchiveCard()
  }


  // BoardList
  val iconBoardList = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconArrow.png")))
  iconBoardList.setFitHeight(15)
  iconBoardList.setFitWidth(15)
  val boardListBtn = new Button("   Board List", iconBoardList)
  boardListBtn.setStyle("-fx-background-color: rgba(255, 255, 255, 0);")
  boardListBtn.setTextFill(RosyBrown)
  boardListBtn.setFont(Font.font("Lora", FontWeight.Bold,16))
  val boardListBox = new HBox(boardListBtn)
  boardListBox.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))


  sideMenu.getChildren().addAll(welcomeBox, searchBox,archiveBox,boardBox,saveBox,loadBox,boardListBox)


  // Function
  def createBoard() = {
    var boardDialog = new BoardDialog()
    boardDialog.show()
    boardDialog.okButton match {
      case bt: javafx.scene.control.Button => bt.onAction = (event) => {
        val boardname = boardDialog.boardname.getText
        var board = new Board(boardname)
        kanbanBoard.addBoard(board)
        addBoard(board)
      }
    }
  }

  def addBoard (board:Board):Unit={
    var boardUI = new UIBoard(board)
    this.boardListUI += boardUI
    var boardBox = new UIBoardBox(boardUI)
    boardBox.boardButton.setText(board.nameBoard)
    sideMenu.getChildren().add(boardBox)
  }


  def deleteBoard(UIboard: UIBoard): Unit = {
    kanbanBoard.removeBoard(UIboard.board)
    this.boardListUI -= UIboard
    sideMenu.getChildren().remove(UIboard.boardBox)
    mainRoot.getChildren().remove(UIboard)

  }

  def updateBoard():Unit = {
    this.mainRoot.add(boardDisplay, 1, 0, 1, 2)

  }

  def archiveCard(cardUI: UICard): Unit = {
    cardUI.inBoardUI.cardListUI -= cardUI
    if (cardUI.card.taskStatus == toDo) archivePage.archiveBox1.getChildren().add(cardUI)
    if (cardUI.card.taskStatus == inProgress) archivePage.archiveBox2.getChildren().add(cardUI)
    if (cardUI.card.taskStatus == done) archivePage.archiveBox3.getChildren().add(cardUI)
    cardUI.archiveButton.setVisible(false)
    cardUI.returnButton.setVisible(true)
  }

  def returnCard(cardUI: UICard): Unit = {
    kanbanBoard.returnCard(cardUI.card)
    cardUI.inBoardUI.cardListUI += cardUI
    if (cardUI.card.taskStatus == toDo){
      archivePage.archiveBox1.getChildren().remove(cardUI)
      cardUI.inBoardUI.toDoBox.getChildren().add(cardUI)
    }
    if (cardUI.card.taskStatus == inProgress){
      archivePage.archiveBox2.getChildren().remove(cardUI)
      cardUI.inBoardUI.inProgressBox.getChildren().add(cardUI)
    }
    if (cardUI.card.taskStatus == done) {
      archivePage.archiveBox3.getChildren().remove(cardUI)
      cardUI.inBoardUI.doneBox.getChildren().add(cardUI)
    }
    cardUI.returnButton.setVisible(false)
    cardUI.archiveButton.setVisible(true)
  }

  def UICardListALl = {
    var cardListUI = Buffer[UICard]()
    for (boardUI <- boardListUI) {
      boardUI.cardListUI.map(cardUI => cardListUI += cardUI)
    }
    cardListUI
  }

  def boardUI (board:Board)= {
    boardListUI.find( boardUI => boardUI.board == board)
  }

  def cardUI (card:Card)={
    UICardListALl.find(cardUI => cardUI.card.getId().trim == card.getId().trim)
  }

  def deleteArchiveCard (cardUI:UICard):Unit= {
    kanbanBoard.deleteArchiveCard(cardUI.card)
    if (cardUI.card.taskStatus == toDo) {
      this.archivePage.archiveBox1.getChildren().remove(cardUI)
    } else if (cardUI.card.taskStatus == inProgress) {
      this.archivePage.archiveBox2.getChildren().remove(cardUI)
    } else if (cardUI.card.taskStatus == done) {
      this.archivePage.archiveBox3.getChildren().remove(cardUI)
    }
  }


  // Save file
  def saveKanban(filePath: String) = {

    val boardList = kanbanBoard.boardList

    var fileContent = Buffer[String]()

    fileContent += "["

    for (board <- boardList) {
      board match {
        case board_ : Board => {
          var lastBoard = boardList(boardList.size - 1)
          fileContent += "{"
          fileContent += """"boardname":""" + s""" "${board.nameBoard}"""" + ","
          fileContent += """"boardID":""" + s""" "${board.getID()}"""" + ","
          fileContent += """"cardList":["""
          if (board.cardList.size > 0) {
            var lastCard = board.cardList(board.cardList.size - 1)
            for (card <- (board.cardList.dropRight(1))) {
              fileContent += "{"
              fileContent += """ "name": """ + s""""${card.name}"""" + ","
              fileContent += """ "tag": """ + s""""${card.tag}"""" + ","
              fileContent += """ "text": """ + s""""${card.text}"""" + ","
              fileContent += """ "taskStatus": """ + s""""${card.taskStatus}"""" + ","
              fileContent += """ "cardID":""" + s""" "${card.getId}" """ + ","
              fileContent += """ "inBoardID": """ + s""""${card.inBoard.getID()}"""" + ","
              fileContent += """ "dateDeadLine" : """ + s""""${card.dateDeadLine}""""
              fileContent += "},"
            }

            fileContent += "{"
            fileContent += """ "name": """ + s""""${lastCard.name}"""" + ","
            fileContent += """ "tag": """ + s""""${lastCard.tag}"""" + ","
            fileContent += """ "text": """ + s""""${lastCard.text}"""" + ","
            fileContent += """ "taskStatus": """ + s""""${lastCard.taskStatus}"""" + ","
            fileContent += """ "cardID":""" + s""" "${lastCard.getId}" """ + ","
            fileContent += """ "inBoardID": """ + s""""${lastCard.inBoard.getID()}"""" + ","
            fileContent += """ "dateDeadLine" : """ + s""""${lastCard.dateDeadLine}""""
            fileContent += "}"
          }
          fileContent += "]"
          if (board == lastBoard) fileContent += "}" else fileContent += "},"
        }
        case _ =>
      }
    }
    fileContent += "]"

    try {
      val fileWriter = new FileWriter(filePath)
      val buffWriter = new BufferedWriter(fileWriter)
      try {
        buffWriter.write(fileContent.mkString("\n"))
      } finally {
        buffWriter.close()
      }
    } catch {
      case _: FileNotFoundException => {
        println("Error with saving file: Save file not found.")
        new Alert(AlertType.Information, "Error with saving file: Save file not found.").showAndWait()
      }
      case _: IOException => {
        println("Error with saving data: IOException")
        new Alert(AlertType.Information, "Error with saving file: IOException").showAndWait()
      }
      case _: Throwable => {
        println("Error with saving data: Unexpected exception.")
        new Alert(AlertType.Information, "Error with saving file: Unexpected exception.").showAndWait()
      }
    }
  }


  def saveArchivePile(filePath: String) = {

    val archiveCards = kanbanBoard.archivedCardList

    var fileContent = Buffer[String]()

    fileContent += "["

    for (card <- archiveCards) {
      card match {
        case card_ : Card => {
          fileContent += "{"
          fileContent += """"boardname":""" + """"Archive Pile"""" + ","
          fileContent += """"boardID":""" + s""" "None """" + ","
          fileContent += """"cardList":["""
          var lastCard = archiveCards(archiveCards.size - 1)
          for (card <- (archiveCards.dropRight(1))) {
            fileContent += "{"
            fileContent += """ "name": """ + s""""${card.name}"""" + ","
            fileContent += """ "tag": """ + s""""${card.tag}"""" + ","
            fileContent += """ "text": """ + s""""${card.text}"""" + ","
            fileContent += """ "taskStatus": """ + s""""${card.taskStatus}"""" + ","
            fileContent += """ "cardID":""" + s""" "${card.getId}" """ + ","
            fileContent += """ "inBoardID": """ + s""""${card.inBoard.getID()}"""" + ","
            fileContent += """ "dateDeadLine" : """ + s"""${card.dateDeadLine}"""
            fileContent += "},"
          }
          fileContent += "{"
          fileContent += """ "name": """ + s""""${lastCard.name}"""" + ","
          fileContent += """ "tag": """ + s""""${lastCard.tag}"""" + ","
          fileContent += """ "text": """ + s""""${lastCard.text}"""" + ","
          fileContent += """ "taskStatus": """ + s""""${lastCard.taskStatus}"""" + ","
          fileContent += """ "cardID":""" + s""" "${lastCard.getId}" """ + ","
          fileContent += """ "inBoardID": """ + s""""${lastCard.inBoard.getID()}"""" + ","
          fileContent += """ "dateDeadLine" : """ + s""""${lastCard.dateDeadLine}""""
          fileContent += "}"
          fileContent += "]"
          fileContent += "}"

        }
        case _ =>
      }
    }
    fileContent += "]"

    try {
      val fileWriter = new FileWriter(filePath)
      val buffWriter = new BufferedWriter(fileWriter)
      try {
        buffWriter.write(fileContent.mkString("\n"))
      } finally {
        buffWriter.close()
      }
    } catch {
      case _: FileNotFoundException => {
        println("Error with saving file: Save file not found.")
        new Alert(AlertType.Information, "Error with saving file: Save file not found.").showAndWait()
      }
      case _: IOException => {
        println("Error with saving data: IOException")
        new Alert(AlertType.Information, "Error with saving file: IOException").showAndWait()
      }
      case _: Throwable => {
        println("Error with saving data: Unexpected exception.")
        new Alert(AlertType.Information, "Error with saving file: Unexpected exception.").showAndWait()
      }
    }
  }



}

