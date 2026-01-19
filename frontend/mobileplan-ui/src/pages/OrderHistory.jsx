import { useEffect, useState } from "react";
import api from "../api/axios";

export default function OrderHistory() {
  const userId = localStorage.getItem("userId");
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    api.get(`/orders/user/${userId}`)
      .then(res => setOrders(res.data))
      .catch(() => setOrders([]));
  }, [userId]);

  return (
    <div className="min-h-screen bg-[#050505] py-12 px-6">
      <div className="mx-auto max-w-4xl">
        <h2 className="text-3xl font-bold text-white mb-8">Order History</h2>

        {orders.length === 0 ? (
          <div className="text-center py-20 bg-[#0A0A0A] rounded-2xl border border-white/10">
            <p className="text-zinc-500">No orders found.</p>
          </div>
        ) : (
          <div className="space-y-4">
            {orders.map((order) => (
              <div key={order.orderId} className="group bg-[#0A0A0A] border border-white/10 rounded-2xl p-6 hover:border-white/20 transition-all">
                <div className="flex flex-col sm:flex-row justify-between sm:items-center gap-4">
                  <div>
                    <div className="flex items-center gap-3 mb-1">
                      <span className="text-white font-mono font-medium">#{order.orderId}</span>
                      <span className={`text-[10px] font-bold px-2 py-0.5 rounded uppercase tracking-wider ${
                        order.status === 'SUCCESS' ? 'bg-green-500/10 text-green-400' : 'bg-zinc-500/10 text-zinc-400'
                      }`}>
                        {order.status}
                      </span>
                    </div>
                    <p className="text-sm text-zinc-500">Paid via {order.paymentMode}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-xl font-bold text-white">₹{order.amount}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}