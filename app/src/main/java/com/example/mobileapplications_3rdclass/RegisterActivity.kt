package com.example.mobileapplications_3rdclass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView

class RegisterActivity : AppCompatActivity() {

    private lateinit var loginText: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the TextView
        loginText = findViewById(R.id.login_text)

        // Set OnClickListener
        loginText.setOnClickListener {
            // Start LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Finish RegisterActivity
            finish()
        }
    }
}