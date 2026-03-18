<template>
  <div>
    <div class="header">
      <h2>Система управления задачами</h2>

      <div v-if="userStore.id" class="user-info">
        <span>Пользователь: {{ userStore.name }}</span>
        <button @click="logout">Выйти</button>
      </div>
    </div>

    <TaskForm @created="reloadTasks" />

    <TaskList ref="taskListRef" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../stores/userStore";

import TaskForm from "../components/TaskForm.vue";
import TaskList from "../components/TaskList.vue";

const taskListRef = ref(null);
const router = useRouter();
const userStore = useUserStore();

const reloadTasks = () => {
  taskListRef.value?.loadTasks?.();
};

const logout = () => {
  userStore.logout();
  router.push("/login");
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.user-info {
  display: flex;
  gap: 10px;
  align-items: center;
}
</style>