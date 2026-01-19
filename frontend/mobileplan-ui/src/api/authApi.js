import api from "./axios";

// LOGIN
export const loginUser = (mobile, password) =>
  api.post("/auth/login", null, {
    params: { mobile, password },
  });

// VERIFY LOGIN OTP
export const verifyLoginOtp = (mobile, otp) =>
  api.post("/auth/verify-login", null, {
    params: { mobile, otp },
  });

// REGISTER
export const registerUser = (mobile, email, password) =>
  api.post("/auth/register", null, {
    params: { mobile, email, password },
  });

// VERIFY REGISTER OTP
export const verifyRegisterOtp = (email, otp) =>
  api.post("/auth/verify-register", null, {
    params: { email, otp },
  });

// FORGOT PASSWORD
export const forgotPassword = (mobile) =>
  api.post("/auth/forgot-password", null, {
    params: { mobile },
  });

// RESET PASSWORD
export const resetPassword = (mobile, otp, newPassword) =>
  api.post("/auth/reset-password", null, {
    params: { mobile, otp, newPassword },
  });
