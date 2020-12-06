package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)



class GameOfFifteen (private val initializer: GameOfFifteenInitializer ) : Game {
    private val board = createGameBoard<Int?>(4)


    override fun initialize() {
        val randomTiles = initializer.initialPermutation.toMutableList()

        board.getAllCells().forEach {
            val first = randomTiles.firstOrNull()
            board[it] = first
            first?.let { randomTiles.remove(it) }
        }
    }

    override fun canMove(): Boolean {
        return !hasWon()
    }

    override fun hasWon(): Boolean {
        return board.getAllCells()
            .zip((1..15).toList())
            .all { board.run {
                get(getCell(it.first.i, it.first.j)) } == it.second
            }
    }

    override fun processMove(direction: Direction) {
        board.moveValues(direction)
    }

    override fun get(i: Int, j: Int): Int? {
        return board.run { get(getCell(i,j)) }
    }
}

/*
 * Update the value stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveNullValueInRowOrColumn(rowOrColumn: List<Cell>): Boolean {

    println("### $rowOrColumn")

    val tiles : List<Int?> = rowOrColumn.map { cell -> get(cell) }

    val movedTiles = tiles.moveAndSwapEmpty()

    val iterator = movedTiles.iterator()
    for ( cell in rowOrColumn ) {
        this[cell] = iterator.next()
    }

    return true
}

/*
 * Update the value stored in a board,
 * so that the value was "moved" to the specified direction
 * following the rules of the 2015 game .
 * Use the 'moveNullValueInRowOrColumn' function above.
 */
fun GameBoard<Int?>.moveValues(direction: Direction) {
    fun findNullCell() : Cell? {
        return getAllCells().find { get(it) == null }
    }
    fun moveVertically(iRange: IntProgression ) {
        val cell = findNullCell()
        val column = cell?.let { getColumn(iRange, it.j) }
        column?.let { moveNullValueInRowOrColumn(it) }
    }
    fun moveHorizontally(jRange: IntProgression ) {
        val cell = findNullCell()
        val row = cell?.let { getRow( it.i, jRange) }
        row?.let { moveNullValueInRowOrColumn( it ) }
    }
    when( direction ) {
        Direction.UP -> moveVertically( width downTo 0)
        Direction.DOWN -> moveVertically( 0 .. width)
        Direction.RIGHT -> moveHorizontally(0 .. width)
        Direction.LEFT -> moveHorizontally( width downTo 0)

    }
}