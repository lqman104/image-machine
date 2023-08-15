package com.luqman.imagemachine.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class UseCaseTest {

    private val validation = GetAllMachineUseCase()

    // given address
    // when empty
    // then error
    @Test
    fun `given address when empty return error`() {
        val address = ""

        val result = validation.invoke(address)

        assertEquals(false, result.successful)
    }

    // given address
    // when filled valid address
    // then show success
    @Test
    fun `given address when filled valid address return success`() {
        val address = "testing"

        val result = validation.invoke(address)

        assertEquals(true, result.successful)
    }

}