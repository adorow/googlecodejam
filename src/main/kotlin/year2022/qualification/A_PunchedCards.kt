package year2022.qualification

fun main() {
    val T = readln().toInt()
    for (t in 1..T) {
        val (R, C) = readln().split(" ").map { it.toInt() }

        println("Case #$t:")
        for (r in 1..R) {
            val isFirst = r == 1

            var borderRow = "+-".repeat(C) + "+"
            var contentRow = "|.".repeat(C) + "|"

            if (isFirst) {
                borderRow = borderRow.replaceRange(0, 2, "..")
                contentRow = contentRow.replaceRange(0, 2, "..")
            }
            println(borderRow)
            println(contentRow)
        }
        val lastRow = "+-".repeat(C) + "+"
        println(lastRow)
    }
}
