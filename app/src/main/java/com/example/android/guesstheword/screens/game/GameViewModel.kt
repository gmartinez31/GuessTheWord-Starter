package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

public class GameViewModel : ViewModel() {

    private var word = ""
    private var score = 0
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
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
            word = wordList.removeAt(0)
        }
        updateWordText()
        updateScoreText()
    }

    private fun updateWordText() {
        binding.wordText.text = word
    }

    private fun updateScoreText() {
        binding.scoreText.text = score.toString()
    }

    public fun onSkip() {
        if (!wordList.isEmpty()) {
            score--
        }
        nextWord()
    }

    public fun onCorrect() {
        if (!wordList.isEmpty()) {
            score++
        }
        nextWord()
    }

}