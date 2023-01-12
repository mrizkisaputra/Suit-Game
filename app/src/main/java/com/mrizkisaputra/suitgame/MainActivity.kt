package com.mrizkisaputra.suitgame

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mrizkisaputra.suitgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val gameHomeFragment: GameHomeFragment = GameHomeFragment()
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
    private lateinit var backgroundSound: MediaPlayer

    private fun playBackgroundSound() {
        backgroundSound.isLooping = true
        backgroundSound.start()
    }

    private fun addFragment() {
        fragmentTransaction.apply {
            add(R.id.container_activity, gameHomeFragment, GameHomeFragment::class.java.simpleName)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backgroundSound = MediaPlayer.create(this, R.raw.background_sound)
        addFragment()
    }

    override fun onStart() {
        super.onStart()
        playBackgroundSound()
    }

    override fun onStop() {
        super.onStop()
        backgroundSound.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundSound.stop()
    }
}