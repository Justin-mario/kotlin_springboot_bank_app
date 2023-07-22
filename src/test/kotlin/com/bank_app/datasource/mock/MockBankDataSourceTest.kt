package com.bank_app.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MockBankDataSourceTest {
    private val mockDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {

        //when
        val banks = mockDataSource.retrieveBanks()

        //then
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {

        //when
        val banks = mockDataSource.retrieveBanks()

        //then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).allMatch { it.transactionFee != 0 }
    }

    @Test
    fun `should retrieve a bank using account number`() {
        //given
        val accountNumber = "1234"
        //when
        val bank = mockDataSource.retrieveBank(accountNumber)

        //then
        assertThat(bank.accountNumber).isEqualTo(accountNumber)
    }

    @Test
    fun `should return NOT FOUND if account number does not exist`() {
        //given
        val accountNumber = "12345"

        //when/then
        assertThrows<NoSuchElementException> {
            mockDataSource.retrieveBank(accountNumber)
        }
    }
}