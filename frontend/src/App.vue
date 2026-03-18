<template>
  <div>

    <router-view />
  </div>
</template>

<script setup>
import { useUserStore } from "./stores/userStore";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

const logout = () => {
  userStore.logout();
  router.push("/login");
};
</script>