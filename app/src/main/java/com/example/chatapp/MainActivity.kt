package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
// app starts here
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //splash screen
        supportActionBar?.hide()// to hide the action bar
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, HomeActivity::class.java))// intent shift to home acitivity
            finish()
        }, 4000)


    }
}