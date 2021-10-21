package Kanban

import scala.collection.mutable
import scala.collection.mutable.Buffer

class Board ( boardName: String) {

  private def randomString(len: Int): String = {
    val rand = new scala.util.Random(System.nanoTime)
    val sb = new StringBuilder(len)
    val ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    for (i <- 0 until len) {
      sb.append(ab(rand.nextInt(ab.length)))
    }
    sb.toString
  }

  val idString = randomString(6)

  var id = this.idString

  def getID()= this.id

  def setID(id:String) = this.id = id

  var isInApp = false

  var cardList = Buffer[Card]()

  var archivedCards: mutable.Buffer[Card] = Buffer[Card]()

  var nameBoard: String = this.boardName

  def addCard(card: Card) = {
    if (!this.cardList.contains(card) & this.isInApp == true) {
      this.cardList += card
      card.inBoardName = Some(this.boardName)
      card.inBoard = this
      true
    } else
      false
  }

  def removeCard(card: Card) = {
    if (this.cardList.contains(card) & this.isInApp == true) {
      this.cardList -= card
      card.isNotAdded
      true
    } else
      false
  }

  def archiveCard(card: Card): Boolean = {
    if (this.cardList.contains(card) && this.isInApp == true) {
      this.archivedCards += card
      this.cardList -= card
      card.isArchived = true
      true
    } else
      false
  }

}


