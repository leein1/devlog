export default function LeftSideBar() {
  return (
    <aside className="hidden lg:flex flex-col p-8 gap-10 h-[calc(100vh-120px)] w-72 rounded-r-[3rem] sticky top-24 glass-panel overflow-y-auto">
      <div className="flex flex-col items-center text-center gap-5">
        <div className="w-24 h-24 rounded-full overflow-hidden border-2 border-outline-variant shadow-[0_4px_20px_rgba(0,0,0,0.08)]">
          <img
            src="https://via.placeholder.com/96"
            alt="Profile"
            className="w-full h-full object-cover"
          />
        </div>
        <div className="space-y-1">
          <h3 className="font-black text-xl tracking-tight text-on-surface">Developer Name</h3>
          <p className="text-on-surface-variant text-sm font-medium">Modern Web Developer</p>
          <p className="text-outline text-xs italic">dev@example.com</p>
        </div>
        <div className="flex gap-3 w-full justify-center">
          <div className="glass-card rounded-2xl px-4 py-2 flex flex-col items-center">
            <span className="text-lg font-black text-primary">24</span>
            <span className="text-[10px] text-outline font-medium uppercase tracking-wider">
              Posts
            </span>
          </div>
          <div className="glass-card rounded-2xl px-4 py-2 flex flex-col items-center">
            <span className="text-lg font-black text-primary">3</span>
            <span className="text-[10px] text-outline font-medium uppercase tracking-wider">
              Series
            </span>
          </div>
        </div>
      </div>

      <div className="flex flex-col gap-2">
        <a
          href="#"
          className="bg-primary text-on-primary rounded-full px-6 py-3 flex items-center gap-3 transition-all hover:bg-primary-container shadow-[0_4px_14px_rgba(92,110,120,0.28)]"
        >
          <span className="material-symbols-outlined text-[20px]">code</span>
          <span className="text-sm font-bold">GitHub</span>
        </a>
      </div>

      <div className="mt-auto">
        <p className="text-xs text-outline leading-relaxed">
          Exploring liquid interfaces and editorial design systems.
        </p>
      </div>
    </aside>
  );
}
