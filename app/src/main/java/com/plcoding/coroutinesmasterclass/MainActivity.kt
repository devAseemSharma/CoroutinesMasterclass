package com.plcoding.coroutinesmasterclass

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.plcoding.coroutinesmasterclass.sections.homework.LeaderBoard
import com.plcoding.coroutinesmasterclass.sections.homework.LeaderboardListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leaderboard.addLeaderBoardListener(leaderBoardListener)
        GlobalScope.launch {
            (1..5_000).map { index ->
                launch {
                    val playerName = "Player $index"
                    val playerScore = Random.nextInt(1, 10_0000)
                    leaderboard.addNewScore(playerScore, playerName)
                }
            }.joinAll()
            println("Completed!")
        }
    }
    // USE THIS TO RUN YOUR Leaderboard CLASS
    private val leaderboard = LeaderBoard()
    private val leaderBoardListener =
        LeaderboardListener { leaderboard ->
            println("New Top Scores:")
            println(leaderboard + "\n\n")
        }

    override fun onDestroy() {
        leaderboard.removeLeaderBoardListener(leaderBoardListener)
        super.onDestroy()
    }
}