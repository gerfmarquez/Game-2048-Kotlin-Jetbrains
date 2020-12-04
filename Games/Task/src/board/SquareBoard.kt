package board

import java.lang.IllegalArgumentException

class SquareBoardImpl private constructor(
        override val width: Int,
        private val cells: MutableList<Cell>): SquareBoard {

    override fun getCell(i: Int, j: Int): Cell {
        fun fail() : Nothing { throw IllegalArgumentException("Non-existent element") }
        return cells.find { cell -> cell.i == i && cell.j == j }?:fail()
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return cells.find { cell -> cell.i == i && cell.j == j }
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val filter = cells.filter { cell -> cell.i == i && cell.j in jRange }
        return if(jRange.first < jRange.last) {
            filter.sortedBy { it.j }
        } else {
            filter.sortedByDescending { it.j }
        }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val filter = cells.filter { cell -> cell.j == j && cell.i in iRange }
        return if(iRange.first < iRange.last) {
            filter.sortedBy { it.i }
        } else {
            filter.sortedByDescending { it.i }
        }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val (i,j) = when(direction) {
            Direction.UP -> (this.i - 1 to this.j)
            Direction.DOWN -> (this.i + 1 to this.j)
            Direction.LEFT -> (this.i to this.j - 1)
            Direction.RIGHT -> (this.i to this.j + 1)
        }
        return cells.find { cell -> cell.i == i && cell.j  == j }
    }

    companion object {
        fun create(width: Int) : SquareBoard {
            val cells = mutableListOf<Cell>()
            for(rowIndex in 1..width) {
                for(columnIndex in 1..width) {
                    cells.add(Cell(rowIndex,columnIndex))
                }
            }
            return SquareBoardImpl(width, cells)
        }
    }
}