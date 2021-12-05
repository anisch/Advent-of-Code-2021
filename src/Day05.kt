data class Vector(val x: Int, val y: Int)
data class Line(val a: Vector, val b: Vector)

fun main() {
    fun part1(input: List<String>): Int {
        val coordSystem = Array(1000) { Array(1000) { 0 } }

        input.map { string ->
            val (a, b) = string.split(" -> ")
            val (x1, y1) = a.split(",").map(String::toInt)
            val (x2, y2) = b.split(",").map(String::toInt)
            Line(Vector(x1, y1), Vector(x2, y2))
        }.filter { (a, b) -> // filter lines vec.x1 == vec.x2 || vec.y1 == vec.y2
            a.x == b.x || a.y == b.y
        }.forEach { line -> // draw line
            var a = line.a
            var b = line.b

            if (a.y == b.y) { // draw horizontal line
                if (a.x > b.x) a = b.also { b = a }
                for (i in a.x..b.x) coordSystem[i][a.y] += 1
            } else { // draw vertikal line
                if (a.y > b.y) a = b.also { b = a }
                for (j in a.y..b.y) coordSystem[a.x][j] += 1
            }
        }
        return coordSystem.sumOf { row -> row.count { cell -> cell >= 2 } }
    }

    fun part2(input: List<String>): Int {
        val coordSystem = Array(1000) { Array(1000) { 0 } }

        input.map { string ->
            val (a, b) = string.split(" -> ")
            val (x1, y1) = a.split(",").map(String::toInt)
            val (x2, y2) = b.split(",").map(String::toInt)
            Line(Vector(x1, y1), Vector(x2, y2))
        }.forEach { line -> // draw line
            var a = line.a
            var b = line.b

            when {
                a.y == b.y -> { // draw horizontal line
                    if (a.x > b.x) a = b.also { b = a }
                    for (i in a.x..b.x) coordSystem[i][a.y] += 1
                }
                a.x == b.x -> { // draw vertikal line
                    if (a.y > b.y) a = b.also { b = a }
                    for (j in a.y..b.y) coordSystem[a.x][j] += 1
                }
                else -> { // draw diagonal line
                    if (a.x > b.x) a = b.also { b = a }
                    val steps = b.x - a.x
                    for (k in 0..steps) {
                        if (a.y < b.y) coordSystem[a.x + k][a.y + k] += 1
                        else coordSystem[a.x + k][a.y - k] += 1
                    }
                }
            }
        }
        return coordSystem.sumOf { row -> row.count { cell -> cell >= 2 } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
