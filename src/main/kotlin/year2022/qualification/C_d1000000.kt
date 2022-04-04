package year2022.qualification

fun main() {
    // val T = 1
    // for (t in 1..T) {
    //     val N = 1000000
    //     val S = (1..1000000).toList()
    val T = readln().toInt()
    for (t in 1..T) {
        val N = readln().toInt()
        val S = readln().split(" ").map { it.toInt() }.sorted()

        var len = 0
        S.forEach {
            if (it > len) len++
        }

        println("Case #$t: $len")
    }
}
