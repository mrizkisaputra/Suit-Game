package com.mrizkisaputra.suitgame

import android.graphics.drawable.Drawable
import kotlin.random.Random

object Game {
    private val optionComputer: Set<String> = setOf("SCISSORS", "ROCK", "PAPER")

    private val optionDrawable: Map<String, Int> = mapOf(
        "SCISSORS" to R.drawable.scissors,
        "ROCK" to R.drawable.rock,
        "PAPER" to R.drawable.paper
    )

    private val gameRule: Map<String, Boolean> = mapOf(
        "SCISSORS-PAPER" to true,
        "SCISSORS-ROCK" to false,
        "ROCK-SCISSORS" to true,
        "ROCK-PAPER" to false,
        "PAPER-ROCK" to true,
        "PAPER-SCISSORS" to false,
    )

    fun computerChoice(): String {
        val random = Random.nextInt(3)
        return optionComputer.elementAt(random)
    }

    fun selectOptionDrawable(optionDrawable: String): Int {
        return this.optionDrawable.getOrDefault(optionDrawable, 0)
    }

    fun isWin(optionPlayer: String, optionComputer: String): Boolean? {
        return gameRule.get("$optionPlayer-$optionComputer")
    }

    fun isDraw(optionPlayer: String, optionComputer: String): Boolean {
        return optionPlayer == optionComputer
    }
}