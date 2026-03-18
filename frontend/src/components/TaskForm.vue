<template>
  <div class="task-form">
    <h3>Создать задачу</h3>

    <form @submit.prevent="submit">
      <input
        v-model="task.title"
        placeholder="Название"
        required
      />

      <textarea
        v-model="task.description"
        placeholder="Описание"
      ></textarea>

      <select v-model="task.priority">
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>

      <input
        type="date"
        v-model="task.dueDate"
      />

      <button type="submit">Создать</button>
    </form>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { useUserStore } from "../stores/userStore";
import taskApi from "../api/tasks";

const emit = defineEmits(["created"]);
const userStore = useUserStore();

const task = reactive({
  title: "",
  description: "",
  status: "NEW",
  priority: "MEDIUM",
  dueDate: ""
});

const resetForm = () => {
  task.title = "";
  task.description = "";
  task.status = "NEW";
  task.priority = "MEDIUM";
  task.dueDate = "";
};

const submit = async () => {
  try {
    await taskApi.create({
      ...task,
      userId: userStore.id
    });

    resetForm();
    emit("created");
  } catch (error) {
    console.error("Ошибка создания задачи:", error);
  }
};
</script>

<style scoped>
.task-form {
  margin-bottom: 20px;
}
</style>