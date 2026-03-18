package org.example.task

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime

enum class TaskStatus {
    NEW,
    IN_PROGRESS,
    DONE
}

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH
}

@Table("tasks")
data class Task(
    @Id
    val id: Long? = null,
    val userId: Long,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val priority: TaskPriority,
    val dueDate: LocalDate?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

