package com.example.mobileapplications_3rdclass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.lang.ref.WeakReference

class LoginFragment : Fragment() {

    private lateinit var credentialsManager: CredentialsManager
    private lateinit var weakContext: WeakReference<Context>
    private var rootView: WeakReference<View>? = null

    // Region UI elements
    private val registerNowText: MaterialTextView by lazy { findView(R.id.register_now_text) }
    private val emailInput: TextInputEditText by lazy { findView(R.id.email_input) }
    private val emailInputLayout: TextInputLayout by lazy { findView(R.id.email_input_layout) }
    private val passwordInput: TextInputEditText by lazy { findView(R.id.password_input) }
    private val passwordInputLayout: TextInputLayout by lazy { findView(R.id.password_input_layout) }
    private val next_button: MaterialButton by lazy { findView(R.id.next_button) }
    // EndRegion

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
        val view = inflater.inflate(R.layout.activity_sign_in, container, false)
        rootView = WeakReference(view)

        credentialsManager = arguments?.getSerializable("credentialsManager") as? CredentialsManager
            ?: run {
                val context = weakContext.get()
                    ?: throw IllegalStateException("Context is null")
                CredentialsManager(context)
            }

        registerNowText.setOnClickListener {
            (activity as? AuthenticationActivity)?.navigateToRegister()
        }

        next_button.setOnClickListener {
            var email = emailInput.text?.toString()
            if (email == null) email = ""
            var password = passwordInput.text?.toString()
            if (password == null) password = ""

            emailInputLayout.error = null

            passwordInputLayout.error = null

            val isEmailValid = credentialsManager.isEmailValid(email)
            val isPasswordValid = credentialsManager.isPasswordValid(password)

            if (!isEmailValid) {
                emailInputLayout.error = "Please, enter a valid email"
            }
            if (!isPasswordValid) {
                passwordInputLayout.error = "Password cannot be empty"
            }

            if (isEmailValid && isPasswordValid) {
                if (credentialsManager.isLoginValid(email, password)) {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        return view
    }

    companion object {
        fun newInstance(credentialsManager: CredentialsManager): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle().apply {
                putSerializable("credentialsManager", credentialsManager)
            }
            fragment.arguments = args
            return fragment
        }
    }
}