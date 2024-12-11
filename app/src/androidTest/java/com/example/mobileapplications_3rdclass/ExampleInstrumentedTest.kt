package com.example.mobileapplications_3rdclass

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CredentialsManagerTest {

    private lateinit var credentialsManager: CredentialsManager
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
        credentialsManager = CredentialsManager(context)
        val sharedPreferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun testIsEmailValid() {
        assertTrue(credentialsManager.isEmailValid("test@example.com"))
        assertFalse(credentialsManager.isEmailValid("invalid_email"))
        assertFalse(credentialsManager.isEmailValid(""))
    }

    @Test
    fun testIsPasswordValid() {
        assertTrue(credentialsManager.isPasswordValid("password123"))
        assertFalse(credentialsManager.isPasswordValid(""))
    }

    @Test
    fun testRegister_NewAccount_Success() {
        assertTrue(credentialsManager.register("test@example.com", "password123"))
        assertTrue(credentialsManager.isLoginValid("test@example.com", "password123"))
    }

    @Test
    fun testRegister_ExistingAccount_Failure() {
        credentialsManager.register("test@example.com", "password123")
        assertFalse(credentialsManager.register("test@example.com", "anotherPassword"))
        assertFalse(credentialsManager.register("TEST@example.com", "anotherPassword"))
    }

    @Test
    fun testRegister_CaseInsensitive_Failure() {
        credentialsManager.register("test@example.com", "password123")
        assertFalse(credentialsManager.register("TEST@EXAMPLE.COM", "anotherPassword"))
    }

    @Test
    fun testIsLoginValid_ValidCredentials_Success() {
        credentialsManager.register("test@example.com", "password123")
        assertTrue(credentialsManager.isLoginValid("test@example.com", "password123"))
    }

    @Test
    fun testIsLoginValid_InvalidPassword_Failure() {
        credentialsManager.register("test@example.com", "password123")
        assertFalse(credentialsManager.isLoginValid("test@example.com", "wrongPassword"))
    }

    @Test
    fun testIsLoginValid_InvalidEmail_Failure() {
        assertFalse(credentialsManager.isLoginValid("nonexistent@example.com", "password123"))
    }

    @Test
    fun testIsLoginValid_CaseInsensitive_Success() {
        credentialsManager.register("test@example.com", "password123")
        assertTrue(credentialsManager.isLoginValid("TEST@EXAMPLE.COM", "password123"))
    }

    @Test
    fun login_with_test_at_te_st_and_1234_succeeds() {
        credentialsManager.register("test@te.st", "1234")
        assertTrue("Login should succeed with test@te.st and 1234", credentialsManager.isLoginValid("test@te.st", "1234"))
    }

    @Test
    fun login_with_test_at_te_st_and_incorrect_password_fails() {
        credentialsManager.register("test@te.st", "1234")
        assertFalse("Login should fail with test@te.st and incorrect password", credentialsManager.isLoginValid("test@te.st", "wrong"))
    }
}