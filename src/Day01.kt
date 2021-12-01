fun main() {
    fun countLargerMeasurements(input: List<String>): Int {
        return input.map { string ->
            string.toInt()
        }.filterIndexed { index, i ->
            if (index == 0) false // jump over the first measurement
            else input[index - 1].toInt() < i // check if prev is smaller than actual
        }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(countLargerMeasurements(testInput) == 7)

    val input = readInput("Day01")
    println(countLargerMeasurements(testInput))
    println(countLargerMeasurements(input))
}
