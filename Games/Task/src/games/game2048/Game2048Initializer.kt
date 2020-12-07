package games.game2048

import board.Cell
import board.GameBoard
import kotlin.random.Random

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * Copyright 2020, Gerardo Marquez.
 */

interface Game2048Initializer<T> {
    /*
     * Specifies the cell and the value that should be added to this cell.
     */
    fun nextValue(board: GameBoard<T?>): Pair<Cell, T>?
}

object RandomGame2048Initializer: Game2048Initializer<Int> {
    private fun generateRandomStartValue(): Int =
            if (Random.nextInt(10) == 9) 4 else 2

    /*
     * Generate a random value and a random cell among free cells
     * that given value should be added to.
     * The value should be 2 for 90% cases, and 4 for the rest of the cases.
     * Use the 'generateRandomStartValue' function above.
     * If the board is full return null.
     */
    override fun nextValue(board: GameBoard<Int?>): Pair<Cell, Int>? {

        if (board.all { it != null }) { return null }

        val emptyCells  = board.filter { it == null }
        if (emptyCells.isEmpty()) {
            return null
        }
        return emptyCells.random() to generateRandomStartValue()
    }
}