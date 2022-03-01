package com.submission.dicoding.dgitapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.submission.dicoding.dgitapp.databinding.ActivitySplashScreenBinding
import com.submission.dicoding.dgitapp.ui.home.MainActivity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val durationSplash: Duration = 1500.milliseconds
        val alpha = 1f
        binding.icGithub.animate().setDuration(durationSplash.toLong(DurationUnit.MILLISECONDS)).alpha(alpha).withEndAction {
            toMainActivity()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    private fun toMainActivity() {
        MainActivity.start(this)
        finish()
    }
}