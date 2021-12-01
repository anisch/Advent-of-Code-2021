fun main() {
    fun countLargerMeasurementsPart1(input: List<String>): Int {
        return input.map { string ->
            string.toInt()
        }.filterIndexed { index, i ->
            if (index == 0) false // jump over the first measurement
            else input[index - 1].toInt() < i // check if prev is smaller than actual
        }.size
    }

    fun countLargerMeasurementsPart2(input: List<String>): Int {
        val tripleList = List(input.size - 2) { index ->
            input[index].toInt() + input[index+1].toInt() + input[index+2].toInt()
        }
        return tripleList.filterIndexed { index, i ->
            if (index == 0) false
            else tripleList[index - 1] < i
        }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(countLargerMeasurementsPart1(testInput) == 7)
    check(countLargerMeasurementsPart2(testInput) == 5)

    val input = readInput("Day01")
    println(countLargerMeasurementsPart1(input))
    println(countLargerMeasurementsPart2(input))
}
