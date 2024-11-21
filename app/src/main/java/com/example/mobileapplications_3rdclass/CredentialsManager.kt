package com.example.mobileapplications_3rdclass

class CredentialsManager {

    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.isNotEmpty() && email.matches(emailPattern.toRegex())
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }
}