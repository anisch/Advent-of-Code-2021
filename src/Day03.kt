fun main() {
    fun part1(input: List<String>): Int {
        val tmp = input.map { string ->
            string.toCharArray().map { it.digitToInt() }
        }.reduce { acc, ints ->
            acc.mapIndexed { index, i -> i + ints[index] }
        }

        val gamma = tmp.map { int ->
            if (int > input.size ushr 1) 1 else 0
        }.map { int ->
            int.toString()
        }.reduce { acc, s ->
            acc + s
        }.toInt(2)

        val epsilon = tmp.map { int ->
            if (int < input.size ushr 1) 1 else 0
        }.map { int ->
            int.toString()
        }.reduce { acc, s ->
            acc + s
        }.toInt(2)

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val tmp = input.map { string ->
            string.toCharArray().map { it.digitToInt() }
        }

        var oxygenList = tmp
        for (i in tmp[0].indices) {
            if (oxygenList.size == 1) break

            val countOne = oxygenList.reduce { acc, ints ->
                acc.mapIndexed { index, i -> i + ints[index] }
            }[i]

            oxygenList = oxygenList.filter { list ->
                val countZero = oxygenList.size - countOne
                if (countOne >= countZero) list[i] == 1
                else list[i] == 0
            }
        }

        var co2List = tmp
        for (i in tmp[0].indices) {
            if (co2List.size == 1) break

            val countOne = co2List.reduce { acc, ints ->
                acc.mapIndexed { index, i -> i + ints[index] }
            }[i]

            co2List = co2List.filter { list ->
                val countZero = co2List.size - countOne
                if (countOne < countZero) list[i] == 1
                else list[i] == 0
            }
        }

        val oxygen = oxygenList[0].map { int ->
            int.toString()
        }.reduce { acc, s ->
            acc + s
        }.toInt(2)

        val co2 = co2List[0].map { int ->
            int.toString()
        }.reduce { acc, s ->
            acc + s
        }.toInt(2)

        return oxygen * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}