package org.example.task

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface TaskRepository : ReactiveCrudRepository<Task, Long> {

    @Query(
        """
        SELECT id, user_id, title, description, status, priority, due_date, created_at, updated_at
        FROM tasks
        WHERE user_id = :userId
        ORDER BY created_at DESC
        """
    )
    fun findByUserId(userId: Long): Flux<Task>
}

