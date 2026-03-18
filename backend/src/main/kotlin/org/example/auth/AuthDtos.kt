package org.example.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class IdResponse(
    val id: Long,
    val name: String,
)

