import api from "./axios";

export const getOrder = (orderId) => {
  return api.get(`/orders/${orderId}`);
};
