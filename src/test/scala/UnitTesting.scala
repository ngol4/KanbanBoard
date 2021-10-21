
import Kanban._

import collection.mutable.Buffer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Test1 extends AnyFlatSpec with Matchers {

  " Add / remove board  functions" should "work correctly" in {
    val testKanbanBoard = KanbanBoard

    // No board created.
    assume(testKanbanBoard.boardList.isEmpty)

    // Create 5 boards
    val board1 = new Board("Week 1")
    val board2 = new Board("Week 2")
    val board3 = new Board("Week 3")
    val board4 = new Board("Week 4")
    val board5 = new Board("Week 5")

    // Add 5 boards to app
    testKanbanBoard.addBoard(board1)
    testKanbanBoard.addBoard(board2)
    testKanbanBoard.addBoard(board3)
    testKanbanBoard.addBoard(board4)
    testKanbanBoard.addBoard(board5)

    // App now has 5 boards
    assert(testKanbanBoard.boardList.size == 5)

    // Remove 2 boards
    testKanbanBoard.removeBoard(board4)
    testKanbanBoard.removeBoard(board5)

    // App now has 3 boards
    assert(testKanbanBoard.boardList.size == 3)
  }
}

class Test2 extends AnyFlatSpec with Matchers {
  " Add / remove / rename / edit text / change status card functions" should "work correctly" in {
    // Same context as test 1
    val testKanbanBoard = KanbanBoard

    val board1 = new Board("Week 1")
    val board2 = new Board("Week 2")
    val board3 = new Board("Week 3")
    val board4 = new Board("Week 4")
    val board5 = new Board("Week 5")

    testKanbanBoard.addBoard(board1)
    testKanbanBoard.addBoard(board2)
    testKanbanBoard.addBoard(board3)
    testKanbanBoard.addBoard(board4)
    testKanbanBoard.addBoard(board5)

    testKanbanBoard.removeBoard(board4)
    testKanbanBoard.removeBoard(board5)

    // Test

    // Create 5 cards
    var card1 = new Card("Calculus", "Math", "Chapter 1 Calculus", toDo)
    var card2 = new Card("Machine Learning D", "Study", "Chapter 1 Regression", toDo)
    var card3 = new Card("Machine Learning", "Study", "Chapter 2 Classification", toDo)
    var card4 = new Card("Programming Studio ", "Computer Science", "Chapter 19", toDo)
    var card5 = new Card(" Programming 2 ", "Computer Science", "Chapter 1 ", toDo)

    // Add to board 1 , 2 , 4
    board1.addCard(card1)
    board1.addCard(card4)
    assert(board1.cardList.size == 2)

    board2.addCard(card2)
    board2.addCard(card5)

    board3.addCard(card3)

    assert(board2.cardList.size == 2)
    assert(board3.cardList.size == 1)

    // Board 4 has been removed before so can't add card
    board4.addCard(card5)
    assert(board4.cardList.size == 0)

    // Card 2 is not in board 1 so the size is 1 instead of 2
    board1.removeCard(card4)
    board1.removeCard(card2)
    assert(board1.cardList.size == 1)
  }
}

class Test3 extends AnyFlatSpec with Matchers {

