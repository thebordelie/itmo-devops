package org.example.task

import org.example.IntegrationTestBase
import org.example.user.User
import org.example.user.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.time.LocalDate

class TaskControllerTest : IntegrationTestBase() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var userRepository: UserRepository

    private var userId: Long = 0

    @BeforeEach
    fun setupUser() {
        val user = User(
            name = "Tasks User",
            email = "tasks-${System.currentTimeMillis()}@example.com",
            password = "123"
        )
        userId = userRepository.save(user)
            .mapNotNull { it.id }
            .switchIfEmpty(Mono.error(IllegalStateException("User not saved")))
            .block()!!
    }

    @Test
    fun `create and list tasks`() {
        webTestClient.post()
            .uri("/tasks")
            .bodyValue(
                TaskRequest(
                    userId = userId,
                    title = "Test task",
                    description = "desc",
                    status = TaskStatus.NEW,
                    priority = TaskPriority.MEDIUM,
                    dueDate = LocalDate.now()
                )
            )
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isNumber
            .jsonPath("$.title").isEqualTo("Test task")

        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/tasks")
                    .queryParam("userId", userId)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].title").isEqualTo("Test task")
    }

    @Test
    fun `update task`() {
        // create
        val createdTask = webTestClient.post()
            .uri("/tasks")
            .bodyValue(
                TaskRequest(
                    userId = userId,
                    title = "Original",
                    description = "original",
                    status = TaskStatus.NEW,
                    priority = TaskPriority.LOW,
                    dueDate = LocalDate.now()
                )
            )
            .exchange()
            .expectStatus().isCreated
            .returnResult(Task::class.java)
            .responseBody
            .blockFirst()!!

        val taskId = createdTask.id!!

        // update
        webTestClient.put()
            .uri("/tasks/$taskId")
            .bodyValue(
                TaskRequest(
                    userId = userId,
                    title = "Updated",
                    description = "updated",
                    status = TaskStatus.IN_PROGRESS,
                    priority = TaskPriority.HIGH,
                    dueDate = LocalDate.now().plusDays(1)
                )
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.title").isEqualTo("Updated")
            .jsonPath("$.status").isEqualTo("IN_PROGRESS")

        // verify via list
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/tasks")
                    .queryParam("userId", userId)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].title").isEqualTo("Updated")
    }

    @Test
    fun `delete task`() {
        // create
        val createdTask = webTestClient.post()
            .uri("/tasks")
            .bodyValue(
                TaskRequest(
                    userId = userId,
                    title = "To delete",
                    description = null,
                    status = TaskStatus.NEW,
                    priority = TaskPriority.MEDIUM,
                    dueDate = null
                )
            )
            .exchange()
            .expectStatus().isCreated
            .returnResult(Task::class.java)
            .responseBody
            .blockFirst()!!

        val taskId = createdTask.id!!

        // delete
        webTestClient.delete()
            .uri("/tasks/$taskId")
            .exchange()
            .expectStatus().isNoContent

        // verify list is empty
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/tasks")
                    .queryParam("userId", userId)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$").isArray
    }
}

