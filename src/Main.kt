//  global vars
var cardDeck = mutableListOf(
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
    2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
    3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
    4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
    5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
    6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
    7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
    8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
    9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
    10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
    11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
    12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -2, -2, -2, -2, -2)

class Game {
    val welcomeText = "\n########################################################\n" +
            "  SKYJO in Kotlin | Welcome to this wonderful cardgame\n" +
            "########################################################\n"
    var numPlayers   = 1
    var activeCards  = 12
    var activePlayer = 1
    var nextPlayer   = 1

    var ablegestapel   = mutableListOf<Int>()

    val playerMap  = mutableMapOf<Int, Player>()
    val playerHand = mutableMapOf<Int, Hand>()
}

class Player {
    var playerID: Int = 0
    var name: String  = "Name"
}

class Hand {
    var name: String  = "Name"
    var hand       = intArrayOf(0,0,0,0,0,0,0,0,0,0,0,0)
    var hiddenHand = arrayOf(true,true,true,true,true,true,true,true,true,true,true,true)

    fun showHand() {
        for (i in 0..3) {
            when(i) {
                0 -> println("    1 | 2 | 3 | 4")
                1 ->  {
                    print("A | ")
                    print(if(hiddenHand[0]) "X | " else "${hand[0]} | ")
                    print(if(hiddenHand[1]) "X | " else "${hand[1]} | ")
                    print(if(hiddenHand[2]) "X | " else "${hand[2]} | ")
                    print(if(hiddenHand[3]) "X\n" else "${hand[3]}\n")
                }
                2 -> {
                    print("B | ")
                    print(if(hiddenHand[4]) "X | " else "${hand[4]} | ")
                    print(if(hiddenHand[5]) "X | " else "${hand[5]} | ")
                    print(if(hiddenHand[6]) "X | " else "${hand[6]} | ")
                    print(if(hiddenHand[7]) "X\n" else "${hand[7]}\n")
                }
                else -> {
                    print("C | ")
                    print(if(hiddenHand[8]) "X | " else "${hand[8]} | ")
                    print(if(hiddenHand[9]) "X | " else "${hand[9]} | ")
                    print(if(hiddenHand[10]) "X | " else "${hand[10]} | ")
                    print(if(hiddenHand[11]) "X\n" else "${hand[11]}\n")
                }
            }
        }
    }
}

fun setActivePlayer(game: Game) {
    when(game.nextPlayer) {
        game.numPlayers -> 1
        else -> game.nextPlayer++
    }
    when(game.activePlayer) {
        game.numPlayers -> 1
        else -> game.activePlayer++
    }
}

fun queryNumPlayersUserInput(): Int {
    print("Wie viele Spieler sind heute dabei?\n" +
            "Eingabe: ")
    return try {
        val numPlayers = readln().toInt()
        println("\nHeute sind $numPlayers Spieler mit dabei. Ich find's geil!\n")
        numPlayers
    } catch (e: Exception) {
        queryNumPlayersUserInput()
    }
}

// wird zu Beginn benötigt und im Spielverlauf, wenn der Ziehstapel leer ist und aus dem Ablagestapel neu befüllt wird
fun shuffleDeck(cardDeck: MutableList<Int>) {
    return cardDeck.shuffle()
}

fun generatePlayers(game: Game) {
    var count = 1
    shuffleDeck(cardDeck)
    try {
        do {
            print("Der Name von Spieler $count lautet: ")
            val inputName = readln()

            game.playerMap[count] = Player()
            game.playerMap[count]!!.playerID = count
            game.playerMap[count]!!.name = inputName

            game.playerHand[count] = Hand()
            game.playerHand[count]!!.name = inputName
            for (i in 0..11) {
                game.playerHand[count]!!.hand[i] = cardDeck[i]
            }
            takeFromCardDeck(12)

            count++
        } while (count <= game.numPlayers)
    } catch (e: Exception) {
        generatePlayers(game)
    }
}

fun takeFromCardDeck(sumCards: Int) {
    for (i in 0..<sumCards) {
        cardDeck.removeFirst()
    }
}

