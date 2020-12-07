package games.gameOfFifteen

import org.junit.Assert
import org.junit.Test

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * Copyright 2020, Gerardo Marquez.
 */

class TestGameOfFifteenInitializer {
    @Test
    fun testInitialPermutationIsNotTrivial() {
        val initializer = RandomGameInitializer()
        Assert.assertNotEquals("The initial permutation must not be trivial",
            (1..15).toList(), initializer.initialPermutation)
    }

    @Test
    fun testInitialPermutationIsEven() {
        val initializer = RandomGameInitializer()
        Assert.assertNotEquals("The initial permutation must be even",
            isEven(initializer.initialPermutation))
    }
}