import kotlin.math.abs

fun calcFuel(steps: Int): Int = if (steps == 0) 0 else calcFuel(steps - 1) + steps

fun main() {
    fun part1(input: List<String>): Int {
        val craps = input[0].split(",").map { it.toInt() }

        val min = craps.minOf { it }
        val max = craps.maxOf { it }

        var fuel = Int.MAX_VALUE
        for (pos in min..max) {
            var sum = 0
            for (crap in craps.indices) {
                sum += abs(pos - craps[crap])
            }
            if (sum < fuel) fuel = sum
        }

        return fuel
    }

    fun part2(input: List<String>): Int {
        val craps = input[0].split(",").map { it.toInt() }

        val min = craps.minOf { it }
        val max = craps.maxOf { it }

        var fuel = Int.MAX_VALUE
        for (pos in min..max) {
            var sum = 0
            for (crap in craps.indices) {
                sum += calcFuel(abs(pos - craps[crap]))
            }
            if (sum < fuel) fuel = sum
        }

        return fuel
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
