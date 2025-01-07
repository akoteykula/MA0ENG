package com.example.mobileapplications_3rdclass

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.lang.ref.WeakReference

class RegisterFragment : Fragment() {

    private lateinit var credentialsManager: CredentialsManager
    private var weakContext: WeakReference<Context>? = null
    private var rootView: WeakReference<View>? = null

    //region UI elements
    private val login_text: MaterialTextView by lazy { findView(R.id.login_text) }
    private val fullNameInput: TextInputEditText by lazy { findView(R.id.full_name_input) }
    private val fullNameInputLayout: TextInputLayout by lazy { findView(R.id.full_name_input_layout) }
    private val email_input: TextInputEditText by lazy { findView(R.id.email_input) }
    private val emailInputLayout: TextInputLayout by lazy { findView(R.id.email_input_layout) }
    private val phoneNumberInput: TextInputEditText by lazy { findView(R.id.phone_number_input) }
    private val phoneNumberInputLayout: TextInputLayout by lazy { findView(R.id.phone_number_input_layout) }
    private val password_input: TextInputEditText by lazy { findView(R.id.password_input) }
    private val passwordInputLayout: TextInputLayout by lazy { findView(R.id.password_input_layout) }
    private val termsConditionsCheckbox: MaterialCheckBox by lazy { findView(R.id.terms_conditions_checkbox) }
    private val nextButton: MaterialButton by lazy { findView(R.id.next_button) }
    //endregion

    private fun <T : View> findView(id: Int): T {
        return rootView?.get()?.findViewById(id)
            ?: throw IllegalStateException("View with ID $id not found in the layout")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        weakContext = WeakReference(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)
        rootView = WeakReference(view)

        credentialsManager = arguments?.getSerializable("credentialsManager") as? CredentialsManager
            ?: run {
                val context = weakContext?.get()
                    ?: throw IllegalStateException("Context is null")
                CredentialsManager(context)
            }

        login_text.setOnClickListener {
            (activity as? AuthenticationActivity)?.navigateToLogin()
        }

        nextButton.setOnClickListener {
            val fullName = fullNameInput.text?.toString() ?: ""
            val email = email_input.text?.toString() ?: ""
            val phoneNumber = phoneNumberInput.text?.toString() ?: ""
            val password = password_input.text?.toString() ?: ""
            val isTermsChecked = termsConditionsCheckbox.isChecked

            fullNameInputLayout.error = null
            emailInputLayout.error = null
            phoneNumberInputLayout.error = null

            passwordInputLayout.error = null

            val isFullNameValid = fullName.isNotEmpty()
            val isEmailValid = credentialsManager.isEmailValid(email)
            val isPhoneNumberValid = phoneNumber.isNotEmpty()
            val isPasswordValid = credentialsManager.isPasswordValid(password)

            if (!isFullNameValid) {
                fullNameInputLayout.error = "Full Name cannot be empty"
            }
            if (!isEmailValid) {
                emailInputLayout.error = "Please, enter a valid email"
            }
            if (!isPhoneNumberValid) {
                phoneNumberInputLayout.error = "Phone Number cannot be empty"
            }
            if (!isPasswordValid) {
                passwordInputLayout.error = "Password cannot be empty"
            }
            if (!isTermsChecked) {
                Toast.makeText(
                    context,
                    "Please, agree to the terms and conditions",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (isFullNameValid && isEmailValid && isPhoneNumberValid && isPasswordValid && isTermsChecked) {
                val registrationSuccessful = credentialsManager.register(email, password)

                if (registrationSuccessful) {
                    Toast.makeText(context, "Registration was successful", Toast.LENGTH_SHORT).show()
                    (activity as? AuthenticationActivity)?.navigateToLogin()
                } else {
                    emailInputLayout.error = "Email is already registered"
                }
            }
        }

        return view
    }

    companion object {
        fun newInstance(credentialsManager: CredentialsManager): RegisterFragment {
            val fragment = RegisterFragment()
            val args = Bundle().apply {
                putSerializable("credentialsManager", credentialsManager)
            }
            fragment.arguments = args
            return fragment
        }
    }
}