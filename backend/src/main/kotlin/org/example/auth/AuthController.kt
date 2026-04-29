package org.example.auth

import org.example.user.User
import org.example.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: RegisterRequest): Mono<IdResponse> {
        val user = User(
            name = request.name,
            email = request.email,
            password = request.password
        )
        return userRepository.save(user)
            .map { saved ->
                IdResponse(id = saved.id!!, name = saved.name)
            }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): Mono<IdResponse> {
        return userRepository.findByEmail(request.email)
            .filter { it.password == request.password }
            .switchIfEmpty(Mono.error(InvalidCredentialsException()))
            .map { user ->
                IdResponse(id = user.id!!, name = user.name)
            }
    }
}

class InvalidCredentialsException : RuntimeException("Invalid email or password")

