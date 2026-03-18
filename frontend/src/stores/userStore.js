import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
  state: () => ({
    id: localStorage.getItem("userId") || null,
    name: localStorage.getItem("userName") || null
  }),

  actions: {
    setUser(user) {
      this.id = user.id;
      this.name = user.name;

      localStorage.setItem("userId", user.id);
      localStorage.setItem("userName", user.name);
    },

    logout() {
      this.id = null;
      this.name = null;

      localStorage.clear();
    }
  }
});