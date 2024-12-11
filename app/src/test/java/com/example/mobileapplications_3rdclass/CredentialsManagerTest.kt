package com.example.mobileapplications_3rdclass

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CredentialsManagerTest {

    private lateinit var credentialsManager: CredentialsManager

    @Before
    fun setUp() {
        credentialsManager = CredentialsManager()
    }
    // Email Validation Tests

    @Test
    fun `email validation fails when email is empty`() {
        val email = ""
        val result = credentialsManager.isEmailValid(email)
        assertFalse("Empty email should be invalid", result)
    }

    @Test
    fun `email validation fails when email is incorrectly formatted`() {
        val email = "invalidEmailFormat"
        val result = credentialsManager.isEmailValid(email)
        assertFalse("Incorrectly formatted email should be invalid", result)
    }

    @Test
    fun `email validation succeeds when email is correctly formatted`() {
        val email = "user@example.com"
        val result = credentialsManager.isEmailValid(email)
        assertTrue("Correctly formatted email should be valid", result)
    }

    // Password Validation Tests

    @Test
    fun `password validation fails when password is empty`() {
        val password = ""
        val result = credentialsManager.isPasswordValid(password)
        assertFalse("Empty password should be invalid", result)
    }

    @Test
    fun `password validation succeeds when password is not empty`() {
        val password = "ExamplePassword123"
        val result = credentialsManager.isPasswordValid(password)
        assertTrue("Non-empty password should be valid", result)
    }

    @Test
    fun `login with test@te,st and 1234 succeeds`() {
        credentialsManager.register("test@te.st", "1234")
        assertTrue("Login should succeed with test@te.st and 1234", credentialsManager.isLoginValid("test@te.st", "1234"))
    }

    @Test
    fun `login with test@te,st and incorrect password fails`() {
        credentialsManager.register("test@te.st", "1234")
        assertFalse("Login should fail with test@te.st and incorrect password", credentialsManager.isLoginValid("test@te.st", "wrong"))
    }
}