<template>
  <div>
    <h3>Список задач</h3>

    <div v-if="tasks.length === 0">
      Нет задач
    </div>

    <div v-for="task in tasks" :key="task.id" class="task">
      <h4>{{ task.title }}</h4>
      <p>{{ task.description }}</p>
      <p>Статус: {{ task.status }}</p>
      <p>Приоритет: {{ task.priority }}</p>
      <p>Дедлайн: {{ task.dueDate }}</p>

      <button @click="setStatus(task, 'IN_PROGRESS')">
        В процессе
      </button>

      <button @click="setStatus(task, 'DONE')">
        Выполнено
      </button>

      <button @click="removeTask(task.id)">
        Удалить
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineExpose } from "vue";
import { useUserStore } from "../stores/userStore";
import taskApi from "../api/tasks";

const tasks = ref([]);
const userStore = useUserStore();

const loadTasks = async () => {
  try {
    const response = await taskApi.getAll(userStore.id);
    tasks.value = response.data;
  } catch (error) {
    console.error("Ошибка загрузки задач:", error);
  }
};

const removeTask = async (id) => {
  try {
    await taskApi.delete(id);
    await loadTasks();
  } catch (error) {
    console.error("Ошибка удаления:", error);
  }
};

const setStatus = async (task, status) => {
  try {
    await taskApi.update(task.id, {
      ...task,
      status
    });

    await loadTasks();
  } catch (error) {
    console.error("Ошибка обновления:", error);
  }
};

onMounted(loadTasks);

defineExpose({ loadTasks });
</script>

<style scoped>
.task {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}
</style>