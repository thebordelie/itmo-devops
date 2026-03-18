package org.example.auth

import org.example.IntegrationTestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

class AuthControllerTest : IntegrationTestBase() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `register and login user`() {
        val email = "test-${System.currentTimeMillis()}@example.com"

        webTestClient.post()
            .uri("/auth/register")
            .bodyValue(
                RegisterRequest(
                    name = "Test User",
                    email = email,
                    password = "123"
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isNumber

        webTestClient.post()
            .uri("/auth/login")
            .bodyValue(
                LoginRequest(
                    email = email,
                    password = "123"
                )
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isNumber
    }

    @Test
    fun `login with wrong password should return 401`() {
        val email = "wrong-pass-${System.currentTimeMillis()}@example.com"

        // register
        webTestClient.post()
            .uri("/auth/register")
            .bodyValue(
                RegisterRequest(
                    name = "User",
                    email = email,
                    password = "123"
                )
            )
            .exchange()
            .expectStatus().isCreated

        // login with wrong password
        webTestClient.post()
            .uri("/auth/login")
            .bodyValue(
                LoginRequest(
                    email = email,
                    password = "wrong"
                )
            )
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun `register with duplicate email should return 409`() {
        val email = "dup-${System.currentTimeMillis()}@example.com"

        webTestClient.post()
            .uri("/auth/register")
            .bodyValue(
                RegisterRequest(
                    name = "User1",
                    email = email,
                    password = "123"
                )
            )
            .exchange()
            .expectStatus().isCreated

        webTestClient.post()
            .uri("/auth/register")
            .bodyValue(
                RegisterRequest(
                    name = "User2",
                    email = email,
                    password = "456"
                )
            )
            .exchange()
            .expectStatus().isEqualTo(409)
    }
}

