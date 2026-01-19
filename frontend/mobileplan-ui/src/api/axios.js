import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  // ⛔ DO NOT attach token for auth endpoints
  if (
    token && !config.url.startsWith("/auth")) {
      config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;
