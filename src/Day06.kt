data class Fish(
    var reProdDay: Int,
) {
    var amount: Long = 0L // sum of fish with the same reproduction day
}

fun mapInputAsFishList(input: List<String>) = input[0]
    .split(",")
    .map { it.toInt() }
    .groupBy { it }
    .map { (k, v) -> Fish(k).apply { amount = v.size.toLong() } }
    .sortedBy { it.reProdDay }
    .asReversed()

fun reproduce(fishList: List<Fish>): List<Fish> {
    val tmp = mutableListOf<Fish>()
    for (f in fishList.indices) {
        val fish = fishList[f]
        if (fish.reProdDay == 0) {
            // add the new fish
            tmp += Fish(8).apply { amount = fish.amount }
            // find young fish
            val idx = tmp.indexOfFirst { it.reProdDay == 6 }
            if (idx == -1) {
                fish.reProdDay = 6
            } else {
                tmp[idx].amount += fish.amount
                continue
            }
        } else {
            fish.reProdDay -= 1
        }
        tmp += fish
    }
    return tmp.sortedBy { it.reProdDay }.asReversed()
}

fun main() {
    fun part1(input: List<String>): Long {
        var fishList = mapInputAsFishList(input)

        repeat(80) { fishList = reproduce(fishList) }

        return fishList.sumOf { it.amount }
    }

    fun part2(input: List<String>): Long {
        var fishList = mapInputAsFishList(input)

        repeat(256) { fishList = reproduce(fishList) }

        return fishList.sumOf { it.amount }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
