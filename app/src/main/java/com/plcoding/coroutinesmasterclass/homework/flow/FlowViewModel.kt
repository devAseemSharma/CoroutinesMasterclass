package com.plcoding.coroutinesmasterclass.homework.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {

    private var _countDownText = MutableStateFlow("")
    val countDownText = _countDownText.asStateFlow()


    fun startCountDown() {
        viewModelScope.launch {
            flow<Int> {
                emit(10)
                var current = 10
                while (current > 0) {
                    delay(1_000)
                    current--
                    emit(current)
                }
            }.onCompletion {
                _countDownText.update {
                    "Countdown completed"
                }
            }.collect { values ->
                println("Values: $values")
                _countDownText.update {
                    "Countdown value: $values"
                }
            }
        }
    }

}