import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getProfile, updateProfile } from "../api/profileApi";

export default function Profile() {
  const userId = localStorage.getItem("userId");
  const navigate = useNavigate();
  const [profile, setProfile] = useState({ name: "", email: "", address: "" });
  const [edit, setEdit] = useState(false);
  
  useEffect(() => {
    getProfile(userId).then(res => setProfile(res.data)).catch(() => {});
  }, [userId]);

  const handleSave = async () => {
    try {
      await updateProfile(userId, profile);
      setEdit(false);
    } catch {
      alert("Update failed");
    }
  };

  return (
    <div className="min-h-screen bg-[#050505] py-12 px-6">
      <div className="mx-auto max-w-2xl">
        <button onClick={() => navigate(-1)} className="text-zinc-500 hover:text-white text-sm mb-8">← Back</button>
        
        <div className="bg-[#0A0A0A] border border-white/10 rounded-3xl p-8">
          <div className="flex justify-between items-center mb-8">
            <h1 className="text-3xl font-bold text-white">Profile</h1>
            <div className="h-12 w-12 rounded-full bg-[#6c63ff] flex items-center justify-center text-xl font-bold text-white">
              {profile.name?.charAt(0) || "U"}
            </div>
          </div>

          <div className="space-y-6">
            <div>
              <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Full Name</label>
              <input 
                className={`input-field ${!edit && "opacity-50 cursor-default border-transparent bg-transparent pl-0"}`}
                value={profile.name} 
                disabled={!edit}
                onChange={(e) => setProfile({...profile, name: e.target.value})}
              />
            </div>
            <div>
              <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Email Address</label>
              <input 
                className={`input-field ${!edit && "opacity-50 cursor-default border-transparent bg-transparent pl-0"}`}
                value={profile.email} 
                disabled={!edit}
                onChange={(e) => setProfile({...profile, email: e.target.value})}
              />
            </div>
            <div>
              <label className="text-xs font-bold text-zinc-500 uppercase tracking-wider block mb-2">Address</label>
              <textarea 
                className={`input-field resize-none h-24 ${!edit && "opacity-50 cursor-default border-transparent bg-transparent pl-0"}`}
                value={profile.address} 
                disabled={!edit}
                onChange={(e) => setProfile({...profile, address: e.target.value})}
              />
            </div>
          </div>

          <div className="mt-8 pt-6 border-t border-white/10 flex justify-end">
            {!edit ? (
              <button onClick={() => setEdit(true)} className="btn-secondary">Edit Profile</button>
            ) : (
              <div className="flex gap-3">
                <button onClick={() => setEdit(false)} className="btn-secondary">Cancel</button>
                <button onClick={handleSave} className="btn-primary">Save Changes</button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}