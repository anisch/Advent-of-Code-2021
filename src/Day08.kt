data class Segment(val segments: String, val digit: Int)

/**
 * Returns true if all chars from [a] are in [b]
 */
fun containsAllChars(a: String, b: String): Boolean {
    for (char in a) {
        if (char !in b) return false
    }
    return true
}

fun uniqueToDigit(uniques: List<String>): Array<Segment> {
    // sort chars from digit and sort it by length
    val tmp = sortCharsAndStrings(uniques).toMutableList()

    // the simple 1, 4, 7, 8
    val one = Segment(tmp.removeAt(0), 1)
    val seven = Segment(tmp.removeAt(0), 7)
    val four = Segment(tmp.removeAt(0), 4)
    val eight = Segment(tmp.removeAt(tmp.lastIndex), 8)

    // list contains  (2, 3, 5)=length:5  (0, 6, 9)=length:6
    // 7 in 3 and size 5
    val three = Segment(tmp.removeAt(tmp.indexOfFirst { s -> s.length == 5 && containsAllChars(seven.segments, s) }), 3)
    // 3 in 9 and size 6
    val nine = Segment(tmp.removeAt(tmp.indexOfFirst { s -> s.length == 6 && containsAllChars(three.segments, s) }), 9)
    // 7 in 0 and size 6
    val zero = Segment(tmp.removeAt(tmp.indexOfFirst { s -> s.length == 6 && containsAllChars(seven.segments, s) }), 0)
    // 6 left over size 6
    val six = Segment(tmp.removeAt(tmp.indexOfFirst { s -> s.length == 6 }), 6)
    // 5 in six
    val five = Segment(tmp.removeAt(tmp.indexOfFirst { s -> containsAllChars(s, six.segments) }), 5)
    // just num 2 left
    val two = Segment(tmp.removeAt(0), 2)

    return arrayOf(
        zero,
        one,
        two,
        three,
        four,
        five,
        six,
        seven,
        eight,
        nine,
    )
}

/**
 * Sorts the Strings by length, and the containing chars by value.
 */
fun sortCharsAndStrings(stringList: List<String>): List<String> {
    return stringList.map { string ->
        string.toCharArray()
    }.map { chars ->
        chars.sortedBy { it }
    }.map { chars ->
        chars.joinToString("")
    }.sortedBy { string ->
        string.length
    }
}

/**
 * Sorts the containing chars by value.
 */
fun sortChars(stringList: List<String>): List<String> {
    return stringList.map { string ->
        string.toCharArray()
    }.map { chars ->
        chars.sortedBy { it }
    }.map { chars ->
        chars.joinToString("")
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val (_, output) = line.split(" | ")
            return@map output
        }.flatMap { output ->
            output.split(" ")
        }.map { s ->
            s.length
        }.count { length ->
            length in 2..4 || length == 7
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val (uniques, output) = line.split(" | ")
            uniques.split(" ") to output.split(" ")
        }.map { (uniques, output) ->
            uniqueToDigit(uniques) to sortChars(output)
        }.sumOf { (segments, digits) ->
            digits.map { digit ->
                segments.find { segment -> segment.segments == digit }
            }.map { segment ->
                segment?.digit
            }.joinToString("").toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
