import api from "./axios";

export const fetchPlans = (type) => {
  if (!type) {
    return api.get("/plans");
  }
  return api.get(`/plans/type/${type}`);
};
