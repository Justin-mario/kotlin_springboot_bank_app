package com.bank_app.datasource.network.dto

import com.bank_app.model.Bank

data class BankList(
    val results: Collection<Bank>
)
