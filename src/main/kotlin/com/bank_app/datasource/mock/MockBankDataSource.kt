package com.bank_app.datasource.mock

import com.bank_app.datasource.BankDataSource
import com.bank_app.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    private val banks = mutableListOf(
        Bank("1234", 1234.0, 17),
        Bank("5678", 34.0, 6),
        Bank("910", 12.0, 12))

    override fun retrieveBanks(): Collection<Bank>  = banks

    override fun retrieveBank(accountNumber: String): Bank {
        return findBank(accountNumber)
    }

    override fun addBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }){
            throw IllegalArgumentException("bank with account number ${bank.accountNumber} already exist")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val accountNumber = bank.accountNumber
        val currentBank = findBank(accountNumber)
        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteBank(accountNumber: String): String {
        val foundBank = findBank(accountNumber)
        banks.remove(foundBank)
        return "bank with account number $accountNumber has been deleted"
    }

    private fun findBank(accountNumber: String): Bank {
        return banks.firstOrNull { it.accountNumber == accountNumber }
             ?: throw NoSuchElementException("Could not find bank with account number $accountNumber")
    }
}