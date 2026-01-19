import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getCart, clearCart } from "../api/cartApi";
import { useAuth } from "../auth/AuthContext";

export default function Cart() {
  const [cart, setCart] = useState(null);
  const navigate = useNavigate();
  const { userId } = useAuth();

  useEffect(() => {
    if (!userId) {
      navigate("/login");
      return;
    }
    getCart(userId)
      .then((res) => setCart(res.data))
      .catch(() => setCart({}));
  }, [navigate, userId]);

  if (!cart) return null;

  // Empty State
  if (!cart.planNames || cart.planNames.length === 0) {
    return (
      <div className="flex h-[80vh] flex-col items-center justify-center text-center">
        <div className="bg-[#14141A] p-6 rounded-full mb-6">
          <svg className="w-10 h-10 text-zinc-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
          </svg>
        </div>
        <h2 className="text-2xl font-bold text-white mb-2">Your Cart is Empty</h2>
        <p className="text-zinc-500 mb-8">Looks like you haven't added any plans yet.</p>
        <button onClick={() => navigate("/plans")} className="btn-primary">
          Browse Plans
        </button>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#050505] py-12 px-6">
      <div className="mx-auto max-w-5xl">
        <button onClick={() => navigate("/plans")} className="text-zinc-500 hover:text-white text-sm mb-8 flex items-center gap-2">
          ← Continue Shopping
        </button>

        <h1 className="text-3xl font-bold text-white mb-10">Review Order</h1>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-10">
          
          {/* Left: Cart Items */}
          <div className="lg:col-span-2 space-y-4">
            <div className="bg-[#0A0A0A] border border-white/10 rounded-2xl p-6 flex justify-between items-center">
              <div>
                <h3 className="text-xl font-bold text-white">{cart.planNames[0]}</h3>
                <p className="text-zinc-400 text-sm mt-1">Validity: {cart.validityInDays} days</p>
              </div>
              <div className="text-right">
                <p className="text-xl font-bold text-white">₹{cart.baseAmount}</p>
                <button 
                  onClick={async () => {
                    await clearCart(userId);
                    navigate("/plans");
                  }}
                  className="text-xs text-red-500 hover:text-red-400 mt-2 font-medium"
                >
                  Remove
                </button>
              </div>
            </div>
          </div>

          {/* Right: Summary */}
          <div className="lg:col-span-1">
            <div className="bg-[#14141A] border border-white/10 rounded-2xl p-6 sticky top-24">
              <h3 className="text-lg font-bold text-white mb-6">Payment Details</h3>
              
              <div className="space-y-3 mb-6">
                <div className="flex justify-between text-sm text-zinc-400">
                  <span>Subtotal</span>
                  <span>₹{cart.baseAmount}</span>
                </div>
                <div className="flex justify-between text-sm text-green-500">
                  <span>Discount</span>
                  <span>- ₹{cart.discount}</span>
                </div>
                <div className="h-px bg-white/10 my-2"></div>
                <div className="flex justify-between text-lg font-bold text-white">
                  <span>Total</span>
                  <span>₹{cart.payableAmount}</span>
                </div>
              </div>

              <button 
                onClick={() => navigate("/checkout")}
                className="w-full btn-primary"
              >
                Proceed to Checkout
              </button>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}