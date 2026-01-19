import { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { askAI } from "../api/aiApi";

export default function AIAssistant() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");
  const scrollRef = useRef(null);

  const [question, setQuestion] = useState("");
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (scrollRef.current) {
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
    }
  }, [messages]);

  if (!userId) { navigate("/login"); return null; }

  const handleAsk = async (text = question) => {
    if (!text.trim()) return;
    const userMessage = { role: "user", content: text };
    setMessages((prev) => [...prev, userMessage]);
    setQuestion("");
    setLoading(true);

    try {
      const res = await askAI(userId, text);
      setMessages((prev) => [...prev, { role: "ai", content: res.data.answer }]);
    } catch (e) {
      setMessages((prev) => [...prev, { role: "ai", content: "Sorry, I encountered an error." }]);
    } finally {
      setLoading(false);
    }
  };

  const suggestions = ["Explain my current plan", "Suggest best plan", "Is my plan expiring?"];

  return (
    <div className="flex flex-col h-[calc(100vh-80px)] bg-[#050505] max-w-4xl mx-auto">
      
      {/* CHAT AREA */}
      <div ref={scrollRef} className="flex-1 overflow-y-auto p-6 space-y-6">
        {messages.length === 0 && (
          <div className="h-full flex flex-col items-center justify-center text-center opacity-50">
            <div className="w-16 h-16 bg-[#6c63ff]/10 rounded-2xl flex items-center justify-center mb-4">
              <span className="text-3xl">🤖</span>
            </div>
            <h3 className="text-xl font-bold text-white">How can I help you?</h3>
            <p className="text-zinc-400 mt-2">Ask about your plan, data usage, or new offers.</p>
          </div>
        )}

        {messages.map((msg, idx) => (
          <div key={idx} className={`flex ${msg.role === "user" ? "justify-end" : "justify-start"}`}>
            <div className={`max-w-[80%] rounded-2xl px-5 py-3.5 text-sm leading-relaxed ${
              msg.role === "user" 
                ? "bg-[#6c63ff] text-white" 
                : "bg-[#14141A] border border-white/10 text-zinc-200"
            }`}>
              {msg.content}
            </div>
          </div>
        ))}
        {loading && (
          <div className="flex justify-start">
             <div className="bg-[#14141A] border border-white/10 rounded-2xl px-5 py-3.5">
               <span className="animate-pulse text-zinc-400 text-sm">Thinking...</span>
             </div>
          </div>
        )}
      </div>

      {/* INPUT AREA */}
      <div className="p-4 border-t border-white/10 bg-[#0A0A0A]/80 backdrop-blur-md">
        
        {/* Chips */}
        {messages.length === 0 && (
          <div className="flex gap-2 overflow-x-auto mb-4 pb-2">
            {suggestions.map((s) => (
              <button
                key={s}
                onClick={() => handleAsk(s)}
                className="whitespace-nowrap rounded-full border border-white/10 bg-white/5 px-4 py-2 text-xs font-medium text-zinc-300 hover:bg-white/10 hover:text-white transition-colors"
              >
                {s}
              </button>
            ))}
          </div>
        )}

        <div className="flex gap-3 relative">
          <input
            className="w-full bg-[#14141A] border border-white/10 rounded-xl px-4 py-3.5 text-white focus:border-[#6c63ff] focus:outline-none pr-12"
            placeholder="Type your question..."
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleAsk()}
          />
          <button
            onClick={() => handleAsk()}
            disabled={loading || !question.trim()}
            className="absolute right-2 top-2 bottom-2 bg-[#6c63ff] hover:bg-[#5b52ff] text-white p-2 rounded-lg disabled:opacity-50 disabled:bg-transparent"
          >
            <svg className="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 10l7-7m0 0l7 7m-7-7v18" />
            </svg>
          </button>
        </div>
      </div>
    </div>
  );
}