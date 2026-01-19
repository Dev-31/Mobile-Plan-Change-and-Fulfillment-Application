import { useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchPlans } from "../api/planApi";
import { addToCart } from "../api/cartApi";
import { useAuth } from "../auth/AuthContext";

export default function Plans() {
  const [plans, setPlans] = useState([]);
  const [type, setType] = useState("");
  const [validity, setValidity] = useState(30);
  const navigate = useNavigate();
  const location = useLocation();
  const { userId } = useAuth();
  
  const renewPlanId = location.state?.renewPlanId;

  useEffect(() => {
    fetchPlans(type).then((res) => setPlans(res.data));
  }, [type]);

  // Clear renew state logic
  useEffect(() => {
    if (renewPlanId) {
      // Optional: scroll to plan or highlight it
      navigate("/plans", { replace: true, state: {} });
    }
  }, []);

  const handleSelectPlan = async (planId) => {
    if (!userId) {
      navigate("/login");
      return;
    }
    try {
      await addToCart(planId, validity);
      navigate("/cart");
    } catch (error) {
      console.error("Add to cart failed", error);
    }
  };

  return (
    <div className="min-h-screen bg-[#050505] px-6 py-12">
      <div className="mx-auto max-w-7xl">
        
        {/* HEADER */}
        <div className="mb-12 flex flex-col md:flex-row md:items-end md:justify-between gap-6">
          <div>
            <h1 className="text-4xl font-bold text-white tracking-tight">Select a Plan</h1>
            <p className="mt-2 text-zinc-400">Choose the perfect data and voice pack for your needs.</p>
          </div>
          
          {/* VALIDITY TOGGLE */}
          <div className="flex items-center gap-3 bg-[#14141A] p-1 rounded-xl border border-white/10">
            {[7, 30].map((v) => (
              <button
                key={v}
                onClick={() => setValidity(v)}
                className={`px-4 py-1.5 rounded-lg text-sm font-medium transition-all ${
                  validity === v ? "bg-white text-black shadow-lg" : "text-zinc-500 hover:text-white"
                }`}
              >
                {v} Days
              </button>
            ))}
          </div>
        </div>

        {/* FILTERS */}
        <div className="flex gap-2 mb-8 overflow-x-auto pb-2">
          {["", "PREPAID", "POSTPAID"].map((opt) => (
            <button
              key={opt}
              onClick={() => setType(opt)}
              className={`px-5 py-2 rounded-full border text-sm font-medium whitespace-nowrap transition-colors ${
                type === opt 
                  ? "bg-[#6c63ff] border-[#6c63ff] text-white" 
                  : "border-white/10 bg-transparent text-zinc-400 hover:border-white/30 hover:text-white"
              }`}
            >
              {opt === "" ? "All Plans" : opt}
            </button>
          ))}
        </div>

        {/* PLANS GRID */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {plans.map((plan) => {
            const isRenew = renewPlanId === plan.id;
            const finalPrice = Math.round((plan.price / plan.validityInDays) * validity);

            return (
              <div 
                key={plan.id}
                className={`relative flex flex-col justify-between p-8 rounded-3xl border transition-all duration-300 group hover:-translate-y-1 ${
                  isRenew 
                    ? "bg-[#6c63ff]/10 border-[#6c63ff] shadow-[0_0_30px_rgba(108,99,255,0.15)]" 
                    : "bg-[#0A0A0A] border-white/10 hover:border-white/20 hover:shadow-2xl"
                }`}
              >
                <div>
                  <div className="flex justify-between items-start mb-6">
                    <span className="text-xs font-bold tracking-wider text-zinc-500 uppercase">{plan.type || "DATA"}</span>
                    {isRenew && <span className="text-xs font-bold text-[#6c63ff] bg-[#6c63ff]/10 px-2 py-1 rounded">RENEWING</span>}
                  </div>
                  
                  <h3 className="text-2xl font-bold text-white mb-2">{plan.name}</h3>
                  <div className="flex items-baseline gap-1 mb-6">
                    <span className="text-4xl font-bold text-white">₹{finalPrice}</span>
                    <span className="text-sm text-zinc-500">/ {validity} days</span>
                  </div>

                  <ul className="space-y-3 mb-8">
                    {["Unlimited Calls", "100 SMS/day", "High Speed Data"].map((feature, i) => (
                      <li key={i} className="flex items-center gap-3 text-sm text-zinc-300">
                        <svg className="w-5 h-5 text-[#6c63ff]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                        </svg>
                        {feature}
                      </li>
                    ))}
                  </ul>
                </div>

                <button
                  onClick={() => handleSelectPlan(plan.id)}
                  className={`w-full py-3.5 rounded-xl text-sm font-bold transition-all ${
                    isRenew 
                      ? "bg-[#6c63ff] text-white shadow-lg hover:bg-[#5b52ff]" 
                      : "bg-white text-black hover:bg-zinc-200"
                  }`}
                >
                  {isRenew ? "Renew Now" : "Choose Plan"}
                </button>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}