/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProviders.of")
        // always use a VM Provider instead of instantiating a viewmodel directly cuz
        // that always happens. A provider returns an existing vm if one exists or creates one if doesn't
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        // this observer receives an event when the data held by the LiveData changes
        // also, essentially when the values of these changes, the displayed score/value text on the screen automatically updates
        // because they're observing the values via the LiveData they're wrapped in.
        viewModel.eventGameFinished.observe(this, Observer<Boolean> { hasFinished -> if (hasFinished) gameFinished() })

        // here we data binded the viewmodel of this fragment to the one listed in the view (game_fragment.xml) to have direct communication between the VM and View
        // Thereafter, we can use listener bindings to change certain view elements based on the logic defined in vm via typical events such as onClick, onZoomIn, etc.
        binding.gameViewModel = viewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun gameFinished() {
        Toast.makeText(activity, "Game finished.", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishedComplete()
    }

}
