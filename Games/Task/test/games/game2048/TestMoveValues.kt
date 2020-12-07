package games.game2048

import board.Direction
import board.Direction.DOWN
import board.Direction.RIGHT
import org.junit.Assert
import org.junit.Test

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * Copyright 2020, Gerardo Marquez.
 */

class TestMoveValues : AbstractTestGameWithSmallNumbers() {
    @Test
    fun testSimpleMove() = testAllDirections("---- ---- -2-- ----", "---- ---- ---2 ----")

    @Test
    fun testNoMove() = testAllDirections("---- ---- ---- 2424", "---- ---- ---- 2424", move = false)

    @Test
    fun testSeveralMoves() = testAllDirections("2--- -2-- --2- ---2", "---2 ---2 ---2 ---2")

    @Test
    fun testMovesInSomeRows() = testAllDirections("2--- ---- --2- 2424", "---2 ---- ---2 2424")

    @Test
    fun testMoveAndMerge() = testAllDirections("2-2- -2-2 --2- ---2", "---4 ---4 ---2 ---2")

    @Test
    fun testMerge() = testAllDirections("2-22 2-42 22-2 ----", "--24 -242 --24 ----")

    private fun testAllDirections(inputString: String, expectedString: String, move: Boolean = true) {
        val input = TestBoard(inputString)
        val expected = TestBoard(expectedString)
        testRegularAndReversedDirections(RIGHT, input, expected, move)
        testRegularAndReversedDirections(DOWN, input.mirror(), expected.mirror(), move)
    }

    private fun testRegularAndReversedDirections(direction: Direction, input: TestBoard, expected: TestBoard, move: Boolean) {
        testMove(direction, input, expected, move)
        testMove(direction.reversed(), input.reversed(), expected.reversed(), move)
    }

    private fun testMove(direction: Direction, input: TestBoard, expected: TestBoard, expectedMove: Boolean) {
        val board = createBoard(input)
        val actualMove = board.moveValues(direction)
        val result = board.toTestBoard()
        Assert.assertEquals("Incorrect move to $direction.\n" +
                "Input:\n$input\n", expected, result)
        Assert.assertEquals("The 'moveValues' method returns incorrect result. Direction: $direction.\n" +
                "Input:\n$input\n", expectedMove, actualMove)
    }
}