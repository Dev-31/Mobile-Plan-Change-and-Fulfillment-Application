import { useLocation, useNavigate } from "react-router-dom";

export default function CheckoutSuccess() {
  const { state } = useLocation();
  const navigate = useNavigate();

  return (
    <div className="min-h-screen flex items-center justify-center bg-green-50">
      <div className="bg-white p-6 rounded shadow w-80 text-center">
        <h2 className="text-xl font-semibold text-green-600 mb-2">
          Payment Successful
        </h2>

        <p>Order ID: {state?.orderId}</p>
        <p>Amount Paid: ₹{state?.amount}</p>

        <button
          className="mt-4 bg-blue-600 text-white px-4 py-2"
          onClick={() => navigate("/plans")}
        >
          Go to Plans
        </button>
      </div>
    </div>
  );
}
