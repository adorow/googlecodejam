package year2022.qualification

import kotlin.math.max

// !! only test set 1 works (see notes below)
fun main() {
    val T = readln().toInt()
    for (t in 1..T) {
        val N = readln().toInt()
        val F = readln().split(" ").map { it.toLong() }
        val P = readln().split(" ").map { it.toInt() }

        val pSet = P.toSet() // only used for fast lookup in (`)it !in pSet) - important for large datasets

        val inverseP = P.mapIndexed { index, p ->
            (index + 1/*0 should be 1*/ to p)
        }
            .filter { (index, p) -> p > 0 }
            .groupBy(
                keySelector = { (index, p) -> p },
                valueTransform = { (index, p) -> index }
            )
        // let's start navigating from the end, and then divide when we find the forks
        val endings = (1..N).filter { P[it - 1] == 0 }
        val initiators = (1..N)
            .filter { it !in pSet }

        val maxFromStartUntil = buildMaxFromStart(N, F, P, initiators)

        val maxFunPerEnding =
            endings.map {
                findMaxFun(endIndex = it, f = F, inverseP = inverseP, maxFromStartUntil = maxFromStartUntil)
            }

        val maxFun = maxFunPerEnding.sum() // sum all the results

        println("Case #$t: $maxFun")
    }
}

fun findMaxFun(
    carryFunValue: Long = 0,
    endIndex: Int,
    f: List<Long>,
    inverseP: Map<Int, List<Int>>,
    maxFromStartUntil: Array<Long>
): Long {
    val children = inverseP[endIndex]
    val thisFunValue = f[endIndex - 1]

    val accFunValue = max(carryFunValue, thisFunValue)

    // end of the trip
    if (children == null || children.isEmpty()) {
        return accFunValue
    }
    // there is only one to follow
    if (children.size == 1) {
        return findMaxFun(
            carryFunValue = accFunValue,
            endIndex = children[0],
            f = f,
            inverseP = inverseP,
            maxFromStartUntil = maxFromStartUntil
        )
    }

    // it's a fork in the road (more than one connecting to this node)
    // val untilChildMap = children.map {
    //     it to maxFromStartUntil[it - 1]
    // }.sortedBy { (_, funUntil) ->
    //     funUntil
    // }
    //
    // // -> the logic below might fail in some scenarios where there's a clash between 1st and 2nd (and more)
    // val funPerBranch = untilChildMap.mapIndexed { i, (pos, maxFromStart) ->
    //     if (i == 0) {
    //         findMaxFun(
    //             carryFunValue = accFunValue,
    //             endIndex = pos,
    //             f = f,
    //             inverseP = inverseP,
    //             maxFromStartUntil = maxFromStartUntil
    //         )
    //     } else {
    //         findMaxFun(
    //             endIndex = pos,
    //             f = f,
    //             inverseP = inverseP,
    //             maxFromStartUntil = maxFromStartUntil
    //         )
    //     }
    // }
    //
    // return funPerBranch.sum()

    // the solution below is too slow (too many loops checking 'optimalFunSum') already for testset 2
    // it's a fork in the road (more than one connecting to this node)
    val untilChildMap = children.map {
        it to maxFromStartUntil[it - 1]
    }.sortedBy { (_, funUntil) ->
        funUntil
    }

    val minFun = untilChildMap.minOf { it.second }

    // -> the logic below might fail in some scenarios where there's a clash between 1st and 2nd (and more)
    val optimalFunSum = children.indices.maxOf { iter ->
        untilChildMap.mapIndexed { i, (pos, maxFromStart) ->
            if (i == iter) {
                findMaxFun(
                    carryFunValue = accFunValue,
                    endIndex = pos,
                    f = f,
                    inverseP = inverseP,
                    maxFromStartUntil = maxFromStartUntil
                )
            } else {
                findMaxFun(
                    endIndex = pos,
                    f = f,
                    inverseP = inverseP,
                    maxFromStartUntil = maxFromStartUntil
                )
            }
        }.sum()
    }
    return optimalFunSum
}

fun buildMaxFromStart(n: Int, f: List<Long>, p: List<Int>, initiators: List<Int>): Array<Long> {
    val queue = mutableListOf<Pair<Int, Long>>()
    initiators.forEach {
        queue.add(it to 0)
    }

    val maxFromStart = Array<Long>(n) { 0 }

    while (queue.isNotEmpty()) {
        val (i, currentFun) = queue.removeFirst()
        val newFun = max(currentFun, f[i - 1])
        if (newFun > maxFromStart[i - 1]) {
            maxFromStart[i - 1] = newFun
            if (p[i - 1] > 0) { // is there a next?
                queue.add(p[i - 1] to newFun)
            }
        }
    }

    return maxFromStart
}
