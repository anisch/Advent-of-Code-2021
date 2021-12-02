enum class Direction { UP, DOWN, FORWARD }
data class Position(
    val x: Int, // horizontal
    val y: Int, // depth
)

data class AimPosition(
    val x: Int, // horizontal
    val y: Int, // depth
    val aim: Int,
)

fun main() {
    fun parseDirection(s: String): Direction {
        return when (s) {
            "up" -> Direction.UP
            "down" -> Direction.DOWN
            else -> Direction.FORWARD
        }
    }

    fun part1(input: List<String>): Int {
        return input.map { line ->
            val (s, i) = line.split(" ")
            parseDirection(s) to i.toInt()
        }.map { (cmd, unit) ->
            when (cmd) {
                Direction.UP -> Position(0, -unit)
                Direction.DOWN -> Position(0, unit)
                Direction.FORWARD -> Position(unit, 0)
            }
        }.reduce { acc, pos ->
            Position(acc.x + pos.x, acc.y + pos.y)
        }.let { pos -> pos.x * pos.y }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val (s, i) = line.split(" ")
            parseDirection(s) to i.toInt()
        }.map { (cmd, unit) ->
            when (cmd) {
                Direction.UP -> cmd to AimPosition(0, 0, -unit)
                Direction.DOWN -> cmd to AimPosition(0, 0, unit)
                Direction.FORWARD -> cmd to AimPosition(unit, 0, 0)
            }
        }.reduce { (_, acc), (cmd, pos) ->
            when (cmd) {
                Direction.UP -> cmd to AimPosition(acc.x + pos.x, acc.y + pos.y, acc.aim + pos.aim)
                Direction.DOWN -> cmd to AimPosition(acc.x + pos.x, acc.y + pos.y, acc.aim + pos.aim)
                Direction.FORWARD -> cmd to AimPosition(acc.x + pos.x, acc.y + acc.aim * pos.x, acc.aim)
            }
        }.let { (_, pos) -> pos.x * pos.y }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}