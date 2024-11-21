package com.example.mobileapplications_3rdclass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView

class LoginActivity : AppCompatActivity() {

    private lateinit var registerNowText: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize the TextView
        registerNowText = findViewById(R.id.register_now_text)

        // Set OnClickListener
        registerNowText.setOnClickListener {
            // Start RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

            // Finish LoginActivity
            finish()
        }
    }
}