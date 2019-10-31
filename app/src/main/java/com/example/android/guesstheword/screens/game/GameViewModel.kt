package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

public class GameViewModel : ViewModel() {

    companion object {
        private const val DONE = 0L
        private const val INTERVAL = 1000L
        private const val COUNTDOWN_TIME = 60000L
    }

    // What we're doing here is encapsulating the MutableLiveData objects. To prevent anything but the VM from modifying these guys
    // We add backing properties to allow read access via LiveData by overriding the default get() method
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    private val timer: CountDownTimer
    private lateinit var wordList: MutableList<String>
    val currentTimeString = Transformations.map(currentTime) {
        time -> DateUtils.formatElapsedTime(time)
    }

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        _word.value = ""
        _score.value = 0

        resetList()
        nextWord()

        timer = object : CountDownTimer(COUNTDOWN_TIME, INTERVAL) {
            override fun onTick(milliSecondsUntilFinished: Long) {
                _currentTime.value = milliSecondsUntilFinished/INTERVAL
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinished()
            }
        }

        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
        timer.cancel()
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
        if (wordList.isEmpty()) {
            resetList()
        } else {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }
    }

    public fun onSkip() {
        if (!wordList.isEmpty()) {
            _score.value = (score.value)?.minus(1)
        }
        nextWord()
    }

    public fun onCorrect() {
        if (!wordList.isEmpty()) {
            _score.value = (score.value)?.plus(1)
        }
        nextWord()
    }

    fun onGameFinished() {
        _eventGameFinished.value = true
    }

    fun onGameFinishedComplete() {
        // we're setting it back to false to prevent from observers from being updated every time said observers
        // change from an inactive to active state (which would trigger those methods)
        // when a fragment is re-created after screen rotation, it moves from inactive to active
        _eventGameFinished.value = false
    }

}