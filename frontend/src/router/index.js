import { createRouter, createWebHistory } from "vue-router";
import { useUserStore } from "../stores/userStore";

import TasksView from "../views/TasksView.vue";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import NotFoundView from "../views/NotFoundView.vue";

const routes = [
  { path: "/", redirect: "/tasks" },
  { path: "/tasks", component: TasksView, meta: { requiresAuth: true } },
  { path: "/login", component: LoginView },
  { path: "/register", component: RegisterView },
{ path: "/:pathMatch(.*)*", name: "NotFound", component: NotFoundView }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to) => {
  const userStore = useUserStore();

  if (to.meta.requiresAuth && !userStore.id) {
    return "/login";
  }
if ((to.path === "/login" || to.path === "/register") && userStore.id) {
    return "/tasks";
  }
});

export default router;