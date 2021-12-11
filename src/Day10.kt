import java.util.*

/**
 * Check the closing tag matches the opening tag.
 */
fun isSyntaxError(stack: Stack<String>, s: String): Boolean = when (s) {
    "(", "[", "{", "<" -> {
        stack.push(s)
        false
    }
    ")" -> stack.pop() != "("
    "]" -> stack.pop() != "["
    "}" -> stack.pop() != "{"
    ">" -> stack.pop() != "<"
    else -> false
}

fun illegalSignToScore(s: String): Int = when (s) {
    ")" -> 3
    "]" -> 57
    "}" -> 1197
    ">" -> 25137
    else -> 0
}

fun incompleteSignToScore(s: String): Int = when (s) {
    "(" -> 1
    "[" -> 2
    "{" -> 3
    "<" -> 4
    else -> 0
}

fun doStack(stack: Stack<String>, s: String): String = when (s) {
    "(", "[", "{", "<" -> stack.push(s)
    ")", "]", "}", ">" -> stack.pop()
    else -> ""
}

fun sumMissing(stack: Stack<String>): Long {
    var sum = 0L
    while (stack.isNotEmpty()) {
        sum = sum * 5 + incompleteSignToScore(stack.pop())
    }
    return sum
}

fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map { line -> line.chunked(1) }

        return lines.sumOf { line ->
            val stack = Stack<String>()
            line.firstOrNull { isSyntaxError(stack, it) }
                ?.let { illegalSignToScore(it) }
                ?: 0
        }
    }

    fun part2(input: List<String>): Long {
        val points = input.map { line ->
            line.chunked(1)
        }.filter { line ->
            val stack = Stack<String>()
            line.firstOrNull { isSyntaxError(stack, it) } == null
        }.map { line ->
            val stack = Stack<String>()
            line.forEach { s -> doStack(stack, s) }
            sumMissing(stack)
        }.sorted()
        return points[points.size ushr 1]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}