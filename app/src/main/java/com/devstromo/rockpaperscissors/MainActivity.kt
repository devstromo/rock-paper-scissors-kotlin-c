package com.devstromo.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.devstromo.rockpaperscissors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stoneImageButton.setOnClickListener { onButtonSelected(binding.stoneImageButton, 's') }
        binding.paperImageButton.setOnClickListener { onButtonSelected(binding.paperImageButton, 'p') }
        binding.scissorsImageButton.setOnClickListener { onButtonSelected(binding.scissorsImageButton, 'z') }
    }

    private fun onButtonSelected(selectedButton: ImageButton, playerChoice: Char) {
        binding.stoneImageButton.isSelected = false
        binding.paperImageButton.isSelected = false
        binding.scissorsImageButton.isSelected = false

        selectedButton.isSelected = true

        playGame(playerChoice)
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
