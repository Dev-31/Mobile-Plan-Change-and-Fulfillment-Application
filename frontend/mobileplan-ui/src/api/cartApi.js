import api from "./axios";

export const addToCart = (planId, validityInDays) =>
  api.post("/cart/add", {
    planId,
    validityInDays,
    userId: localStorage.getItem("userId"),
  });

export const getCart = (userId) =>
  api.get(`/cart/${userId}`);

export const clearCart = (userId) =>
  api.delete(`/cart/${userId}`);
