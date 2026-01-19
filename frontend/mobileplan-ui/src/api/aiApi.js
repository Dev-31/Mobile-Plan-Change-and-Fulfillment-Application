import api from "./axios";

export const askAI = (userId, question) => {
  return api.post("/ai/ask", {
    userId,
    question,
  });
};
