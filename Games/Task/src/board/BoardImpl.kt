package board

import board.Direction.*
import java.lang.IllegalArgumentException

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl.create(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl.create(width)