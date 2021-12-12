data class Octopus(var energy: Int, var isFlashing: Boolean = false)

fun readOctopusMap(input: List<String>): List<List<Octopus>> {
    val dead = Octopus(Int.MIN_VALUE)
    val tmp = input.map { line ->
        val row = line.chunked(1).map { s -> Octopus(s.toInt()) }
        listOf(dead) + row + dead
    }
    val deadRow = buildList { repeat(tmp.size + 2) { add(dead) } }

    return listOf(deadRow) + tmp + listOf(deadRow)
}

fun energyUp(octos: List<List<Octopus>>) {
    (1 until octos.size - 1).forEach { y ->
        (1 until octos[0].size - 1).forEach { x ->
            val octo = octos[y][x]
            if (octo.energy > 9) octo.energy = 0
            else octos[y][x].energy += 1
        }
    }
}

fun flash(octos: List<List<Octopus>>) {
    while (octos.any { row -> row.any { octo -> octo.energy > 9 && !octo.isFlashing } }) {
        (1 until octos.size - 1).forEach { y ->
            (1 until octos[0].size - 1).forEach { x ->
                val octo = octos[y][x]
                if (octo.energy > 9 && !octo.isFlashing) {
                    octo.isFlashing = true
                    flashEnergyUp(octos, x, y)
                }
            }
        }
    }
}

fun flashEnergyUp(octos: List<List<Octopus>>, x: Int, y: Int) {
    octos[y - 1][x - 1].energy += 1
    octos[y - 1][x].energy += 1
    octos[y - 1][x + 1].energy += 1
    octos[y][x - 1].energy += 1
    octos[y][x + 1].energy += 1
    octos[y + 1][x - 1].energy += 1
    octos[y + 1][x].energy += 1
    octos[y + 1][x + 1].energy += 1
}

fun resetFlash(octos: List<List<Octopus>>): Int {
    var flashes = 0
    (1 until octos.size - 1).forEach { y ->
        (1 until octos[0].size - 1).forEach { x ->
            val octo = octos[y][x]
            if (octo.isFlashing) {
                octo.isFlashing = false
                octo.energy = 0
                flashes += 1
            }
        }
    }
    return flashes
}

fun allOctosFlashing(octos: List<List<Octopus>>): Boolean =
    (1 until octos.size - 1).all { y ->
        (1 until octos[0].size - 1).all { x ->
            octos[y][x].energy == 0
        }
    }

fun main() {
    fun part1(input: List<String>): Int {
        val octos = readOctopusMap(input)

        var flashes = 0
        repeat(100) {
            energyUp(octos)
            flash(octos)
            flashes += resetFlash(octos)
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        val octos = readOctopusMap(input)
        var steps = 0
        do {
            energyUp(octos)
            flash(octos)
            resetFlash(octos)
            steps++
        } while (!allOctosFlashing(octos))

        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