fun turnCards(game: Game, player: Int, iterator: Int) {
    var i = 1
    if (game.activePlayer == 1) println("\nLasst uns das Spiel beginnen. ${game.playerMap[game.activePlayer]!!.name} ist als erste/r dran.\n" +
                "Du darfst zunächst zwei Karten aus deinem Spielfeld umdrehen. Wähle dafür nacheinander die Koordinaten, z.B. Zeile A und Spalte 1 für die Karte oben links.\n")
    else println("\nJetzt ist ${game.playerMap[i + 1]!!.name} dran. Du darfst dir jetzt auch zwei Karten aussuchen, die du umdrehen möchtest.\n")

    println("${game.playerMap[game.activePlayer]!!.name}s Kartendeck:")
    game.playerHand[game.activePlayer]!!.showHand()

    do {
        try {
            print("\nReihe  (A,B,C)   > ")
            val coordinateRow = readln()
            print("Spalte (1,2,3,4) > ")
            val coordinateCol = readln().toInt()

            when(coordinateRow) {
                "A" -> game.playerHand[player]!!.hiddenHand[coordinateCol - 1] = false
                "B" -> game.playerHand[player]!!.hiddenHand[coordinateCol + 3] = false
                "C" -> game.playerHand[player]!!.hiddenHand[coordinateCol + 7] = false
                else -> {
                    println("Du hast ungültige Koordinaten eingegeben. Das machst du jetzt nochmal, du Talahon!\n")
                    turnCards(game, game.activePlayer, iterator)
                }
            }
            game.playerHand[player]!!.showHand()
            i++
        } catch (e: Exception) {
            turnCards(game, game.activePlayer, iterator)
        }
    } while (i <= iterator)
}

fun getCard(game: Game, origin: String) {
    when(origin) {
        "A" -> {
            println("${game.playerHand[game.activePlayer]!!.name} Du hast die Karte [${game.ablegestapel.first()}] gezogen. Möchtest du sie behalten [b] oder ablegen [a]? Wenn du sie ablegen möchtest, nenne bitte zusätzlich die Koordinate." +
                    "> ")
            val userInput = readln()
            if (userInput == "b") {
                println("Behalten, super! Wohin möchtest du die Karte hinlegen? Erst den Buchstaben" +
                        "> ")
                val coordinateRow = readln()
                println("Und jetzt die Zahl" +
                        "> ")
                val coordinateCol = readln().toInt()
                when(coordinateRow) {
                    "A" -> {
                        when(game.playerHand[game.activePlayer]!!.hiddenHand[coordinateCol - 1]) {
                            true -> {
                                game.playerHand[game.activePlayer]!!.hiddenHand[coordinateCol - 1] = false
                                game.playerHand[game.activePlayer]!!.hand[coordinateCol - 1] = game.ablegestapel.first()
                                takeFromCardDeck(1)
                            }
                            else -> println("Test")// game.playerHand[game.activePlayer]!!.hand[coordinateCol - 1] auf den Ablagestapel
                        }
                    }
                    "B" -> {
                        game.playerHand[game.activePlayer]!!.hiddenHand[coordinateCol + 3] = false
                        game.playerHand[game.activePlayer]!!.hand[coordinateCol + 3] = game.ablegestapel.first()
                        takeFromCardDeck(1)
                    }
                    "C" -> {
                        game.playerHand[game.activePlayer]!!.hiddenHand[coordinateCol + 7] = false
                        game.playerHand[game.activePlayer]!!.hand[coordinateCol + 7] = game.ablegestapel.first()
                        takeFromCardDeck(1)
                    }
                    else -> {
                        println("Du hast ungültige Koordinaten eingegeben. Das machst du jetzt nochmal von vorne, du Talahon!\n")
                        getCard(game,"A")
                    }
                }
            } else if (userInput == "b") {
                // Karte auf Ablegestapel legen und eine Karte aus der Matrix umdrehen
                // Karte aus dem cardDeck nehmen
            }
        }
        "N" -> cardDeck.first()
    }
}

fun playerTurn(game: Game) {
    val textAblegestapel = if (game.ablegestapel.size > 0) " oder vom Ablegestapel [A]"
    else ""
    println("${game.playerMap[game.activePlayer]!!.name}, nimm dir eine Karte vom Nachziehstapel [N]${textAblegestapel}." +
            "> ")
    val playerTurnFrom = readln()

    when (playerTurnFrom) {
        "A" -> getCard(game, "A")
        "N" -> getCard(game, "N")       // Anzeigen und nach Entscheidung fragen; danach takeFromCardDeck(1)
    }

}


fun main() {
    val game = Game()
    println(game.welcomeText)

    game.numPlayers  = queryNumPlayersUserInput()
    generatePlayers(game)
    game.activeCards = game.numPlayers * 12
    if(game.activePlayer > 1) game.nextPlayer = 2

    repeat(game.numPlayers) {
        turnCards(game, game.activePlayer, 2)
        setActivePlayer(game)
    }

    // An dieser Stelle beginnt das eigentliche Spiel - Infos dazu auch in Skyjo.kt

    // später im Spiel
    // game.playerHand[1]!!.showHand()
}