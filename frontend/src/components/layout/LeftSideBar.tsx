export default function LeftSideBar() {
  return (
    <aside className="hidden lg:flex flex-col p-8 gap-10 h-[calc(100vh-120px)] w-72 rounded-r-[3rem] sticky top-24 glass-panel overflow-y-auto">
      <div className="flex flex-col items-center text-center gap-5">
        <div className="w-24 h-24 rounded-full overflow-hidden border-2 border-[#ddddd9] shadow-[0_4px_20px_rgba(0,0,0,0.08)]">
          <img
            src="https://via.placeholder.com/96"
            alt="Profile"
            className="w-full h-full object-cover"
          />
        </div>
        <div className="space-y-1">
          <h3 className="font-black text-xl tracking-tight text-[#1c1c1a]">Developer Name</h3>
          <p className="text-[#494946] text-sm font-medium">Modern Web Developer</p>
          <p className="text-[#797976] text-xs italic">dev@example.com</p>
        </div>
        <div className="flex gap-3 w-full justify-center">
          <div className="glass-card rounded-2xl px-4 py-2 flex flex-col items-center">
            <span className="text-lg font-black text-[#5c6e78]">24</span>
            <span className="text-[10px] text-[#797976] font-medium uppercase tracking-wider">Posts</span>
          </div>
          <div className="glass-card rounded-2xl px-4 py-2 flex flex-col items-center">
            <span className="text-lg font-black text-[#5c6e78]">3</span>
            <span className="text-[10px] text-[#797976] font-medium uppercase tracking-wider">Series</span>
          </div>
        </div>
      </div>

      <div className="flex flex-col gap-2">
        <a
          href="#"
          className="bg-[#5c6e78] text-[#f4f6f7] rounded-full px-6 py-3 flex items-center gap-3 transition-all hover:bg-[#4a5a63] shadow-[0_4px_14px_rgba(92,110,120,0.28)]"
        >
          <span className="material-symbols-outlined text-[20px]">code</span>
          <span className="text-sm font-bold">GitHub</span>
        </a>
      </div>

      <div className="mt-auto">
        <p className="text-xs text-[#797976] leading-relaxed">
          Exploring liquid interfaces and editorial design systems.
        </p>
      </div>
    </aside>
  );
}
