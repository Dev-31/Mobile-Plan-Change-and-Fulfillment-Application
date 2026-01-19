import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getCart, clearCart } from "../api/cartApi";
import api from "../api/axios";

export default function Checkout() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");
  const mobile = localStorage.getItem("mobile") || "";

  const [cart, setCart] = useState(null);
  const [promo, setPromo] = useState("");
  const [paymentMode, setPaymentMode] = useState("CARD");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!userId) { navigate("/login"); return; }
    getCart(userId)
      .then((res) => {
        if (!res.data.planNames?.length) navigate("/plans");
        else setCart(res.data);
      })
      .catch(() => setCart(null));
  }, [userId, navigate]);

  if (!cart) return null;

  const handlePay = async () => {
    setLoading(true);
    setError("");
    try {
      await api.post("/checkout", { userId, promoCode: promo || null, paymentMode });
      await clearCart(userId);
      // Navigate to orders with success state, or a dedicated success page
      navigate("/orders"); 
    } catch (e) {
      setError("Payment failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const paymentOptions = [
    { id: "CARD", label: "Credit/Debit Card", icon: "💳" },
    { id: "UPI", label: "UPI / Netbanking", icon: "📱" },
    { id: "WALLET", label: "Digital Wallet", icon: "👛" },
  ];

  return (
    <div className="min-h-screen bg-[#050505] py-16 px-4">
      <div className="mx-auto max-w-lg">
        
        <div className="text-center mb-10">
          <h1 className="text-3xl font-bold text-white">Checkout</h1>
          <p className="text-zinc-400 mt-2">Securely complete your transaction</p>
        </div>

        {/* Order Card */}
        <div className="bg-[#14141A] border border-white/10 rounded-2xl p-6 mb-8 text-center">
          <p className="text-zinc-400 text-sm">Amount to Pay</p>
          <div className="text-4xl font-bold text-white mt-1 mb-4">₹{cart.payableAmount}</div>
          <div className="inline-block bg-white/5 rounded-lg px-3 py-1 text-xs text-zinc-300 border border-white/5">
            {cart.planNames[0]}
          </div>
        </div>

        {/* Form */}
        <div className="space-y-6">
          
          <div>
            <label className="text-xs font-semibold text-zinc-500 uppercase tracking-wider mb-2 block">Mobile Number</label>
            <input className="input-field opacity-60 cursor-not-allowed" value={mobile} disabled />
          </div>

          <div>
            <label className="text-xs font-semibold text-zinc-500 uppercase tracking-wider mb-2 block">Promo Code</label>
            <input 
              className="input-field" 
              placeholder="Enter code (optional)" 
              value={promo}
              onChange={(e) => setPromo(e.target.value)}
            />
          </div>

          <div>
            <label className="text-xs font-semibold text-zinc-500 uppercase tracking-wider mb-2 block">Payment Mode</label>
            <div className="grid grid-cols-3 gap-3">
              {paymentOptions.map((opt) => (
                <button
                  key={opt.id}
                  onClick={() => setPaymentMode(opt.id)}
                  className={`flex flex-col items-center justify-center p-4 rounded-xl border transition-all ${
                    paymentMode === opt.id 
                      ? "bg-[#6c63ff]/10 border-[#6c63ff] text-white" 
                      : "bg-[#0A0A0A] border-white/10 text-zinc-500 hover:border-white/30"
                  }`}
                >
                  <span className="text-2xl mb-2">{opt.icon}</span>
                  <span className="text-xs font-medium">{opt.label.split(" ")[0]}</span>
                </button>
              ))}
            </div>
          </div>

          {error && <div className="p-3 rounded-lg bg-red-500/10 border border-red-500/20 text-red-400 text-sm text-center">{error}</div>}

          <div className="pt-4 flex gap-4">
            <button 
              onClick={() => navigate("/plans")}
              className="flex-1 btn-secondary"
            >
              Cancel
            </button>
            <button 
              onClick={handlePay}
              disabled={loading}
              className="flex-1 btn-primary"
            >
              {loading ? "Processing..." : "Pay Now"}
            </button>
          </div>

        </div>
      </div>
    </div>
  );
}