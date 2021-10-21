package Kanban

import  scala.collection.mutable.Buffer

object KanbanBoard {

  var boardList = Buffer[Board]()

  def addBoard(board: Board) = {
    if (!this.boardList.contains(board)) {
      this.boardList += board
      board.isInApp = true
      true
    } else false
  }

  def removeBoard(board: Board) = {
    if (this.boardList.contains(board)) {
      this.boardList -= board
      board.cardList.foreach( card => board.removeCard(card))
      board.isInApp = false
      true
    } else
      false
  }


  def searchByTag ( tagSearch: String)  = {
    var searchTagList = Buffer [Card] ()
    var tag = tagSearch.toLowerCase.trim
    for (card <- returnAllCard){
      if (card.tag.toLowerCase.trim == tag){
        searchTagList += card
        true
      }
      else false
    }
    if ( searchTagList.size == 0) None else Some (searchTagList)
  }


  def searchByName ( nameSearch: String)  = {
    var searchNameList = Buffer[Card]()
    var name = nameSearch.toLowerCase.trim
    for (card <- returnAllCard) {
      if (card.name.toLowerCase.trim == name) {
        searchNameList += card
        true
      } else false
    }
    if (searchNameList.size == 0) None else Some(searchNameList)
  }


  def returnAllCard:Buffer[Card] = {
    var cardListAll = Buffer[Card]()
    for (board <- boardList) {
      board.cardList.map(card => cardListAll += card)
    }
    cardListAll
  }

  def archivedCardList: Buffer[Card]= {
    var cardStorage = Buffer [Card] ()
    for ( board <- boardList) {
      board.archivedCards.map(card => cardStorage += card)
    }
    cardStorage
  }


  def returnCard ( card: Card) = {
    if (card.isArchived) {
      for (board <- boardList) {
        if (card.inBoard.getID().trim == board.getID().trim) {
          board.addCard(card)
          card.inBoard.archivedCards -= card
        }
      }
      true
    }
    else false
  }

  def getBoard ( id:String) = this.boardList.find( board => board.getID().trim == id.trim)



  def deleteArchiveCard (card: Card) ={
    if (card.isArchived){
      card.inBoard.archivedCards -= card
      true
    } else
      false
  }



}









