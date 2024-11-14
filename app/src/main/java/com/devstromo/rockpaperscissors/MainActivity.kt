package com.devstromo.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.devstromo.rockpaperscissors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var playerOption: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stoneImageButton.setOnClickListener {
            onButtonSelected(
                binding.stoneImageButton,
                's'
            )
        }
        binding.paperImageButton.setOnClickListener {
            onButtonSelected(
                binding.paperImageButton,
                'p'
            )
        }
        binding.scissorsImageButton.setOnClickListener {
            onButtonSelected(
                binding.scissorsImageButton,
                'z'
            )
        }
    }

    private fun onButtonSelected(selectedButton: ImageButton, playerChoice: Char) {
        binding.stoneImageButton.isSelected = false
        binding.paperImageButton.isSelected = false
        binding.scissorsImageButton.isSelected = false

        selectedButton.isSelected = true
        setPlayerOptionSelection(playerChoice)
        playGame(playerChoice)
    }

    private fun playGame(playerChoice: Char) {
        // Call the native function and display the result
        val result = playGameJNI(playerChoice)
        binding.resultText.text = result
    }

    private fun setPlayerOptionSelection(playerChoice: Char) {
        playerOption = playerChoice
    }

    external fun playGameJNI(playerChoice: Char): String

    companion object {
        init {
            System.loadLibrary("rockpaperscissors")
        }
    }
    //if (result == -1) {
//        outcome = "Game Draw!";
//    } else if (result == 1) {
//        outcome = "You won the game!";
//    } else {
//        outcome = "You lost the game!";
//    }
}
