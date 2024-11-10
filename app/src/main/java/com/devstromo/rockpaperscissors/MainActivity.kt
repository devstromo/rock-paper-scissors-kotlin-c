package com.devstromo.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.devstromo.rockpaperscissors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button click listeners for the player's choice
        binding.stoneImageButton.setOnClickListener { playGame('s') }
        binding.buttonPaper.setOnClickListener { playGame('p') }
        binding.buttonScissors.setOnClickListener { playGame('z') }
    }

    private fun playGame(playerChoice: Char) {
        // Call the native function and display the result
        val result = playGameJNI(playerChoice)
        binding.resultText.text = result
    }

    external fun playGameJNI(playerChoice: Char): String

    companion object {
        init {
            System.loadLibrary("rockpaperscissors")
        }
    }
}
