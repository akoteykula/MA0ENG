package com.example.mobileapplications_3rdclass

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class LoginActivity : AppCompatActivity() {

    private lateinit var registerNowText: MaterialTextView
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var nextButton: MaterialButton

    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        // Handle Window Insets if necessary
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_in_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Views
        registerNowText = findViewById(R.id.register_now_text)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        nextButton = findViewById(R.id.next_button)

        // Set OnClickListener for "Register now"
        registerNowText.setOnClickListener {
            // Start RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish() // Prevent back navigation to LoginActivity
        }

        // Set OnClickListener for "Next" button
        nextButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            val isEmailValid = credentialsManager.isEmailValid(email)
            val isPasswordValid = credentialsManager.isPasswordValid(password)

            if (isEmailValid && isPasswordValid) {
                // Proceed with login
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            } else {
                // Show error messages
                if (!isEmailValid) {
                    emailInput.error = "Please enter a valid email address"
                }
                if (!isPasswordValid) {
                    passwordInput.error = "Password cannot be empty"
                }
            }
        }
    }
}