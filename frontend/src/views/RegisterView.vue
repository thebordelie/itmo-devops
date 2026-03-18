<template>
  <div>
    <h2>Регистрация</h2>

    <form @submit.prevent="register">
      <input v-model="name" placeholder="Имя" required />
      <input v-model="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Пароль" required />
      <button type="submit">Зарегистрироваться</button>
    </form>

    <router-link to="/login">Уже есть аккаунт? Войти</router-link>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import authApi from "../api/auth";

const name = ref("");
const email = ref("");
const password = ref("");
const router = useRouter();

const register = async () => {
  await authApi.register({
    name: name.value,
    email: email.value,
    password: password.value
  });

  router.push("/login");
};
</script>