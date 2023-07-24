package com.bank_app.datasource.network

import com.bank_app.datasource.BankDataSource
import com.bank_app.datasource.network.dto.BankList
import com.bank_app.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository(value = "network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate) : BankDataSource {


    override fun retrieveBanks(): Collection<Bank> {
        val response = restTemplate.getForEntity<BankList>("http://54.219.183.128/banks")
        return response.body?.results
            ?: throw IOException("Could not fetch banks from network")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun addBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String): String {
        TODO("Not yet implemented")
    }
}