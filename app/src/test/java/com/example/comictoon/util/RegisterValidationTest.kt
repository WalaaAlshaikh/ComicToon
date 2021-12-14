package com.example.comictoon.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterValidationTest{
    private lateinit var validator:RegisterValidation
    @Before
    fun setup(){
        validator= RegisterValidation()
    }
    @Test
    fun emailIsValidWithInvalidEmailThenReturnFalseValue(){
        val validation= validator.emailIsValid("test-test.com")

        assertEquals(false,validation)


    }

    @Test
    fun emailIsValidWithValidEmailThenReturnTrueValue(){
        val validation= validator.emailIsValid("test@test.com")

        assertEquals(true,validation)

    }

    @Test
    fun passwordIsValidWithInvalidPasswordThenReturnFalseValue(){
        val validation=validator.passIsValid("882")
        assertEquals(false,validation)
    }


    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrueValue(){
        val validation=validator.passIsValid("@Wala1234")
        assertEquals(true,validation)
    }


}