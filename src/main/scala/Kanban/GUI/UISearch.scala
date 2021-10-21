package Kanban.GUI

import Kanban.Card
import scalafx.Includes._
import javafx.scene.image.ImageView
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.image.Image
import scalafx.scene.layout._
import scalafx.scene.paint.Color.{Bisque, Black, FloralWhite, Gray, LightCoral, LightGoldrenrodYellow, LightGray, LightSalmon, LightSkyBlue, MediumSpringGreen, Pink, RosyBrown, RoyalBlue, White, YellowGreen}
import scalafx.scene.text.{Font, FontWeight}

import java.io.FileInputStream

class UISearch extends GridPane {

  var root = this
  root.setHgap(10)

  // Column + row constraints
  val column0 = new ColumnConstraints
  val column1 = new ColumnConstraints
  val row0 = new RowConstraints
  val row1 = new RowConstraints
  val row2 = new RowConstraints

  column0.percentWidth = 50
  column1.percentWidth = 50
  row0.percentHeight = 4.5
  row1.percentHeight = 7
  row2.percentHeight = 87
  root.columnConstraints = Array[ColumnConstraints](column0, column1)
  root.rowConstraints = Array[RowConstraints](row0, row1,row2)

  //Component
  // Search bar
  var searchField = new TextField()
  searchField.setPromptText(" Search for cards here...")
  searchField.setPrefWidth(300)

  var iconSearch = new ImageView(new Image(new FileInputStream("src/main/scala/pics/iconSearch.png")))
  iconSearch.setFitHeight(10)
  iconSearch.setFitWidth(10)
  var searchButton = new Button()
  searchButton.setGraphic(iconSearch)
  searchButton.onAction = (event) => searchCard()
  var searchComponent = new HBox(searchField,searchButton)
  searchComponent.prefWidth  = Double.MaxValue
  searchComponent.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))
  root.add(searchComponent, 0, 0, 1, 1)


  // Title box

  var titleName = new Label("> Search by card name")
  var titleTag = new Label("> Search by card tag")
  titleName.setTextFill(RosyBrown)
  titleTag.setTextFill(RosyBrown)

  titleName.setFont(Font.font("Times New Roman", FontWeight.Bold, 20))
  titleTag.setFont(Font.font("Times New ROman", FontWeight.Bold, 20))
  var titleBoxName = new HBox(titleName)
  var titleBoxTag= new HBox(titleTag)
  titleBoxName.setPrefHeight(30)
  titleBoxTag.setPrefHeight(30)
  titleBoxName.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))
  titleBoxTag.background = new Background(Array(new BackgroundFill((Bisque), CornerRadii.Empty, Insets.Empty)))


  // Result display box
  var keyword = ""
  var resultNameSearch = ""
  var resultTagSearch = ""
  var headline = ""

  var headlineLabel = new Label(headline)
  var resultNameLabel = new Label(resultNameSearch)
  var resultTagLabel = new Label(resultTagSearch)

  headlineLabel.setFont(Font.font("Times New Roman", FontWeight.Bold, 14))
  resultNameLabel.setFont(Font.font("Times New Roman", FontWeight.Bold, 14))
  resultTagLabel.setFont(Font.font("Times New Roman", FontWeight.Bold, 14))

  headlineLabel.setTextFill(Gray)
  resultNameLabel.setTextFill(Gray)
  resultTagLabel.setTextFill(Gray)


  var headlineBox = new HBox(headlineLabel)
  var nameResultBox = new HBox(resultNameLabel)
  var tagResultBox = new HBox(resultTagLabel)

  nameResultBox.setPrefHeight(20)
  tagResultBox.setPrefHeight(20)

  nameResultBox.background = new Background(Array(new BackgroundFill((LightGoldrenrodYellow), CornerRadii.Empty, Insets.Empty)))
  tagResultBox.background = new Background(Array(new BackgroundFill((LightGoldrenrodYellow), CornerRadii.Empty, Insets.Empty)))


  var nameDisplayBox = new VBox(titleBoxName,nameResultBox)
  var tagDisplayBox = new VBox(titleBoxTag,tagResultBox)

  headlineBox.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))
  root.add(headlineBox,1,0,2,1)
  root.add(nameDisplayBox, 0,1,1,1)
  root.add(tagDisplayBox, 1,1,1,1)


  // Result big box
  var searchNameBox = new VBox()
  var searchTagBox = new VBox()
  searchNameBox.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))
  searchTagBox.background = new Background(Array(new BackgroundFill((FloralWhite), CornerRadii.Empty, Insets.Empty)))

  root.add(searchNameBox,0,2,1,1)
  root.add(searchTagBox,1,2,2,1)

// Function
  def searchByNameUI(nameSearch: String) = {
    var result = MainWindow.kanbanBoard.searchByName(nameSearch)
    if (result.isDefined) {
      for (card_ <- result.get) {
        var card = new Card(card_.name, card_.tag, card_.text, card_.taskStatus)
        var cardUI = new UICard(card)
        searchNameBox.getChildren().add(cardUI)
        cardUI.setTextCard(card.name, card.tag, card.text)
      }
      resultNameLabel.setText(result.get.size + " card(s) have the same card name as your keyword")
    } else resultNameLabel.setText("Can't find any card having the name as your keyword.")
  }

  def searchByTagUI(tagSearch: String) = {
    var result = MainWindow.kanbanBoard.searchByTag(tagSearch)
    if (result.isDefined) {
      for (card_ <- result.get) {
        var card = new Card(card_.name, card_.tag, card_.text, card_.taskStatus)
        var cardUI = new UICard(card)
        searchTagBox.getChildren().add(cardUI)
        cardUI.setTextCard(card.name, card.tag, card.text)
      }
      resultTagLabel.setText(result.get.size + " card(s) have the same card tag as your keyword")
    } else resultTagLabel.setText("Can't find any card having the same tag as your keyword.")
  }


  def searchCard() = {
    var node = searchNameBox.getChildren
    searchNameBox.getChildren().removeAll(node)
    var node1 = searchTagBox.getChildren
    searchTagBox.getChildren().removeAll(node1)
    keyword = searchField.getText()
    headlineLabel.setText("You have search keyword: "+ keyword )
    searchByNameUI(keyword)
    searchByTagUI(keyword)
  }


}
