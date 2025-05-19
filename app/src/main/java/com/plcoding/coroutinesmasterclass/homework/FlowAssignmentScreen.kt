package com.plcoding.coroutinesmasterclass.homework

import android.icu.number.Scale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.coroutinesmasterclass.homework.flow.FlowViewModel

@Composable
fun FlowAssignmentScreen() {
    val viewModel = viewModel<FlowViewModel>()
    val countDown by viewModel.countDownText.collectAsStateWithLifecycle()


    Scaffold {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (countDown.isNotEmpty()) {
                Text(countDown)
            }
            Button(onClick = viewModel::startCountDown) {
                Text("Start")
            }
        }
    }
}