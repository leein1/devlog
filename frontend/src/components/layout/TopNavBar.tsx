interface TopNavBarProps {
  onWrite?: () => void;
}

export default function TopNavBar({ onWrite }: TopNavBarProps) {
  return (
    <nav className="fixed top-0 w-full z-50 glass-panel flex justify-between items-center px-8 py-4">
      <div className="flex items-center gap-12">
        <span className="text-2xl font-black tracking-tighter text-[#1c1c1a]">devlog</span>
        <div className="hidden md:flex gap-2 items-center">
          <a
            className="text-[#5c6e78] font-bold px-4 py-1.5 rounded-full bg-[#5c6e78]/8 border border-[#5c6e78]/15"
            href="#"
          >
            게시글
          </a>
          <a
            className="text-[#494946] font-medium hover:bg-[#1c1c1a]/5 transition-all duration-200 px-4 py-1.5 rounded-full"
            href="#"
          >
            시리즈
          </a>
          <a
            className="text-[#494946] font-medium hover:bg-[#1c1c1a]/5 transition-all duration-200 px-4 py-1.5 rounded-full"
            href="#"
          >
            카테고리
          </a>
        </div>
      </div>
      <div className="flex items-center gap-4">
        <button
          onClick={onWrite}
          className="hidden md:inline-flex items-center gap-1.5 px-5 py-2 rounded-full font-bold text-sm text-[#5c6e78] border border-[#5c6e78]/25 hover:bg-[#5c6e78]/8 transition-all"
        >
          <span className="material-symbols-outlined text-[18px]">edit</span>
          글쓰기
        </button>
        <button className="bg-[#5c6e78] text-[#f4f6f7] px-6 py-2 rounded-full font-bold transition-all hover:bg-[#4a5a63] active:scale-95 shadow-[0_4px_14px_rgba(92,110,120,0.28)]">
          Login
        </button>
      </div>
    </nav>
  );
}
