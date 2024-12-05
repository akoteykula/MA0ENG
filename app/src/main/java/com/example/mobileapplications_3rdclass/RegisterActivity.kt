package com.example.mobileapplications_3rdclass

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class RegisterActivity : AppCompatActivity() {

    private lateinit var loginText: MaterialTextView
    private lateinit var fullNameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneNumberInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var termsConditionsCheckbox: MaterialCheckBox
    private lateinit var nextButton: MaterialButton

    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle Window Insets if necessary
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Views
        loginText = findViewById(R.id.login_text)
        fullNameInput = findViewById(R.id.full_name_input)
        emailInput = findViewById(R.id.email_input)
        phoneNumberInput = findViewById(R.id.phone_number_input)
        passwordInput = findViewById(R.id.password_input)
        termsConditionsCheckbox = findViewById(R.id.terms_conditions_checkbox)
        nextButton = findViewById(R.id.next_button)

        // Set OnClickListener for "Log In"
        loginText.setOnClickListener {
            // Start LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Prevent back navigation to RegisterActivity
        }

        // Set OnClickListener for "Next" button
        nextButton.setOnClickListener {
            val fullName = fullNameInput.text.toString()
            val email = emailInput.text.toString()
            val phoneNumber = phoneNumberInput.text.toString()
            val password = passwordInput.text.toString()
            val isTermsChecked = termsConditionsCheckbox.isChecked

            val isFullNameValid = fullName.isNotEmpty()
            val isEmailValid = credentialsManager.isEmailValid(email)
            val isPhoneNumberValid = phoneNumber.isNotEmpty() // TODO: enhance this validation
            val isPasswordValid = credentialsManager.isPasswordValid(password)

            if (isFullNameValid && isEmailValid && isPhoneNumberValid && isPasswordValid && isTermsChecked) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
            } else {
                // Show error messages
                if (!isFullNameValid) {
                    fullNameInput.error = "Full Name cannot be empty"
                }
                if (!isEmailValid) {
                    emailInput.error = "Please enter a valid email address"
                }
                if (!isPhoneNumberValid) {
                    phoneNumberInput.error = "Phone Number cannot be empty"
                }
                if (!isPasswordValid) {
                    passwordInput.error = "Password cannot be empty"
                }
                if (!isTermsChecked) {
                    Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}