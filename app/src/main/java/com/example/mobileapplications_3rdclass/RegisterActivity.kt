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
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class RegisterActivity : AppCompatActivity() {

    private val loginText: MaterialTextView
        get() = findViewById(R.id.login_text)
    private val fullNameInput: TextInputEditText
        get() = findViewById(R.id.full_name_input)
    private val fullNameInputLayout: TextInputLayout
        get() = findViewById(R.id.full_name_input_layout)
    private val emailInput: TextInputEditText
        get() = findViewById(R.id.email_input)
    private val emailInputLayout: TextInputLayout
        get() = findViewById(R.id.email_input_layout)
    private val phoneNumberInput: TextInputEditText
        get() = findViewById(R.id.phone_number_input)
    private val phoneNumberInputLayout: TextInputLayout
        get() = findViewById(R.id.phone_number_input_layout)
    private val passwordInput: TextInputEditText
        get() = findViewById(R.id.password_input)
    private val passwordInputLayout: TextInputLayout
        get() = findViewById(R.id.password_input_layout)
    private val termsConditionsCheckbox: MaterialCheckBox
        get() = findViewById(R.id.terms_conditions_checkbox)
    private val nextButton: MaterialButton
        get() = findViewById(R.id.next_button)

    private lateinit var credentialsManager: CredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        credentialsManager = CredentialsManager(applicationContext)

        // Handle Window Insets if necessary
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set OnClickListener for "Log In"
        loginText.setOnClickListener {
            // Start LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Prevent back navigation to RegisterActivity
        }

        // Set OnClickListener for "Next" button
        nextButton.setOnClickListener {
            val fullName = fullNameInput.text?.toString() ?: ""
            val email = emailInput.text?.toString() ?: ""
            val phoneNumber = phoneNumberInput.text?.toString() ?: ""
            val password = passwordInput.text?.toString() ?: ""
            val isTermsChecked = termsConditionsCheckbox.isChecked

            // Reset any previous errors
            fullNameInputLayout.error = null
            emailInputLayout.error = null
            phoneNumberInputLayout.error = null
            passwordInputLayout.error = null

            val isFullNameValid = fullName.isNotEmpty()
            val isEmailValid = credentialsManager.isEmailValid(email)
            val isPhoneNumberValid = phoneNumber.isNotEmpty() // TODO: Enhance this validation
            val isPasswordValid = credentialsManager.isPasswordValid(password)

            if (!isFullNameValid) {
                fullNameInputLayout.error = "Full Name cannot be empty"
            }
            if (!isEmailValid) {
                emailInputLayout.error = "Please enter a valid email address"
            }
            if (!isPhoneNumberValid) {
                phoneNumberInputLayout.error = "Phone Number cannot be empty"
            }
            if (!isPasswordValid) {
                passwordInputLayout.error = "Password cannot be empty"
            }
            if (!isTermsChecked) {
                Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show()
            }

            if (isFullNameValid && isEmailValid && isPhoneNumberValid && isPasswordValid && isTermsChecked) {
                val registrationSuccessful = credentialsManager.register(email, password)

                if (registrationSuccessful) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    // Go back to LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    emailInputLayout.error = "Email already registered"
                }
            }
        }
    }
}