import api from "./axios";

export const getProfile = (userId) => {
  return api.get(`/profile/${userId}`);
};

export const updateProfile = (userId, data) => {
  return api.put(`/profile/${userId}`, data);
};
