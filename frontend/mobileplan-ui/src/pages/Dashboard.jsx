import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getActiveSubscription } from "../api/subscriptionApi";

export default function Dashboard() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");
  const [subscription, setSubscription] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!userId) { navigate("/login"); return; }
    getActiveSubscription(userId)
      .then((res) => setSubscription(res.data))
      .catch(() => setSubscription(null))
      .finally(() => setLoading(false));
  }, [userId, navigate]);

  if (loading) {
    return <div className="min-h-screen bg-[#050505] flex items-center justify-center text-zinc-500">Loading Dashboard...</div>;
  }

  return (
    <div className="min-h-screen bg-[#050505] px-6 py-12 text-white">
      <div className="mx-auto max-w-6xl">
        <h1 className="text-5xl font-bold tracking-tight mb-4">Dashboard</h1>
        <p className="text-zinc-400 mb-12 text-lg">Manage your connectivity experience.</p>

        {!subscription ? (
          <div className="bg-[#0A0A0A] border border-white/10 rounded-3xl p-16 text-center">
            <h3 className="text-2xl font-bold mb-4">No Active Plan</h3>
            <p className="text-zinc-400 mb-8">You are currently offline. Get a plan to start.</p>
            <button onClick={() => navigate("/plans")} className="btn-primary mx-auto">Browse Plans</button>
          </div>
        ) : (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {/* Active Plan Card */}
            <div className="lg:col-span-2 bg-[#0A0A0A] border border-white/10 rounded-3xl p-10 relative overflow-hidden">
              <div className="flex justify-between items-start mb-10">
                <div>
                  <h2 className="text-4xl font-bold">{subscription.planName}</h2>
                  <p className="text-zinc-400 mt-2">₹{subscription.price}/mo</p>
                </div>
                <div className="bg-[#6c63ff]/10 text-[#6c63ff] border border-[#6c63ff]/20 px-4 py-1.5 rounded-full text-xs font-bold uppercase tracking-wider">
                  Active
                </div>
              </div>
              
              <div className="grid grid-cols-2 gap-10 border-t border-white/5 pt-8 mb-8">
                <div>
                  <p className="text-xs font-bold text-zinc-500 uppercase tracking-wider mb-2">Expires On</p>
                  <p className="text-2xl font-medium">{new Date(subscription.endDate).toLocaleDateString()}</p>
                </div>
                <div>
                   <p className="text-xs font-bold text-zinc-500 uppercase tracking-wider mb-2">Data Left</p>
                   <p className="text-2xl font-medium">{100 - subscription.dataUsedPercent}%</p>
                </div>
              </div>

              <div className="flex gap-4">
                <button onClick={() => navigate("/renew", { state: { planId: subscription.planId, planName: subscription.planName } })} className="btn-primary flex-1">Renew</button>
                <button onClick={() => navigate("/plans")} className="btn-secondary flex-1">Change Plan</button>
              </div>
            </div>

            {/* AI Teaser */}
            <div className="bg-gradient-to-br from-[#6c63ff] to-[#4f46e5] rounded-3xl p-10 flex flex-col justify-between text-white relative overflow-hidden group hover:scale-[1.02] transition-transform">
               <div className="absolute top-0 right-0 w-64 h-64 bg-white/10 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2"></div>
               <div>
                 <div className="w-12 h-12 bg-white/20 backdrop-blur-md rounded-xl flex items-center justify-center mb-6">✨</div>
                 <h3 className="text-2xl font-bold mb-2">AI Assistant</h3>
                 <p className="text-white/80 text-sm">Ask about usage patterns or get plan advice.</p>
               </div>
               <button onClick={() => navigate("/ai")} className="mt-8 w-full bg-white text-[#6c63ff] py-3 rounded-xl font-bold hover:bg-zinc-100 transition-colors">Launch AI</button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}