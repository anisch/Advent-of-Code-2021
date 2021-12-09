sealed class Point
data class LowPoint(val x: Int, val y: Int, val h: Int) : Point()
object HighPoint : Point()

class Tree(val lowPoint: Node<Point>)
class Node<Point>(val p: Point) {
    var l: Node<Point>? = null
    var r: Node<Point>? = null
    var t: Node<Point>? = null
    var b: Node<Point>? = null
}

fun createHeightMap(input: List<String>): Array<IntArray> {
    val tmp = input.map { string ->
        val ints = string.chunked(1).map { it.toInt() }
        intArrayOf(9) + ints + 9
    }
    val help = IntArray(tmp[0].size) { 9 }
    return arrayOf(help) + tmp + help
}

fun isLowestPoint(map: Array<IntArray>, x: Int, y: Int): Point {
    val point = LowPoint(x, y, map[y][x])
    return when {
        point.h >= map[y + 1][x] -> HighPoint
        point.h >= map[y - 1][x] -> HighPoint
        point.h >= map[y][x + 1] -> HighPoint
        point.h >= map[y][x - 1] -> HighPoint
        else -> point
    }
}

fun findAllLowPoints(heightmap: Array<IntArray>): MutableList<LowPoint> {
    val lowPoints = mutableListOf<LowPoint>()
    for (y in 1 until heightmap.size - 1) {
        for (x in 1 until heightmap[0].size - 1) {
            when (val lowPoint = isLowestPoint(heightmap, x, y)) {
                is LowPoint -> lowPoints += lowPoint
                else -> continue
            }
        }
    }
    return lowPoints
}

fun countTreePoints(lowPoint: LowPoint, map: Array<IntArray>): Int {
    val tree = Tree(Node(lowPoint))
    createPoints(tree.lowPoint, map)
    return countPoints(tree)
}

/**
 * Create the points for the basim.
 */
fun createPoints(node: Node<Point>, map: Array<IntArray>) {
    val p = node.p as LowPoint
    val hl = map[p.y][p.x - 1]
    val hr = map[p.y][p.x + 1]
    val ht = map[p.y + 1][p.x]
    val hb = map[p.y - 1][p.x]

    if (p.h < hl && hl < 9) {
        node.l = Node(LowPoint(p.x - 1, p.y, hl))
        createPoints(node.l!!, map)
    }
    if (p.h < hr && hr < 9) {
        node.r = Node(LowPoint(p.x + 1, p.y, hr))
        createPoints(node.r!!, map)
    }
    if (p.h < ht && ht < 9) {
        node.t = Node(LowPoint(p.x, p.y + 1, ht))
        createPoints(node.t!!, map)
    }
    if (p.h < hb && hb < 9) {
        node.b = Node(LowPoint(p.x, p.y - 1, hb))
        createPoints(node.b!!, map)
    }
}

/**
 * Count all points from the basin and drop the duplicates.
 */
fun countPoints(tree: Tree): Int {
    return collectNode(tree.lowPoint)
        .map { it.p as LowPoint }
        .toSet()
        .size
}

fun collectNode(node: Node<Point>): List<Node<Point>> {
    val points = mutableListOf(node)
    if (node.l != null) {
        points += collectNode(node.l!!)
    }
    if (node.r != null) {
        points += collectNode(node.r!!)
    }
    if (node.t != null) {
        points += collectNode(node.t!!)
    }
    if (node.b != null) {
        points += collectNode(node.b!!)
    }
    return points
}

fun main() {
    fun part1(input: List<String>): Int {
        return findAllLowPoints(createHeightMap(input))
            .sumOf { point -> point.h + 1 }
    }

    fun part2(input: List<String>): Int {
        val heightmap = createHeightMap(input)
        return findAllLowPoints(heightmap)
            .map { low -> countTreePoints(low, heightmap) }
            .sorted()
            .reversed()
            .take(3)
            .reduce { acc, h -> acc * h }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
