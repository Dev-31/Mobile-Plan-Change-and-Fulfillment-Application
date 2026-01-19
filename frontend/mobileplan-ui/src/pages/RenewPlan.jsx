import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { addToCart } from "../api/cartApi";

export default function RenewPlan() {
  const navigate = useNavigate();
  const { state } = useLocation();
  const planId = state?.planId;
  const planName = state?.planName;
  const [validity, setValidity] = useState(30);
  const [loading, setLoading] = useState(false);

  if (!planId) { navigate("/dashboard"); return null; }

  const handleRenew = async () => {
    try {
      setLoading(true);
      await addToCart(planId, validity);
      navigate("/cart");
    } catch (e) {
      alert("Renewal Error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#050505] flex items-center justify-center p-6">
      <div className="w-full max-w-md bg-[#0A0A0A] border border-white/10 rounded-3xl p-8">
        <h2 className="text-2xl font-bold text-white mb-2">Renew Plan</h2>
        <p className="text-zinc-400 mb-8">Select duration for <span className="text-white font-medium">{planName}</span></p>

        <div className="space-y-4 mb-8">
          {[7, 30].map((days) => (
            <button
              key={days}
              onClick={() => setValidity(days)}
              className={`w-full flex items-center justify-between p-4 rounded-xl border transition-all ${
                validity === days 
                  ? "bg-[#6c63ff]/10 border-[#6c63ff] text-white" 
                  : "bg-[#14141A] border-white/10 text-zinc-500 hover:border-white/30"
              }`}
            >
              <span className="font-medium">{days} Days</span>
              {validity === days && (
                <div className="w-5 h-5 bg-[#6c63ff] rounded-full flex items-center justify-center">
                  <svg className="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={3} d="M5 13l4 4L19 7" /></svg>
                </div>
              )}
            </button>
          ))}
        </div>

        <button 
          onClick={handleRenew} 
          disabled={loading}
          className="btn-primary w-full"
        >
          {loading ? "Processing..." : "Continue to Checkout"}
        </button>
        
        <button 
          onClick={() => navigate("/dashboard")}
          className="w-full mt-4 text-sm text-zinc-500 hover:text-white"
        >
          Cancel
        </button>
      </div>
    </div>
  );
}