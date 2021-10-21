package Kanban

import java.time.LocalDate
import java.time.temporal.ChronoUnit

 case class Card(
                  var name: String,
                  var tag: String,
                  var text: String,
                  var taskStatus: TaskStatus) {


  def randomString(len: Int): String = {
   val rand = new scala.util.Random(System.nanoTime)
   val sb = new StringBuilder(len)
   val ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
   for (i <- 0 until len) {
    sb.append(ab(rand.nextInt(ab.length)))
   }
   sb.toString
  }

  val idString = randomString(8)

  var id = this.idString

  def getId() = this.id

  def setId(id:String) = this.id = id

  private var isInBoard = false

  var isArchived = false

  var inBoardName: Option[String] = None

  def isAdded() = this.isInBoard = true

  def isNotAdded() = this.isInBoard = false

  var inBoard = new Board("")

  var dateDeadLine = LocalDate.now()

  def daysRemaining(): Long = {
   ChronoUnit.DAYS.between(LocalDate.now(), dateDeadLine)
  }

  def setTextCard(name: String, tag: String, description: String) = {
   this.name = name
   this.tag = tag
   this.text = description

  }
 }

sealed trait TaskStatus

case object toDo extends TaskStatus
case object inProgress extends TaskStatus
case object done extends TaskStatus
