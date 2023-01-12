package com.mrizkisaputra.suitgame

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mrizkisaputra.suitgame.databinding.FragmentGameHomeBinding

class GameHomeFragment : Fragment() {
    private var _binding: FragmentGameHomeBinding? = null
    private val binding get() = _binding!!

    private val gamePlayFragment: GamePlayFragment = GamePlayFragment()
    private lateinit var bundle: Bundle
    private lateinit var soundeffectButton: MediaPlayer

    private fun onClick(v: View) {
        when (v.id) {
            R.id.start_game_button -> {
                soundeffectButton.start()
                val round: String = binding.roundEdittext.text.toString().trim()
                bundle = Bundle().apply {
                    putString(GamePlayFragment.BUNDLE_DATA, round)
                }
                gamePlayFragment.arguments = bundle

                if (round.isNotEmpty()) {
                    if (round.toInt() < 1) {
                        binding.roundEdittext.error = "round cannot be less than 1"
                    } else {
                        replaceFragment(gamePlayFragment)
                    }
                } else {
                    Toast.makeText(requireActivity(), "enter how many round", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment is GamePlayFragment) {
            beginTransactionFragment(fragment)
        }
    }

    private fun beginTransactionFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.apply {
            replace(R.id.container_activity, fragment, fragment::class.java.simpleName)
            commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundeffectButton = MediaPlayer.create(requireActivity(), R.raw.sound_effect)
        binding.startGameButton.setOnClickListener(::onClick)
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
}