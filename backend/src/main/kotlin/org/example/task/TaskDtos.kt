package org.example.task

import java.time.LocalDate

data class TaskRequest(
    val userId: Long,
    val title: String,
    val description: String? = null,
    val status: TaskStatus = TaskStatus.NEW,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: LocalDate? = null
)

