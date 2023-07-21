package com.bank_app.datasource

import com.bank_app.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
}