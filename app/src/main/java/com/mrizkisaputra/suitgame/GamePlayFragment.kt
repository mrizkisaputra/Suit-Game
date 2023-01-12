package com.mrizkisaputra.suitgame

import android.app.Dialog
import android.content.Context
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.mrizkisaputra.suitgame.databinding.FragmentGameplayBinding

class GamePlayFragment : Fragment() {
    private var _binding: FragmentGameplayBinding? = null
    private val binding get() = _binding!!

    private lateinit var soundEffectButton: MediaPlayer
    private lateinit var soundEffectScore: MediaPlayer
    private lateinit var soundEffectWinner: MediaPlayer
    private lateinit var soundEffectLose: MediaPlayer

    private var rounds: Int = 0
    private var currentRounds: Int = 1
    private var scorePlayer: Int = 0
    private var scoreComputer: Int = 0

    private fun onClick(v: View) {
        when (v.id) {
            R.id.scissors_imageview -> {
                soundEffectButton.start()
                startGamePlay(SCISSORS)
            }

            R.id.rock_imageview -> {
                soundEffectButton.start()
                startGamePlay(ROCK)
            }

            R.id.paper_imageview -> {
                soundEffectButton.start()
                startGamePlay(PAPER)
            }
        }
    }

    private fun startGamePlay(optionPlayer: String) {
        val optionComputer: String = Game.computerChoice()

        if (currentRounds <= rounds) {
            val computerDrawable: Int = Game.selectOptionDrawable(optionComputer)
            val playerDrawable: Int = Game.selectOptionDrawable(optionPlayer)

            binding.apply {
                roundOfTextview.text = getString(R.string.text_roundof_textview, currentRounds, rounds)
                computerChoiceImageview.setImageResource(computerDrawable)
                playerChoiceImageview.setImageResource(playerDrawable)
            }

            when {
                Game.isDraw(optionPlayer, optionComputer) -> {
                    val snackbar = Snackbar.make(binding.coordinatorLayout, "Draw, Try again", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(resources.getColor(R.color.yellow))
                    snackbar.show()
                }

                Game.isWin(optionPlayer, optionComputer)!! -> {
                    soundEffectScore.start()
                    scorePlayer += (100 / rounds)
                    binding.scorePlayer.text = scorePlayer.toString()
                }

                else -> {
                    scoreComputer += (100 / rounds)
                    binding.scoreComputer.text = scoreComputer.toString()
                }
            }

            if (currentRounds >= rounds) {
                when {
                    scorePlayer > scoreComputer -> {
                        soundEffectWinner.start()
                        val message: String = getString(R.string.text_winner)
                        showDialog(R.drawable.winner, message)
                    }

                    scoreComputer > scorePlayer -> {
                        soundEffectLose.start()
                        val message: String = getString(R.string.text_lose)
                        showDialog(R.drawable.lose, message)
                    }

                    else -> {
                        val message: String = getString(R.string.text_draw)
                        showDialog(R.drawable.draw, message)
                    }
                }
            }

            currentRounds += 1
        } else {
            val snackbar = Snackbar.make(binding.coordinatorLayout, getString(R.string.game_over), Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(resources.getColor(android.R.color.holo_red_light))
            snackbar.setAction(getString(R.string.text_play_again_button)) {
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.container_activity, GameHomeFragment(), GameHomeFragment::class.java.simpleName)
                    commit()
                }
            }.show()
        }
    }

    private fun showDialog(image: Int, message: String) {
        val dialog = Dialog(requireActivity()).apply {
            setContentView(R.layout.fragment_dialog_game_result)
            setCancelable(false)
            show()
        }

        val imageView: ImageView = dialog.findViewById(R.id.game_result_imageview)
        val textView: TextView = dialog.findViewById(R.id.game_result_textview)
        val cancelButton: Button = dialog.findViewById(R.id.cancel_button)
        val playAgainButton: Button = dialog.findViewById(R.id.play_again_button)

        imageView.setImageResource(image)
        textView.text = getString(R.string.text_result_match, message)

        cancelButton.setOnClickListener { dialog.dismiss() }
        playAgainButton.setOnClickListener {
            dialog.dismiss()
            replaceFragment()
        }
    }

    private fun replaceFragment() {
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.container_activity, GameHomeFragment(), GameHomeFragment::class.java.simpleName)
            commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameplayBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        soundEffectButton = MediaPlayer.create(requireActivity(), R.raw.sound_effect)
        soundEffectScore = MediaPlayer.create(requireActivity(), R.raw.sound_effect_score)
        soundEffectLose = MediaPlayer.create(requireActivity(), R.raw.sound_effect_lose)
        soundEffectWinner = MediaPlayer.create(requireActivity(), R.raw.sound_effect_win)

        arguments?.let {
            rounds = it.getString(BUNDLE_DATA)?.toInt() ?: 0
        }

        binding.apply {
            scissorsImageview.setOnClickListener(::onClick)
            rockImageview.setOnClickListener(::onClick)
            paperImageview.setOnClickListener(::onClick)
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        const val BUNDLE_DATA: String = "com.mrizkisaputra.suitgame"
        private const val SCISSORS: String = "SCISSORS"
        private const val ROCK: String = "ROCK"
        private const val PAPER: String = "PAPER"
        private val TAG: String = GamePlayFragment::class.java.simpleName
    }
}