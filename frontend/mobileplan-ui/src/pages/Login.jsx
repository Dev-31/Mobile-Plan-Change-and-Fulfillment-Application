import { useState } from "react";
import { loginUser, verifyLoginOtp } from "../api/authApi";
import { useAuth } from "../auth/AuthContext";
import { useNavigate, Link } from "react-router-dom";

export default function Login() {
  const [mobile, setMobile] = useState("");
  const [password, setPassword] = useState("");
  const [otp, setOtp] = useState("");
  const [step, setStep] = useState(1);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();

  const sendOtp = async () => {
    setLoading(true);
    setError("");
    try {
      await loginUser(mobile, password);
      setStep(2);
    } catch {
      setError("Invalid credentials. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const verifyOtp = async () => {
    setLoading(true);
    setError("");
    try {
      const res = await verifyLoginOtp(mobile, otp);
      login(res.data.token, res.data.userId);
      navigate("/dashboard");
    } catch {
      setError("Invalid OTP code.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#050505] flex items-center justify-center relative overflow-hidden p-6">
      
      {/* Ambient Glow */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[500px] h-[500px] bg-[#6c63ff]/20 rounded-full blur-[120px] pointer-events-none" />

      <div className="w-full max-w-md bg-[#0A0A0A] border border-white/10 p-8 rounded-3xl shadow-2xl relative z-10 backdrop-blur-sm">
        
        {/* Header */}
        <div className="text-center mb-10">
          <div className="inline-flex h-12 w-12 items-center justify-center rounded-xl bg-[#6c63ff]/10 text-[#6c63ff] mb-4">
            <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
            </svg>
          </div>
          <h2 className="text-3xl font-bold text-white tracking-tight">Welcome Back</h2>
          <p className="text-zinc-400 mt-2 text-sm">Sign in to manage your plan.</p>
        </div>

        {step === 1 && (
          <div className="space-y-5">
            <div>
              <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Mobile Number</label>
              <input
                className="input-field"
                placeholder="Enter 10-digit number"
                value={mobile}
                onChange={(e) => setMobile(e.target.value)}
              />
            </div>
            
            <div>
              <div className="flex justify-between mb-2">
                <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider">Password</label>
                <Link to="/forgot" className="text-xs font-medium text-[#6c63ff] hover:text-[#5b52ff]">
                  Forgot password?
                </Link>
              </div>
              <input
                className="input-field"
                type="password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>

            <button
              className="btn-primary w-full"
              onClick={sendOtp}
              disabled={loading}
            >
              {loading ? "Verifying..." : "Continue"}
            </button>

            <div className="text-center pt-2">
              <span className="text-zinc-500 text-sm">Don't have an account? </span>
              <Link to="/register" className="text-white font-medium hover:underline">
                Create Account
              </Link>
            </div>
          </div>
        )}

        {step === 2 && (
          <div className="space-y-5">
            <div className="text-center mb-6">
              <p className="text-zinc-400 text-sm">We sent a verification code to</p>
              <p className="text-white font-medium">+91 {mobile}</p>
            </div>

            <div>
               <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">OTP Code</label>
              <input
                className="input-field text-center text-2xl tracking-widest"
                placeholder="••••"
                value={otp}
                onChange={(e) => setOtp(e.target.value)}
                maxLength={6}
              />
            </div>

            <button
              className="btn-primary w-full"
              onClick={verifyOtp}
              disabled={loading}
            >
              {loading ? "Logging in..." : "Verify & Login"}
            </button>
            
            <button
              className="w-full text-sm text-zinc-500 hover:text-white py-2"
              onClick={() => setStep(1)}
            >
              ← Use a different number
            </button>
          </div>
        )}

        {error && (
          <div className="mt-6 p-3 rounded-lg bg-red-500/10 border border-red-500/20 text-red-400 text-sm text-center">
            {error}
          </div>
        )}
      </div>
    </div>
  );
}