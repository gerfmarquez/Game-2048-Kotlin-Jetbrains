package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * Copyright 2020, Gerardo Marquez.
 */

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
        Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val pair = initializer.nextValue(this)
    pair?.let { set(pair.first, pair.second) }
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {

    println("### $rowOrColumn")

    val tiles : List<Int?> = rowOrColumn.map { cell -> get(cell) }

    val mergedTiles = tiles.moveAndMergeEqual {
        it + it
    }
    rowOrColumn.zip(mergedTiles)
    var isMoved = false
    val iterator = mergedTiles.iterator()
    for ( cell in rowOrColumn ) {
        if ( iterator.hasNext() ) {
            this[cell] = iterator.next()
        } else {
            if(this[cell] != null) {
                this[cell] = null
                isMoved = true
            }
        }
    }

    return isMoved
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {

    var isMoved = mutableListOf<Boolean>()
    fun iterateColumns( iRange: IntProgression )  {
        for( j in 1 .. width) {
            isMoved.add( moveValuesInRowOrColumn( getColumn( iRange, j )))
        }
    }
    fun iterateRows( jRange: IntProgression ) {
        for( i in 1 .. width) {
            isMoved.add( moveValuesInRowOrColumn( getRow( i, jRange )))
        }
    }
    when( direction ) {
        Direction.UP -> iterateColumns( 0 .. width)
        Direction.DOWN -> iterateColumns(width downTo 0)
        Direction.RIGHT -> iterateRows(width downTo 0)
        Direction.LEFT -> iterateRows( 0 .. width)

    }

    return isMoved.any{ it }
}