  "Search cards by tag / name " should "work correctly" in {

    val testKanbanBoard = KanbanBoard

    val board1 = new Board("Week 1")
    val board2 = new Board("Week 2")
    val board3 = new Board("Week 3")
    val board4 = new Board("Week 4")
    val board5 = new Board("Week 5")

    testKanbanBoard.addBoard(board1)
    testKanbanBoard.addBoard(board2)
    testKanbanBoard.addBoard(board3)
    testKanbanBoard.addBoard(board4)
    testKanbanBoard.addBoard(board5)

    var card1 = new Card("Calculus", "Math", "Chapter 1 Calculus", toDo)
    var card2 = new Card("Machine Learning D", "Study", "Chapter 1 Regression", toDo)
    var card3 = new Card("Machine Learning", "Study", "Chapter 2 Classification", inProgress)
    var card4 = new Card("Programming Studio ", "Computer Science", "Chapter 19", done)
    var card5 = new Card(" Programming 2 ", "Computer Science", "Chapter 1 ", toDo)
    val card6 = new Card(" Programming 2 ", "Computer Science", "Chapter 2", toDo)

    board1.addCard(card1)
    board2.addCard(card2)
    board3.addCard(card3)
    board4.addCard(card4)
    board5.addCard(card5)


    assert(testKanbanBoard.boardList.size == 5)
    assert(testKanbanBoard.returnAllCard.size == 5)

    testKanbanBoard.removeBoard(board5)

    assert(testKanbanBoard.boardList.size == 4)
    assert(testKanbanBoard.returnAllCard.size == 4)


    val testTag1 = testKanbanBoard.searchByTag("    stuDY ")
    assert(testTag1 == Some(Buffer(card2, card3)))

    val testTag2 = testKanbanBoard.searchByTag(" Play")
    assert(testTag2 == None)

    val testTag3 = testKanbanBoard.searchByTag(" Computer Science")
    assert(testTag3 == Some(Buffer(card4)))

    board1.addCard(card6)


    val testName1 = testKanbanBoard.searchByName(" ProgrAmming 2")
    assert(testName1 == Some(Buffer(card6)))

    val testName2 = testKanbanBoard.searchByName("Calculus")
    assert(testName2 == Some(Buffer(card1)))

    val testName3 = testKanbanBoard.searchByName("Computer")
    assert(testName3 == None)

  }
}

class Test4 extends AnyFlatSpec with Matchers{
  "Archive / return cards / delete archived cards/get board" should "work correctly" in {

    val testKanbanBoard = KanbanBoard

    val board1 = new Board("Week 1")
    val board2 = new Board("Week 2")


    testKanbanBoard.addBoard(board1)
    testKanbanBoard.addBoard(board2)


    var card1 = new Card("Calculus", "Math", "Chapter 1 Calculus", toDo)
    var card2 = new Card("Machine Learning D", "Study", "Chapter 1 Regression", toDo)
    var card3 = new Card("Machine Learning", "Study", "Chapter 2 Classification", inProgress)
    var card4 = new Card("Programming Studio ", "Computer Science", "Chapter 19", done)
    var card5 = new Card(" Programming 2 ", "Computer Science", "Chapter 1 ", toDo)

    board1.addCard(card1)
    board2.addCard(card2)
    board1.addCard(card3)
    board2.addCard(card4)
    board1.addCard(card5)

    assert(testKanbanBoard.archivedCardList.size == 0)
    assert(board1.cardList.size == 3)
    assert(board2.cardList.size == 2)

    board1.archiveCard(card1)
    board2.archiveCard(card2)

    assert( testKanbanBoard.archivedCardList.size == 2)
    assert(board1.cardList.size == 2)
    assert(board1.archivedCards.size ==1)
    assert(board2.cardList.size == 1)
    assert(board2.archivedCards.size ==1)


    testKanbanBoard.returnCard(card1)
    testKanbanBoard.returnCard(card2)

    assert(board1.cardList.size == 3)
    assert(board2.cardList.size == 2)
    assert(board1.archivedCards.size ==0)
    assert(board2.archivedCards.size ==0)


    board1.archiveCard(card2)
    assert(board1.cardList.size == 3)
    assert(board2.cardList.size == 2)

    testKanbanBoard.returnCard(card2)
    assert(board1.cardList.size == 3)
    assert(board2.cardList.size == 2)


    // Delete archive cards
    board1.archiveCard(card1)
    assert(board1.archivedCards.size ==1)

    testKanbanBoard.deleteArchiveCard(card1)
    assert(board1.cardList.size ==2)
    assert(board1.archivedCards.size ==0)


    val testGetBoard = testKanbanBoard.getBoard(board1.getID())
    assert(testGetBoard != None)
    assert(testGetBoard == Some(board1))
  }


  }

