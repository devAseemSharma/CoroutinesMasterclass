package com.plcoding.coroutinesmasterclass.sections.homework

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LeaderBoard @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO.limitedParallelism(
            1
        ) + SupervisorJob()
    )
) {

    private val listeners = mutableListOf<LeaderboardListener>()
    private val allScores = mutableMapOf<String, Int>(
        "Mani" to 45,
        "Alex" to 100,
        "Peter" to 300,
    )

    fun addLeaderBoardListener(listener: LeaderboardListener) {
        coroutineScope.launch {
            listeners.add(listener)
        }
    }

    fun removeLeaderBoardListener(listener: LeaderboardListener) {
        coroutineScope.launch {
            listeners.remove(listener)
        }
    }

    fun addNewScore(score: Int, playerName: String) {
        coroutineScope.launch {
            allScores[playerName] = score
            updatedTopThreeInLeaderBoard()
        }
    }

    private fun updatedTopThreeInLeaderBoard() {
        val topThree = allScores
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .withIndex()
            .joinToString("\n") { (index, entry) ->
                "#${index + 1} is ${entry.key} with ${entry.value} points"
            }
        notifyAllLeaderBoardListeners(topThree)
    }

    private fun notifyAllLeaderBoardListeners(result: String) {
        listeners.forEach {
            it.onLeaderboardUpdated(result)
        }
    }
}