<template>
  <div>
    <h2>Вход</h2>

    <div v-if="userStore.id">
      <p>Вы уже авторизованы</p>
      <router-link to="/tasks">Перейти к задачам</router-link>
    </div>

    <form @submit.prevent="login">
      <input v-model="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Пароль" required />
      <button type="submit">Войти</button>
    </form>

    <router-link to="/register">Регистрация</router-link>

  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import authApi from "../api/auth";
import { useUserStore } from "../stores/userStore";

const email = ref("");
const password = ref("");
const router = useRouter();
const userStore = useUserStore();

const login = async () => {
  const response = await authApi.login({
    email: email.value,
    password: password.value
  });

  userStore.setUser(response.data);
  router.push("/tasks");
};
</script>