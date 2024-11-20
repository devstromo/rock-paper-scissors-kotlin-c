package com.devstromo.rockpaperscissors

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.devstromo.rockpaperscissors.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var playerOption: Char? = null

    private var iconImageView: ImageView? = null

    private val icons = intArrayOf(
        R.drawable.ic_stone,
        R.drawable.ic_paper,
        R.drawable.ic_scissor,
    )

    private val iconsMap = mapOf(
        's' to R.drawable.ic_stone,
        'p' to R.drawable.ic_paper,
        'z' to R.drawable.ic_scissor,
    )

    private lateinit var handler: Handler
    private var iconSwitcher: Runnable? = null
    private var random: Random? = null
    private var isRunning = false

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

        iconImageView = findViewById(R.id.iconImageView)

        handler = Handler(Looper.getMainLooper())
        random = Random()

        startCyclingIcons()
    }

    private fun onButtonSelected(selectedButton: ImageButton, playerChoice: Char) {
        binding.stoneImageButton.isSelected = false
        binding.paperImageButton.isSelected = false
        binding.scissorsImageButton.isSelected = false

        selectedButton.isSelected = true
        startCyclingIcons()
        setPlayerOptionSelection(playerChoice)
        playGame(playerChoice)
    }

    private fun playGame(playerChoice: Char) {
        val result = playGameJNI(playerChoice)
        binding.resultText.text = "$result $playerChoice"
        try {
            val jsonObject = JSONObject(result)
            val computerChoice = jsonObject.getString("computerChoice")
            val complete = object : Runnable {
                override fun run() {
                    computerSelection(computerChoice[0])
                    if (isRunning) {
                        handler.postDelayed(this, 5000)
                    }
                }
            }
            handler.post(complete)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setPlayerOptionSelection(playerChoice: Char) {
        playerOption = playerChoice
    }

    private fun computerSelection(computerChoice: Char) {
        stopCyclingIcons()
        val selection = iconsMap[computerChoice]
        iconImageView!!.setImageResource(selection!!)
    }

    private fun startCyclingIcons() {
        isRunning = true
        iconSwitcher = object : Runnable {
            override fun run() {
                val randomIndex = random!!.nextInt(icons.size)
                iconImageView!!.setImageResource(icons[randomIndex])

                if (isRunning) {
                    handler.postDelayed(this, 100)
                }
            }
        }

        handler.post(iconSwitcher as Runnable)
    }

    private fun stopCyclingIcons() {
        isRunning = false
        iconSwitcher?.let { handler.removeCallbacks(it) }
    }

    private external fun playGameJNI(playerChoice: Char): String

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
