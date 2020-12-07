package board

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * Copyright 2020, Gerardo Marquez.
 */

class GameBoardImpl<T> private constructor (
        private val gameBoard: MutableMap<Cell,T?>,
        override val width: Int,
        squareBoard: SquareBoard) : SquareBoard by squareBoard, GameBoard<T> {


    override operator fun get(cell: Cell): T? {
        return gameBoard[cell]
    }

    override operator fun set(cell: Cell, value: T?) {
        gameBoard[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        val list = mutableListOf<Cell>()
        for (element in gameBoard) {
            if (predicate(element.value)) list.add(element.key)
        }
        return list
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        for (element in gameBoard) {
            if (predicate(element.value)) return element.key
        }
        return null
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        for (element in gameBoard) {
            if (predicate(element.value)) return true
        }
        return false
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        var all = true
        for (element in gameBoard) {
            if (predicate(element.value) && all) all = true
            else return false
        }
        return all
    }

    companion object {
        fun <T> create(width: Int): GameBoard<T> {
            val squareBoard = SquareBoardImpl.create(width)
            val map: MutableMap<Cell, T?> = squareBoard.getAllCells()
                    .map { it to null as T }.toMap().toMutableMap()
            return GameBoardImpl(map, width, squareBoard)
        }
    }
}