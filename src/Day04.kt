data class BingoCell(
    val num: Int,
    var check: Boolean = false,
)

data class BingoRow(val data: List<BingoCell>)
data class BingoCard(val data: List<BingoRow>)

fun parseBingoCards(
    input: List<String>,
): List<BingoCard> {
    val bingoCards = mutableListOf<BingoCard>()
    val countCards = input.size / 6

    for (i in 0 until countCards) {
        val rows = mutableListOf<BingoRow>()

        for (j in 0 until 5) {
            val tmp = input[1 + (i * 6) + (j + 1)].chunked(3)
                .map { string -> string.trim().toInt() }
                .map { int -> BingoCell(int) }
            rows += BingoRow(tmp)
        }

        bingoCards += BingoCard(rows)
    }

    return bingoCards
}

fun fillBingoCards(bingoCards: List<BingoCard>, num: Int) {
    for (card in bingoCards) {
        for (row in card.data) {
            for (cell in row.data) {
                if (cell.num == num) {
                    cell.check = true
                }
            }
        }
    }
}

/**
 * Returns the first [BingoCard] which has a bingo.
 */
fun findBingo(bingoCards: List<BingoCard>): BingoCard? {
    for (card in bingoCards) {
        for (row in card.data) {
            val bingo = row.data.all { cell -> cell.check }
            if (bingo) return card
        }
        for (j in card.data.indices) {
            val col = mutableListOf<BingoCell>()
            for (row in card.data) col += row.data[j]
            val bingo = col.all { cell -> cell.check }
            if (bingo) return card
        }
    }
    return null
}

/**
 * Returns all [BingoCard] which has a bingo.
 */
fun findAllBingo(bingoCards: List<BingoCard>): List<BingoCard> {
    val winCards = mutableListOf<BingoCard>()

    cards@ for (card in bingoCards) {
        for (row in card.data) {
            val bingo = row.data.all { cell -> cell.check }
            if (bingo) {
                winCards += card
                continue@cards
            }
        }
        for (j in card.data.indices) {
            val col = mutableListOf<BingoCell>()
            for (row in card.data) col += row.data[j]
            val bingo = col.all { cell -> cell.check }
            if (bingo) {
                winCards += card
                continue@cards
            }
        }
    }
    return winCards
}

fun findSumUnchecked(card: BingoCard): Int = card.data.sumOf { row ->
    row.data.sumOf { cell ->
        if (!cell.check) cell.num else 0
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val bingoNums = input[0].split(",").map(String::toInt)
        val bingoCards = parseBingoCards(input)

        var card: BingoCard? = null
        var winNum: Int? = null
        for (num in bingoNums) {
            fillBingoCards(bingoCards, num)
            card = findBingo(bingoCards)
            if (card != null) {
                winNum = num
                break
            }
        }

        return findSumUnchecked(card!!) * winNum!!
    }

    fun part2(input: List<String>): Int {
        val bingoNums = input[0].split(",").map(String::toInt)
        val bingoCards = parseBingoCards(input) as MutableList

        var card: BingoCard? = null
        var winNum: Int? = null
        for (num in bingoNums) {
            fillBingoCards(bingoCards, num)
            val winCards = findAllBingo(bingoCards)
            if (bingoCards.size == 1 && winCards.size == 1) {
                winNum = num
                card = winCards[0]
                break
            }
            bingoCards -= winCards.toSet()
        }
        return findSumUnchecked(card!!) * winNum!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
