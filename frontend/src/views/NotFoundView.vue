<template>
  <div class="not-found">
    <h1>404</h1>
    <h2>Страница не найдена</h2>

    <p>Запрошенная страница не существует.</p>

    <router-link to="/login">Вернуться на главную</router-link>
  </div>
</template>

<style scoped>
.not-found {
  text-align: center;
  margin-top: 80px;
}

h1 {
  font-size: 80px;
  margin-bottom: 10px;
}
</style>