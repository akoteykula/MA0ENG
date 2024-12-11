package com.example.mobileapplications_3rdclass

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CredentialsManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val accounts: MutableMap<String, String> = loadAccounts()

    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.isNotEmpty() && email.matches(emailPattern.toRegex())
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    /**
     * Registers a new account with the given email and password.
     *
     * @param email The email address to register.
     * @param password The password for the new account.
     * @return True if the registration was successful, false if the email is already registered.
     */
    fun register(email: String, password: String): Boolean {
        val lowerCaseEmail = email.lowercase()
        if (accounts.containsKey(lowerCaseEmail)) {
            return false // Email already exists
        }
        accounts[lowerCaseEmail] = password
        saveAccounts()
        return true // Registration successful
    }

    /**
     * Checks if an account with the given email and password exists.
     *
     * @param email The email address to check.
     * @param password The password to check.
     * @return True if the credentials are valid, false otherwise.
     */
    fun isLoginValid(email: String, password: String): Boolean {
        val lowerCaseEmail = email.lowercase()
        return accounts[lowerCaseEmail] == password
    }

    private fun loadAccounts(): MutableMap<String, String> {
        val accountsJson = sharedPreferences.getString("accounts", null)
        return if (accountsJson != null) {
            val type = object : TypeToken<MutableMap<String, String>>() {}.type
            gson.fromJson(accountsJson, type)
        } else {
            mutableMapOf()
        }
    }

    private fun saveAccounts() {
        val accountsJson = gson.toJson(accounts)
        sharedPreferences.edit().putString("accounts", accountsJson).apply()
    }
}