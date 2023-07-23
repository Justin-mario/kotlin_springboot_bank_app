package com.bank_app.service

import com.bank_app.datasource.BankDataSource
import com.bank_app.model.Bank
import org.springframework.stereotype.Service



@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)
    fun addBank(bank: Bank): Bank = dataSource.addBank(bank)
    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)
    fun deleteBank(accountNumber: String): String = dataSource.deleteBank(accountNumber)


}