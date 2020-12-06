package games.gameOfFifteen

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


    val path = permutation.toMutableList()
    val permutations = permutation.flatMap { home ->
        path.remove(home)
        path.map { home to it }
    }

    val indexes = generateSequence(0) { it + 1 }
            .take(permutation.size).toList()

    val indexesPath = indexes.toMutableList()
    val indexesPermutations = indexes.flatMap { home ->
        indexesPath.remove(home)
        indexesPath.map { home to it }
    }

    val isEven = indexesPermutations.zip(permutations)
            .count { it.first.first < it.first.second &&
                    it.second.first > it.second.second
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