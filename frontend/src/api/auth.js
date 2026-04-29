import axios from "axios";

const HOST = window.location.hostname;
const AUTH_URL = `http://${HOST}:8080/auth`;

export default {
  login(credentials) {
    return axios.post(`${AUTH_URL}/login`, credentials);
  },

  register(user) {
    return axios.post(`${AUTH_URL}/register`, user);
  }
};