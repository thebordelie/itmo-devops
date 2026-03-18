import axios from "axios";

const AUTH_URL = "http://localhost:8080/auth";

export default {
  login(credentials) {
    return axios.post(`${AUTH_URL}/login`, credentials);
  },

  register(user) {
    return axios.post(`${AUTH_URL}/register`, user);
  }
};