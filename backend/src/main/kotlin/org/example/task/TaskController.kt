package org.example.task

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
@RequestMapping("/tasks")
class TaskController(
    private val taskRepository: TaskRepository
    ) {

    @GetMapping
    fun getTasksByUser(@RequestParam userId: Long): Flux<Task> {
        return taskRepository.findByUserId(userId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody request: TaskRequest): Mono<Task> {
        val now = LocalDateTime.now()
        val task = Task(
            userId = request.userId,
            title = request.title,
            description = request.description,
            status = request.status,
            priority = request.priority,
            dueDate = request.dueDate,
            createdAt = now,
            updatedAt = now
        )
        return taskRepository.save(task)
    }

    @PutMapping("/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @RequestBody request: TaskRequest
    ): Mono<Task> {
        return taskRepository.findById(id)
            .switchIfEmpty(Mono.error(TaskNotFoundException(id)))
            .flatMap { existing ->
                val updated = existing.copy(
                    userId = request.userId,
                    title = request.title,
                    description = request.description,
                    status = request.status,
                    priority = request.priority,
                    dueDate = request.dueDate,
                    updatedAt = LocalDateTime.now()
                )
                taskRepository.save(updated)
            }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: Long): Mono<Void> {
        return taskRepository.deleteById(id)
    }
}

class TaskNotFoundException(id: Long) : RuntimeException("Task with id=$id not found")

