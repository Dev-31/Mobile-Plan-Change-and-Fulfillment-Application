import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";
import { useState } from "react";

export default function Navbar() {
  const { token, logout } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

  if (!token) return null;

  const isActive = (path) => location.pathname === path;

  const navLinks = [
    { label: "Dashboard", path: "/dashboard" },
    { label: "Plans", path: "/plans" },
    { label: "Cart", path: "/cart" },
    { label: "Orders", path: "/orders" },
    { label: "AI Assistant", path: "/ai" },
  ];

  return (
    <header className="sticky top-0 z-50 w-full border-b border-white/[0.06] bg-[#050505]/80 backdrop-blur-xl">
      <div className="mx-auto flex h-20 max-w-7xl items-center justify-between px-6">
        
        {/* BRAND */}
        <Link to="/dashboard" className="flex items-center gap-3 group">
          <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-[#6c63ff]/10 text-[#6c63ff] transition-colors group-hover:bg-[#6c63ff] group-hover:text-white">
            <svg className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
            </svg>
          </div>
          <span className="text-lg font-bold tracking-tight text-white">MobilePlan</span>
        </Link>

        {/* DESKTOP NAV */}
        <nav className="hidden md:flex items-center gap-8">
          {navLinks.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className={`text-sm font-medium transition-colors ${
                isActive(item.path) ? "text-white" : "text-zinc-500 hover:text-white"
              }`}
            >
              {item.label}
            </Link>
          ))}
        </nav>

        {/* RIGHT SIDE */}
        <div className="flex items-center gap-6">
          <Link
            to="/profile"
            className="hidden md:block text-sm font-medium text-zinc-500 hover:text-white transition-colors"
          >
            Profile
          </Link>
          <button
            onClick={() => {
              logout();
              navigate("/login", { replace: true });
            }}
            className="rounded-full border border-white/10 bg-white/5 px-4 py-1.5 text-xs font-medium text-white hover:bg-white/10 transition-colors"
          >
            Logout
          </button>
          
          {/* Mobile Menu Btn */}
          <button 
            className="md:hidden text-white"
            onClick={() => setOpen(!open)}
          >
            <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d={open ? "M6 18L18 6M6 6l12 12" : "M4 6h16M4 12h16M4 18h16"} />
            </svg>
          </button>
        </div>
      </div>

      {/* MOBILE MENU */}
      {open && (
        <div className="absolute left-0 top-20 w-full border-b border-white/10 bg-[#0A0A0A] p-4 md:hidden">
          <div className="flex flex-col gap-4">
            {navLinks.map((item) => (
              <Link
                key={item.path}
                to={item.path}
                onClick={() => setOpen(false)}
                className={`text-sm font-medium ${
                  isActive(item.path) ? "text-[#6c63ff]" : "text-zinc-400"
                }`}
              >
                {item.label}
              </Link>
            ))}
          </div>
        </div>
      )}
    </header>
  );
}