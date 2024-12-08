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
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class LoginActivity : AppCompatActivity() {

    // Properties with custom getters for each view
    private val registerNowText: MaterialTextView
        get() = findViewById(R.id.register_now_text)
    private val emailInput: TextInputEditText
        get() = findViewById(R.id.email_input)
    private val emailInputLayout: TextInputLayout
        get() = findViewById(R.id.email_input_layout)
    private val passwordInput: TextInputEditText
        get() = findViewById(R.id.password_input)
    private val passwordInputLayout: TextInputLayout
        get() = findViewById(R.id.password_input_layout)
    private val nextButton: MaterialButton
        get() = findViewById(R.id.next_button)

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

        // Set OnClickListener for "Register now"
        registerNowText.setOnClickListener {
            // Start RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish() // Prevent back navigation to LoginActivity
        }

        // Set OnClickListener for "Next" button
        nextButton.setOnClickListener {
            val email = emailInput.text?.toString() ?: ""
            val password = passwordInput.text?.toString() ?: ""

            // Reset any previous errors
            emailInputLayout.error = null
            passwordInputLayout.error = null

            val isEmailValid = credentialsManager.isEmailValid(email)
            val isPasswordValid = credentialsManager.isPasswordValid(password)

            if (!isEmailValid) {
                emailInputLayout.error = "Please enter a valid email address"
            }
            if (!isPasswordValid) {
                passwordInputLayout.error = "Password cannot be empty"
            }
            

            if (isEmailValid && isPasswordValid) {
                // Check if credentials match hardcoded ones
                if (email == "test@te.st" && password == "1234") {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Show error message :(
                    Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}