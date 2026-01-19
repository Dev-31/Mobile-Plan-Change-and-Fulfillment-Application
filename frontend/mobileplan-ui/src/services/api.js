import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

export const registerUser = (mobileNumber) =>
  api.post(`/users/register?mobileNumber=${mobileNumber}`);

export const getPlans = () =>
  api.get("/plans");

export const createOrder = (userId, planId) =>
  api.post("/orders/create", { userId, planId });

export const payOrder = (orderId) =>
  api.post(`/orders/${orderId}/pay`);

export const getActiveSubscription = (userId) =>
  api.get(`/subscriptions/active/${userId}`);

export default api;
