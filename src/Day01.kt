fun main() {
    fun part1(input: List<String>): Int {
        return input.map { string ->
            string.toInt()
        }.filterIndexed { index, i ->
            if (index == 0) false // jump over the first measurement
            else input[index - 1].toInt() < i // check if prev is smaller than actual
        }.size
    }

    fun part2(input: List<String>): Int {
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
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
