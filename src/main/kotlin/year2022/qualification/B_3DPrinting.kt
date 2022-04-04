package year2022.qualification

import kotlin.math.min

const val neededInk = 1000000

fun main() {
    val T = readln().toInt()
    for (t in 1..T) {
        var cmyk =
            (1..3).map {
                readln().split(" ").map { it.toInt() }
            }.reduce { acc, ints ->
                listOf(
                    min(acc[0], ints[0]),
                    min(acc[1], ints[1]),
                    min(acc[2], ints[2]),
                    min(acc[3], ints[3]),
                )
            }

        val usedInk = cmyk.sum()
        if (usedInk < neededInk) {
            println("Case #$t: IMPOSSIBLE")
        } else {
            // too much ink maybe, let's reduce it to what we need
            var excessInk = usedInk - neededInk
            cmyk = cmyk.map {
                val toReduce = min(it, excessInk)
                excessInk -= toReduce // maybe can do better without this mutation here
                it - toReduce
            }

            val (c, m, y, k) = cmyk
            println("Case #$t: $c $m $y $k")
        }
    }
}
