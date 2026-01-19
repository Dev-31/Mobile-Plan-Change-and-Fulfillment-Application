import { useState } from "react";
import { registerUser, verifyRegisterOtp } from "../api/authApi";
import { useNavigate, Link } from "react-router-dom";

export default function Register() {
  const [mobile, setMobile] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [otp, setOtp] = useState("");
  const [step, setStep] = useState(1);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleRegister = async () => {
    setLoading(true);
    setError("");
    try {
      await registerUser(email, email, password);
      setStep(2);
    } catch (e) {
      setError(e.response?.data?.message || "Registration failed. Try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleVerifyOtp = async () => {
    setLoading(true);
    setError("");
    try {
      <p className="text-zinc-400 text-sm">
        Enter the OTP sent to {email}
      </p>

      navigate("/login", { replace: true });
    } catch {
      setError("Invalid OTP code.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#050505] flex items-center justify-center relative overflow-hidden p-6">

      {/* Ambient Glow */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[500px] h-[500px] bg-indigo-600/20 rounded-full blur-[120px] pointer-events-none" />

      <div className="w-full max-w-md bg-[#0A0A0A] border border-white/10 p-8 rounded-3xl shadow-2xl relative z-10 backdrop-blur-sm">

        {step === 1 && (
          <>
            <div className="mb-8">
              <button onClick={() => navigate(-1)} className="text-zinc-500 hover:text-white text-sm flex items-center gap-1 mb-6 transition-colors">
                <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" /></svg>
                Back
              </button>
              <h2 className="text-3xl font-bold text-white tracking-tight">Create Account</h2>
              <p className="text-zinc-400 mt-2 text-sm">Join us to explore the best plans.</p>
            </div>

            <div className="space-y-4">
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
                <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Email Address</label>
                <input
                  className="input-field"
                  placeholder="name@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <div>
                <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Password</label>
                <input
                  className="input-field"
                  type="password"
                  placeholder="Create a password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>

              <button
                className="btn-primary w-full mt-2"
                onClick={handleRegister}
                disabled={loading}
              >
                {loading ? "Processing..." : "Register"}
              </button>

              <p className="text-center text-zinc-500 text-sm mt-4">
                Already have an account? <Link to="/login" className="text-white hover:underline">Login</Link>
              </p>
            </div>
          </>
        )}

        {step === 2 && (
          <div className="space-y-6">
            <div className="text-center">
              <h2 className="text-2xl font-bold text-white mb-2">Verify Mobile</h2>
              <p className="text-zinc-400 text-sm">Enter the OTP sent to +91 {mobile}</p>
            </div>

            <input
              className="input-field text-center text-2xl tracking-widest"
              placeholder="••••"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              maxLength={6}
            />

            <button
              className="btn-primary w-full"
              onClick={handleVerifyOtp}
              disabled={loading}
            >
              {loading ? "Verifying..." : "Verify & Create Account"}
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