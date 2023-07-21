package com.bank_app.datasource.mock

import com.bank_app.datasource.BankDataSource
import com.bank_app.model.Bank


class MockBankDataSource : BankDataSource {
    private val banks = listOf(
        Bank("1234", 1234.0, 17),
        Bank("5678", 34.0, 6),
        Bank("910", 12.0, 12))
    override fun retrieveBanks(): Collection<Bank>  = banks

}