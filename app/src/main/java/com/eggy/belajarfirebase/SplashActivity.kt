package com.eggy.belajarfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.eggy.belajarfirebase.databinding.ActivitySplashBinding
import com.eggy.belajarfirebase.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load("https://img.icons8.com/color/452/firebase.png")
            .into(binding.splashIcon)
        Handler(Looper.myLooper()!!).postDelayed({
            if (FirebaseAuth.getInstance().currentUser != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                overridePendingTransition(0,0)
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                overridePendingTransition(0,0)
            }
        },1200)

    }
}