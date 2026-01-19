import { useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

export default function ForgotPassword() {
  const [mobile, setMobile] = useState("");
  const [otp, setOtp] = useState("");
  const [password, setPassword] = useState("");
  const [step, setStep] = useState(1);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const sendOtp = async () => {
    setLoading(true);
    setError("");
    try {
      await api.post("/auth/forgot-password", null, { params: { mobile } });
      setStep(2);
    } catch {
      setError("User not found or connection error.");
    } finally {
      setLoading(false);
    }
  };

  const reset = async () => {
    setLoading(true);
    setError("");
    try {
      await api.post("/auth/reset-password", null, {
        params: { mobile, otp, newPassword: password },
      });
      navigate("/login");
    } catch {
      setError("Invalid OTP or server error.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#050505] flex items-center justify-center relative overflow-hidden p-6">
       
      {/* Ambient Glow */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[500px] h-[500px] bg-teal-600/10 rounded-full blur-[120px] pointer-events-none" />

      <div className="w-full max-w-md bg-[#0A0A0A] border border-white/10 p-8 rounded-3xl shadow-2xl relative z-10 backdrop-blur-sm">
        
        <div className="mb-8">
           <h2 className="text-2xl font-bold text-white tracking-tight">Reset Password</h2>
           <p className="text-zinc-400 mt-2 text-sm">Recover access to your account.</p>
        </div>

        {step === 1 && (
          <div className="space-y-6">
            <div>
              <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Registered Mobile</label>
              <input 
                className="input-field" 
                placeholder="Enter mobile number" 
                value={mobile}
                onChange={e => setMobile(e.target.value)} 
              />
            </div>
            
            <button 
              className="btn-primary w-full" 
              onClick={sendOtp}
              disabled={loading}
            >
              {loading ? "Sending..." : "Send Verification Code"}
            </button>
          </div>
        )}

        {step === 2 && (
          <div className="space-y-5">
            <div>
              <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Enter OTP</label>
              <input 
                className="input-field text-center tracking-widest" 
                placeholder="••••" 
                value={otp}
                onChange={e => setOtp(e.target.value)} 
              />
            </div>
            
            <div>
              <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">New Password</label>
              <input 
                className="input-field" 
                type="password" 
                placeholder="Set new password" 
                value={password}
                onChange={e => setPassword(e.target.value)} 
              />
            </div>

            <button 
              className="btn-primary w-full" 
              onClick={reset}
              disabled={loading}
            >
              {loading ? "Updating..." : "Reset Password"}
            </button>
          </div>
        )}

        {error && <p className="mt-6 text-red-400 text-sm text-center">{error}</p>}

        {/* BACK BUTTON */}
        <button
          className="w-full text-sm text-zinc-500 hover:text-white mt-6 py-2 transition-colors"
          onClick={() => navigate("/login")}
        >
          ← Back to Login
        </button>
      </div>
    </div>
  );
}