package com.example.android.guesstheword.screens.game

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class GameViewModel : ViewModel() {

    val word = MutableLiveData<String>()
    val score = MutableLiveData<Int>()
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        word.value = ""
        score.value = 0

        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        if (!wordList.isEmpty()) {
            //Select and remove a word from the list
            word.value = wordList.removeAt(0)
        }
    }

    public fun onSkip() {
        if (!wordList.isEmpty()) {
            score.value = (score.value)?.minus(1)
        }
        nextWord()
    }

    public fun onCorrect() {
        if (!wordList.isEmpty()) {
            score.value = (score.value)?.plus(1)
        }
        nextWord()
    }

}