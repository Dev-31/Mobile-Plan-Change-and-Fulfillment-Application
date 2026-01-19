import api from "./axios";

export const checkout = (userId, promoCode) => {
  return api.post("/checkout", {
    userId,
    promoCode,
  });
};

export const payOrder = (orderId) => {
  return api.post(`/orders/${orderId}/pay`);
};
