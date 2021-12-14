data class Polymer(val group: String, val insert: String, var count: Long = 0L)

fun parseInsertions(input: List<String>) = input
    .subList(2, input.size)
    .map { s ->
        val (a, b) = s.split(" -> ")
        return@map a to b
    }

fun emptyTemplate(input: List<String>) = parseInsertions(input)
    .map { (group, inst) ->
        Polymer(group, inst)
    }.groupBy { p ->
        p.group
    }.mapValues { (_, v) ->
        v.first()
    }

fun insertToPolymer(
    template: String,
    insertions: List<Pair<String, String>>,
): String {
    val sb = StringBuilder("")
    for (i in 0 until template.length - 1) {
        val sub = template.subSequence(i, i + 2)
        val match = insertions.find { (a, _) -> sub == a }
        if (match == null) sb.append(template[i])
        else sb.append(template[i]).append(match.second)
    }
    return sb.append(template.last()).toString()
}

fun main() {
    fun part1(input: List<String>): Int {
        var template = input[0]
        val insertions = parseInsertions(input)

        repeat(10) {
            template = insertToPolymer(template, insertions)
        }

        val group = template.groupBy { it }.map { (k, v) -> k to v.size }.sortedBy { (_, v) -> v }
        return group.last().second - group.first().second
    }

    fun part2(input: List<String>): Long {
        val template = input[0]
        val empty = emptyTemplate(input)

        var result = empty.toMap()
        (0 until template.length - 1).forEach { i ->
            val sub = template.substring(i, i + 2)
            result[sub]!!.count += 1
        }

        repeat(40) {
            val tmp = emptyTemplate(input)

            result.forEach { (_, v) ->
                val s0 = v.group[0] + v.insert
                val s1 = v.insert + v.group[1]

                tmp[s0]!!.count += v.count
                tmp[s1]!!.count += v.count
            }
            result = tmp
        }

        // the last char from template is missing :(((
        val miss = template[template.lastIndex]
        result["$miss$miss"]!!.count += 1

        val group = result.map { (k, v) ->
            k to v.count
        }.groupBy { (k, _) ->
            k[0]
        }.map { (k, v) ->
            k to v.sumOf { (_, l) -> l }
        }.sortedBy { (_, v) -> v }

        return group.last().second - group.first().second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
