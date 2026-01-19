import api from "./axios";

export const getActiveSubscription = (userId) => {
  return api.get(`/subscriptions/active/${userId}`);
};
