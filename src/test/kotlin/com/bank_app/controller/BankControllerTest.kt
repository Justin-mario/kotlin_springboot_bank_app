package com.bank_app.controller

import com.bank_app.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*


@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {




    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("Get api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks` () {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].account_number") {value("1234")}
                }
        }
    }

    @Nested
    @DisplayName("Get api/banks/accountNumber")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with given account number` () {
            //given
            val accountNumber = 1234

            //when//then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") {value(1234.0)}
                    jsonPath("$.default_transaction_fee") {value(17)}
                }
        }

        @Test
        fun `should return NOT FOUND if account number does not exist` () {
            val accountNumber = "does_not_exist"
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add new bank` () {
            //given
            val newBank = Bank("8989", 12.0, 4)
            //when/then
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
                .andDo { print() }
                .andExpect { status { isCreated()}
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank)) }
                     }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
        }
        @Test
        fun `should return BAD REQUEST if bank with given account number already exist` () {
            //given
            val invalidBank = Bank("8989", 12.0, 4)
            //when/then
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("PATCH api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update an existing bank` () {

            //given
            val newBank = Bank("ac8989", 12.0, 4)
            //when/then
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
                .andDo { print() }
                .andExpect { status { isCreated()} }

            //given
            val updatedBank = Bank("ac8989", 12.0, 8)

            //when
            val performPatchRequest =  mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }


            //then
              performPatchRequest  .andDo { print() }
                .andExpect { status { isOk()}
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updatedBank))}
                   }
            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }

        @Test
        fun `should return BAD REQUEST if no bank with given account number` () {
            //given
            val invalidBank = "bad request"

            //when
            val performPatchRequest =   mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            //then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isBadRequest() } }

        }
    }

    @Nested
    @DisplayName("Delete api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {
        @Test
        @DirtiesContext
        fun `should delete an existing bank` () {
            //given
            val accountNumber = "1234"

            //when
            val performDeleteRequest = mockMvc.delete("$baseUrl/$accountNumber") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(accountNumber)

            }
            //then
            performDeleteRequest
                .andDo { print() }
                .andExpect { status { isNoContent()} }

            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return BAD REQUEST if no bank with given account number` () {
            //given
            val invalidBank = "bad request"

            //when
            val performPatchRequest =   mockMvc.delete("$baseUrl/$invalidBank") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            //then
            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }

        }
    }

}