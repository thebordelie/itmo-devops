package org.example.user

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, Long> {

    @Query("SELECT id, name, email, password FROM users WHERE email = :email")
    fun findByEmail(email: String): Mono<User>
}

