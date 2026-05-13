import axios from "axios";

const HOST = "api.task-manager.local";
const AUTH_URL = `http://${HOST}/auth`;

export default {
  login(credentials) {
    return axios.post(`${AUTH_URL}/login`, credentials);
  },

  register(user) {
    return axios.post(`${AUTH_URL}/register`, user);
  }
};