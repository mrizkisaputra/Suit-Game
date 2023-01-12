package com.mrizkisaputra.suitgame

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameTest {
    private val optionComputer: Set<String> = setOf("SCISSORS", "ROCK", "PAPER")
    private val optionDrawable: Map<String, Int> = mapOf(
        "SCISSORS" to R.drawable.scissors,
        "ROCK" to R.drawable.rock,
        "PAPER" to R.drawable.paper
    )


    @Test
    @RepeatedTest(value = 3, name = "[Test Case {currentRepetition}] - {displayName}")
    fun testComputerChoice() {
        assertAll("testing method computerChoice dengan skenario sukses",
            { assertTrue(
                Game.computerChoice() in optionComputer,
                "${Game.computerChoice()} tidak ada dalam collection option computer"
            ) },
            { assertNotNull(
                Game.computerChoice(),
                "sebaiknya tidak mengembalikan nilai null"
            ) }
        )
    }


    @ParameterizedTest(name = "[Test Case {index}] - parsing param {0}")
    @ValueSource(strings = ["SCISSORS", "ROCK", "PAPER"])
    fun testSelectOptionDrawable(value: String) {
        assertAll("testing method selectOptionDrawable dengan skenario sukses",
            { assertEquals(
                optionDrawable.get(value), Game.selectOptionDrawable(value),
            "sebaiknya mengembalikan hasil sesuai dengan yang diharapkan"
            ) },
            { assertNotNull(
                Game.selectOptionDrawable(value),
            "sebaiknya tidak mengembalikan nilai null") }
        )
    }

    @ParameterizedTest(name = "[Player] - {0} vs {1} seharusnya mengembalikan true")
    @CsvSource("SCISSORS, PAPER", "ROCK, SCISSORS", "PAPER, ROCK")
    fun testIsWinPlayer(player: String, computer: String) {
        assertAll("testing method isWin dengan hasil boolean true",
            { assertTrue(
                Game.isWin(player, computer)!!,
                "sebaiknya mengembalikan hasil boolen true"
            ) },
            { assertNotNull(
                Game.isWin(player, computer),
                "sebaiknya tidak mengembalikan hasil null"
            ) }
        )
    }

    @ParameterizedTest(name = "[Computer] - {0} vs {1} seharusnya mengembalikan false")
    @CsvSource("PAPER, SCISSORS", "SCISSORS, ROCK", "ROCK, PAPER")
    fun testIsWinComputer(player: String, computer: String) {
        assertAll("testing method isWin dengan hasil false",
            { assertFalse(
                Game.isWin(player, computer)!!,
                "sebaiknya mengembalikan hasil boolean false"
            ) }

        )
    }

    @ParameterizedTest(name = "[Test Case {index}] - {0} vs {1} sebaiknya mengembalikan true")
    @CsvSource("ROCK, ROCK", "PAPER, PAPER", "SCISSORS, SCISSORS")
    fun testIsDraw(player: String, computer: String) {
        assertAll("harus mengembalikan boolen true",
            { assertTrue(Game.isDraw(player, computer)) })
    }

}