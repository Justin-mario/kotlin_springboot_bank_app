package com.bank_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyBankAppApplication

fun main(args: Array<String>) {
    runApplication<MyBankAppApplication>(*args)
}
