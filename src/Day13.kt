enum class FoldAlong { X, Y }
data class FoldInstruction(val along: FoldAlong, val line: Int)

fun parseCords(input: List<String>) = input.filter { line ->
    line.matches("""[\d]+,[\d]+""".toRegex())
}.map { line ->
    val (x, y) = line.split(",").map(String::toInt)
    Vector(x, y) // Day05
}

fun parseFolds(input: List<String>) = input.filter { line ->
    line.matches("""fold along [xy]=[\d]+""".toRegex())
}.map { string ->
    val (along, line) = string
        .replace("fold along ", "")
        .split("=")

    val direction = when (along) {
        "x" -> FoldAlong.X
        "y" -> FoldAlong.Y
        else -> throw IllegalArgumentException()
    }

    FoldInstruction(direction, line.toInt())
}

fun foldPaper(cords: List<Vector>, fold: FoldInstruction): List<Vector> =
    when (fold.along) {
        FoldAlong.X -> {
            cords.filter { vec ->
                vec.x != fold.line
            }.map { vec ->
                Vector(
                    if (vec.x > fold.line) 2 * fold.line - vec.x else vec.x,
                    vec.y,
                )
            }
        }
        FoldAlong.Y -> {
            cords.filter { vec ->
                vec.y != fold.line
            }.map { vec ->
                Vector(
                    vec.x,
                    if (vec.y > fold.line) 2 * fold.line - vec.y else vec.y,
                )
            }.toSet()
        }
    }.toSet().toList()


fun main() {
    fun part1(input: List<String>): Int {
        val cords = parseCords(input)
        val folds = parseFolds(input)

        val result = foldPaper(cords, folds[0])

        return result.size
    }

    fun part2(input: List<String>): Int {
        val cords = parseCords(input)
        val folds = parseFolds(input)

        var result = cords
        for (fold in folds) result = foldPaper(result, fold)

        result.groupBy { vec -> vec.y }
            .toSortedMap()
            .forEach { (_, v) ->
                val x = v.map { vec -> vec.x }
                val max = x.maxOf { it }

                (0..max).forEach { i -> print(if (i in x) '#' else '.') }
                println()
            }

        return result.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput) == 16)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
