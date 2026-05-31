import axios from "axios";
import { useUserStore } from "../stores/userStore";
const HOST = "api.task-manager.local";
const API_URL = `http://${HOST}/tasks`;

export default {
  async getAll() {
    const userStore = useUserStore();
    return axios.get(`${API_URL}?userId=${userStore.id}`);
  },

  async create(task) {
    const userStore = useUserStore();
    task.userId = userStore.id;
    return axios.post(API_URL, task);
  },

  update(id, task) {
    return axios.put(`${API_URL}/${id}`, task);
  },

  delete(id) {
    return axios.delete(`${API_URL}/${id}`);
  }
};