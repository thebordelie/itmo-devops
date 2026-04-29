import axios from "axios";
import { useUserStore } from "../stores/userStore";
const API_HOST = window.location.hostname;
const API_URL = `http://${API_HOST}:8080/tasks`;

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