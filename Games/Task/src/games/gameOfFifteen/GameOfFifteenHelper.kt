package games.gameOfFifteen

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * Copyright 2020, Gerardo Marquez.
 */

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {


    val indexes = (0 .. permutation.size).toList()

    val indexesPermutations = indexes.flatMap { home ->
        with(indexes) {
            takeLast(size - (indexOf(home) + 1))
        }.map { home to it }
    }
    val permutations = permutation.flatMap { home ->
        with(permutation) {
            takeLast(size - (indexOf(home) + 1))
        }.map { home to it }
    }

    val isEven = indexesPermutations.zip(permutations).count {
        it.first.first < it.first.second
                && it.second.first > it.second.second
    }

    return isEven % 2 == 0
}

fun <T : Any> List<T?>.moveAndSwapEmpty(): List<T?> {

    val merge = this.toMutableList()

    for( e in 1 until merge.size ) {

        if(merge[e] == null) {
            merge[e] = merge[e-1]
            merge[e-1] = null
        }
    }
    return merge.toList()
}