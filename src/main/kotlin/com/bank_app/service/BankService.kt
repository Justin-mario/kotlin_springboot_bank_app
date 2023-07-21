package com.bank_app.service

import com.bank_app.datasource.BankDataSource
import com.bank_app.model.Bank
import org.springframework.stereotype.Service


@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()


}